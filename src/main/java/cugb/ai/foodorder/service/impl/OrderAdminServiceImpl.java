package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.entity.OrderInfo;
import cugb.ai.foodorder.entity.OrderItem;
import cugb.ai.foodorder.mapper.OrderInfoMapper;
import cugb.ai.foodorder.mapper.OrderItemMapper;
import cugb.ai.foodorder.service.OrderAdminService;
import cugb.ai.foodorder.vo.AdminOrderSummaryVO;
import cugb.ai.foodorder.vo.OrderDetailVO;
import cugb.ai.foodorder.vo.OrderItemVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderAdminServiceImpl implements OrderAdminService {

    private final OrderInfoMapper orderInfoMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderAdminServiceImpl(OrderInfoMapper orderInfoMapper,
                                 OrderItemMapper orderItemMapper) {
        this.orderInfoMapper = orderInfoMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public PageResult<AdminOrderSummaryVO> pageOrders(String orderNo, Long userId, Integer status,
                                                      LocalDateTime from, LocalDateTime to,
                                                      Integer page, Integer size) {
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;
        int offset = (page - 1) * size;

        Long total = orderInfoMapper.countAdmin(orderNo, userId, status, from, to);
        List<OrderInfo> list = orderInfoMapper.selectPageAdmin(orderNo, userId, status, from, to,
                offset, size);

        List<AdminOrderSummaryVO> voList = new ArrayList<>();
        for (OrderInfo o : list) {
            AdminOrderSummaryVO vo = new AdminOrderSummaryVO();
            vo.setId(o.getId());
            vo.setOrderNo(o.getOrderNo());
            vo.setUserId(o.getUserId());
            vo.setStatus(o.getStatus());
            vo.setTotalAmount(o.getTotalAmount());
            vo.setPayAmount(o.getPayAmount());
            vo.setCreatedAt(o.getCreatedAt());
            vo.setPayTime(o.getPayTime());
            voList.add(vo);
        }

        return PageResult.of(voList, total, page, size);
    }

    @Override
    public OrderDetailVO getOrderDetail(Long orderId) {
        OrderInfo order = orderInfoMapper.selectById(orderId);
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

    @Transactional
    @Override
    public void updateOrderStatus(Long orderId, Integer status, String cancelReason) {
        OrderInfo order = orderInfoMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");
        }

        // 这里只处理两种：取消(2) 和 完成(3)
        if (status == 3) {
            int updated = orderInfoMapper.updateStatusToCompleted(orderId);
            if (updated == 0) {
                throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "订单状态不允许标记为已完成");
            }
        } else if (status == 2) {
            int updated = orderInfoMapper.adminCancelOrder(orderId, cancelReason);
            if (updated == 0) {
                throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "订单状态不允许取消，或订单已取消");
            }
        } else {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "不支持的状态更新");
        }
    }
}
