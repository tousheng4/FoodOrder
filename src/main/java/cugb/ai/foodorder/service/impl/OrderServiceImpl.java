package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.CancelOrderRequest;
import cugb.ai.foodorder.dto.CreateOrderRequest;
import cugb.ai.foodorder.entity.Address;
import cugb.ai.foodorder.entity.OrderInfo;
import cugb.ai.foodorder.entity.OrderItem;
import cugb.ai.foodorder.mapper.AddressMapper;
import cugb.ai.foodorder.mapper.CartItemMapper;
import cugb.ai.foodorder.mapper.OrderInfoMapper;
import cugb.ai.foodorder.mapper.OrderItemMapper;
import cugb.ai.foodorder.service.OrderService;
import cugb.ai.foodorder.vo.CartItemVO;
import cugb.ai.foodorder.vo.OrderDetailVO;
import cugb.ai.foodorder.vo.OrderItemVO;
import cugb.ai.foodorder.vo.OrderSummaryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderInfoMapper orderInfoMapper;
    private final OrderItemMapper orderItemMapper;
    private final CartItemMapper cartItemMapper;
    private final AddressMapper addressMapper;

    public OrderServiceImpl(OrderInfoMapper orderInfoMapper,
                            OrderItemMapper orderItemMapper,
                            CartItemMapper cartItemMapper,
                            AddressMapper addressMapper) {
        this.orderInfoMapper = orderInfoMapper;
        this.orderItemMapper = orderItemMapper;
        this.cartItemMapper = cartItemMapper;
        this.addressMapper = addressMapper;
    }
    /**
     * 从购物车生成订单
     */
    @Transactional
    @Override
    public Long createOrder(Long userId, CreateOrderRequest req) {
        // 1. 查询当前用户购物车
        List<CartItemVO> cartItems = cartItemMapper.selectDetailByUserId(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new BusinessException(ErrorCode.CART_EMPTY, "购物车为空，无法下单");
        }
        // 2. 收货信息处理：优先使用 addressId
        String receiverName = req.getReceiverName();
        String receiverPhone = req.getReceiverPhone();
        String receiverAddress = req.getReceiverAddress();

        if (req.getAddressId() != null) {
            Address addr = addressMapper.selectByIdAndUserId(req.getAddressId(), userId);
            if (addr == null || addr.getDeleted() != null && addr.getDeleted() == 1) {
                throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND, "所选地址不存在");
            }
            receiverName = addr.getReceiverName();
            receiverPhone = addr.getReceiverPhone();
            receiverAddress = addr.getDetailAddress();
        }
        // 3. 计算金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItemVO ci : cartItems) {
            BigDecimal itemTotal = ci.getPrice()
                    .multiply(BigDecimal.valueOf(ci.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        BigDecimal freightAmount = BigDecimal.ZERO; // 先写死，后面可以从配置表读取
        BigDecimal payAmount = totalAmount.add(freightAmount);

        // 4. 生成订单主表
        OrderInfo order = new OrderInfo();
        order.setOrderNo(generateOrderNo(userId));
        order.setUserId(userId);
        order.setStatus(0);
        order.setTotalAmount(totalAmount);
        order.setPayAmount(payAmount);
        order.setFreightAmount(freightAmount);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        order.setRemark(req.getRemark());

        orderInfoMapper.insert(order); // useGeneratedKeys 回填 id
        Long orderId = order.getId();

        //5. 生成订单明细
        List<OrderItem> items = new ArrayList<>();
        for (CartItemVO ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrderId(orderId);
            oi.setDishId(ci.getDishId());
            oi.setDishName(ci.getDishName());
            oi.setDishImage(ci.getDishImage());
            oi.setUnitPrice(ci.getPrice());
            oi.setQuantity(ci.getQuantity());
            oi.setSubTotal(
                    ci.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity()))
            );
            items.add(oi);
        }
        orderItemMapper.batchInsert(items);

        // 6. 清空购物车
        cartItemMapper.deleteByUserId(userId);

        return orderId;
    }

    /**
     * 模拟支付
     */
    @Transactional
    @Override
    public void payOrder(Long userId, Long orderId) {
        OrderInfo order = orderInfoMapper.selectByIdAndUserId(orderId, userId);
        if (order == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");
        }
        if (order.getStatus() == null || order.getStatus() != 0) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "只有待支付订单可以支付");
        }

        int updated = orderInfoMapper.updateStatusToPaid(orderId);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "订单状态更新失败，请重试");
        }
    }

    /**
     * 取消订单
     */
    @Transactional
    @Override
    public void cancelOrder(Long userId, Long orderId, CancelOrderRequest req) {
        OrderInfo order = orderInfoMapper.selectByIdAndUserId(orderId, userId);
        if (order == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");
        }
        if (order.getStatus() == null || order.getStatus() != 0) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "只有待支付订单可以取消");
        }

        String reason = req != null ? req.getReason() : null;
        int updated = orderInfoMapper.updateStatusToCancelled(orderId, reason);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "订单状态更新失败，请重试");
        }
    }

    @Override
    public PageResult<OrderSummaryVO> listUserOrders(Long userId, Integer status, Integer page, Integer size) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        int offset = (page - 1) * size;

        Long total = orderInfoMapper.countByUserId(userId, status);
        List<OrderInfo> list = orderInfoMapper.selectPageByUserId(userId, status, offset, size);

        List<OrderSummaryVO> voList = new ArrayList<>();
        for (OrderInfo o : list) {
            OrderSummaryVO vo = new OrderSummaryVO();
            vo.setId(o.getId());
            vo.setOrderNo(o.getOrderNo());
            vo.setStatus(o.getStatus());
            vo.setTotalAmount(o.getTotalAmount());
            vo.setPayAmount(o.getPayAmount());
            vo.setFreightAmount(o.getFreightAmount());
            vo.setCreatedAt(o.getCreatedAt());
            vo.setPayTime(o.getPayTime());
            vo.setCancelTime(o.getCancelTime());
            voList.add(vo);
        }

        return PageResult.of(voList, total, page, size);
    }

    @Override
    public OrderDetailVO getOrderDetail(Long userId, Long orderId) {
        OrderInfo order = orderInfoMapper.selectByIdAndUserId(orderId, userId);
        if (order == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");
        }

        List<OrderItem> items = orderItemMapper.selectByOrderId(orderId);
        List<OrderItemVO> itemVOList = new ArrayList<>();
        for (OrderItem oi : items) {
            OrderItemVO vo = new OrderItemVO();
            vo.setId(oi.getId());
            vo.setDishId(oi.getDishId());
            vo.setDishName(oi.getDishName());
            vo.setDishImage(oi.getDishImage());
            vo.setUnitPrice(oi.getUnitPrice());
            vo.setQuantity(oi.getQuantity());
            vo.setSubTotal(oi.getSubTotal());
            itemVOList.add(vo);
        }

        OrderDetailVO detail = new OrderDetailVO();
        detail.setId(order.getId());
        detail.setOrderNo(order.getOrderNo());
        detail.setStatus(order.getStatus());
        detail.setTotalAmount(order.getTotalAmount());
        detail.setPayAmount(order.getPayAmount());
        detail.setFreightAmount(order.getFreightAmount());
        detail.setCreatedAt(order.getCreatedAt());
        detail.setPayTime(order.getPayTime());
        detail.setCancelTime(order.getCancelTime());
        detail.setCancelReason(order.getCancelReason());
        detail.setReceiverName(order.getReceiverName());
        detail.setReceiverPhone(order.getReceiverPhone());
        detail.setReceiverAddress(order.getReceiverAddress());
        detail.setRemark(order.getRemark());
        detail.setItems(itemVOList);

        return detail;
    }

    private String generateOrderNo(Long userId) {
        // 简单一点：时间戳 + userId 后几位 + 随机
        String ts = String.valueOf(System.currentTimeMillis());
        String uid = userId == null ? "0" : String.valueOf(userId % 10000);
        String rand = UUID.randomUUID().toString().substring(0, 8).replace("-", "");
        return "FO" + ts + uid + rand;
    }
}
