package cugb.ai.foodorder.controller.admin;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.AdminUpdateOrderStatusRequest;
import cugb.ai.foodorder.security.AdminAuth;
import cugb.ai.foodorder.service.OrderAdminService;
import cugb.ai.foodorder.vo.AdminOrderSummaryVO;
import cugb.ai.foodorder.vo.OrderDetailVO;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final OrderAdminService orderAdminService;

    public AdminOrderController(OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }

    @GetMapping
    public Result<PageResult<AdminOrderSummaryVO>> pageOrders(
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        AdminAuth.requireAdmin();
        PageResult<AdminOrderSummaryVO> result =
                orderAdminService.pageOrders(orderNo, userId, status, from, to, page, size);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<OrderDetailVO> detail(@PathVariable Long id) {
        AdminAuth.requireAdmin();
        OrderDetailVO detail = orderAdminService.getOrderDetail(id);
        return Result.success(detail);
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id,
                                     @Valid @RequestBody AdminUpdateOrderStatusRequest req) {
        AdminAuth.requireAdmin();
        orderAdminService.updateOrderStatus(id, req.getStatus(), req.getCancelReason());
        return Result.success();
    }
}
