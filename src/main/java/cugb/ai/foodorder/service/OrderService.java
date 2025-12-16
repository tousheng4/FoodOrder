package cugb.ai.foodorder.service;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.CancelOrderRequest;
import cugb.ai.foodorder.dto.CreateOrderRequest;
import cugb.ai.foodorder.vo.OrderDetailVO;
import cugb.ai.foodorder.vo.OrderSummaryVO;

public interface OrderService {

    Long createOrder(Long userId, CreateOrderRequest req);

    void payOrder(Long userId, Long orderId);

    void cancelOrder(Long userId, Long orderId, CancelOrderRequest req);

    PageResult<OrderSummaryVO> listUserOrders(Long userId, Integer status, Integer page, Integer size);

    OrderDetailVO getOrderDetail(Long userId, Long orderId);

    /**
     * 同步支付状态（幂等）
     * 查询支付服务获取最新支付状态，如果已支付则更新本地订单
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return true=状态已同步为已支付，false=仍为待支付或同步失败
     */
    boolean syncPaymentStatus(Long userId, Long orderId);
}
