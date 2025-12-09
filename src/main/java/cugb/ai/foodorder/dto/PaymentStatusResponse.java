package cugb.ai.foodorder.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentStatusResponse {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付状态：PENDING(待支付)、PAID(已支付)、FAILED(支付失败)
     */
    private String status;

    /**
     * 支付时间
     */
    private LocalDateTime paidAt;

    /**
     * 支付宝交易号
     */
    private String tradeNo;
}
