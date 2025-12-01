package cugb.ai.foodorder.service;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.vo.AdminOrderSummaryVO;
import cugb.ai.foodorder.vo.OrderDetailVO;

import java.time.LocalDateTime;

public interface OrderAdminService {

    PageResult<AdminOrderSummaryVO> pageOrders(String orderNo, Long userId, Integer status,
                                               LocalDateTime from, LocalDateTime to,
                                               Integer page, Integer size);

    OrderDetailVO getOrderDetail(Long orderId);

    void updateOrderStatus(Long orderId, Integer status, String cancelReason);
}
