package cugb.ai.foodorder.dto;

import lombok.Data;

@Data
public class CancelOrderRequest {

    /** 取消原因，可选 */
    private String reason;
}