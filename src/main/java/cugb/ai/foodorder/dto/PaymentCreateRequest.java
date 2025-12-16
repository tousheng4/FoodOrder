package cugb.ai.foodorder.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentCreateRequest {

    /**
     * 主服务的订单ID（用于支付完成后重定向到前端）
     */
    private Long orderId;

    /**
     * 订单号（用于支付宝交易）
     */
    private String orderNo;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 订单标题
     */
    private String subject;
}
