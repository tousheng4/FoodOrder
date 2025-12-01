package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.CancelOrderRequest;
import cugb.ai.foodorder.dto.CreateOrderRequest;
import cugb.ai.foodorder.security.UserContext;
import cugb.ai.foodorder.service.OrderService;
import cugb.ai.foodorder.vo.OrderDetailVO;
import cugb.ai.foodorder.vo.OrderSummaryVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private Long currentUserId() {
        UserContext.LoginUser loginUser = UserContext.get();
        if (loginUser == null) {
            // 正常来说不会走到这里，因为拦截器已经拦了
            throw new RuntimeException("未登录");
        }
        return loginUser.getUserId();
    }

    /**
     * 创建订单（从当前用户购物车）
     */
    @PostMapping
    public Result<Long> createOrder(@Valid @RequestBody CreateOrderRequest req) {
        Long userId = currentUserId();
        Long orderId = orderService.createOrder(userId, req);
        return Result.success(orderId);
    }

    /**
     * 模拟支付
     */
    @PostMapping("/{id}/pay")
    public Result<Void> pay(@PathVariable("id") Long id) {
        Long userId = currentUserId();
        orderService.payOrder(userId, id);
        return Result.success();
    }

    /**
     * 取消订单
     */
    @PostMapping("/{id}/cancel")
    public Result<Void> cancel(@PathVariable("id") Long id,
                               @RequestBody(required = false) CancelOrderRequest req) {
        Long userId = currentUserId();
        orderService.cancelOrder(userId, id, req);
        return Result.success();
    }

    /**
     * 我的订单列表
     * /api/orders?page=1&size=10&status=0
     */
    @GetMapping
    public Result<PageResult<OrderSummaryVO>> list(@RequestParam(required = false) Integer status,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        Long userId = currentUserId();
        PageResult<OrderSummaryVO> result = orderService.listUserOrders(userId, status, page, size);
        return Result.success(result);
    }

    /**
     * 订单详情
     */
    @GetMapping("/{id}")
    public Result<OrderDetailVO> detail(@PathVariable("id") Long id) {
        Long userId = currentUserId();
        OrderDetailVO detail = orderService.getOrderDetail(userId, id);
        return Result.success(detail);
    }
}
