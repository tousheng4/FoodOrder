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
}
