package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.client.PaymentClient;
import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.CancelOrderRequest;
import cugb.ai.foodorder.dto.CreateOrderRequest;
import cugb.ai.foodorder.dto.PaymentCreateResponse;
import cugb.ai.foodorder.dto.PaymentStatusResponse;
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
    private final PaymentClient paymentClient;

    public OrderController(OrderService orderService, PaymentClient paymentClient) {
        this.orderService = orderService;
        this.paymentClient = paymentClient;
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
     * 仅返回本地数据库中的订单状态，不主动同步支付服务
     * 支付状态同步请使用 /payment-status 接口（支付完成后轮询）
     */
    @GetMapping("/{id}")
    public Result<OrderDetailVO> detail(@PathVariable("id") Long id) {
        Long userId = currentUserId();
        OrderDetailVO detail = orderService.getOrderDetail(userId, id);
        return Result.success(detail);
    }

    /**
     * 支付宝支付（调用外部支付服务）
     * 返回支付表单HTML，前端渲染后自动跳转支付宝
     */
    @PostMapping("/{id}/alipay")
    public Result<PaymentCreateResponse> alipay(@PathVariable("id") Long id) {
        Long userId = currentUserId();
        OrderDetailVO order = orderService.getOrderDetail(userId, id);

        // 调用支付服务创建支付
        // 传递 orderId 和 orderNo，支付服务会存储两者的对应关系
        // 支付完成后会重定向到 /order/{orderId}
        PaymentCreateResponse response = paymentClient.createPayment(
                order.getId(),
                order.getOrderNo(),
                order.getPayAmount(),
                "订单支付-" + order.getOrderNo()
        );
        return Result.success(response);
    }

    /**
     * 查询支付状态
     * 前端轮询此接口确认支付结果
     * <p>
     * 数据一致性说明：
     * 1. 调用 syncPaymentStatus 同步支付状态（幂等）
     * 2. 查询最新订单状态返回给前端
     * 3. 支付服务查询失败时，返回本地订单状态
     */
    @GetMapping("/{id}/payment-status")
    public Result<PaymentStatusResponse> paymentStatus(@PathVariable("id") Long id) {
        Long userId = currentUserId();

        // 1. 先获取订单信息（用于获取orderNo）
        OrderDetailVO order = orderService.getOrderDetail(userId, id);

        // 2. 同步支付状态（幂等），失败不影响返回
        orderService.syncPaymentStatus(userId, id);

        // 3. 重新获取最新订单状态
        OrderDetailVO latestOrder = orderService.getOrderDetail(userId, id);

        // 4. 构建响应，根据本地订单状态返回
        PaymentStatusResponse response = new PaymentStatusResponse();
        response.setOrderNo(latestOrder.getOrderNo());

        // 将本地订单状态映射为支付状态
        // 订单状态：0=待支付，1=已支付，2=已取消，3=已完成
        switch (latestOrder.getStatus()) {
            case 1:
            case 3:
                response.setStatus("PAID");
                response.setPaidAt(latestOrder.getPayTime());
                break;
            case 2:
                response.setStatus("FAILED");
                break;
            default:
                response.setStatus("PENDING");
        }

        return Result.success(response);
    }
}
