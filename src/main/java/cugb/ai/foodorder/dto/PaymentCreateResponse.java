package cugb.ai.foodorder.dto;

import lombok.Data;

@Data
public class PaymentCreateResponse {

    /**
     * 支付记录ID
     */
    private String paymentId;

    /**
     * 支付宝支付表单HTML
     */
    private String formHtml;
}
