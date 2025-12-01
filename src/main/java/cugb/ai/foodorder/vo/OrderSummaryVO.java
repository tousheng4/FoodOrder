package cugb.ai.foodorder.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderSummaryVO {

    private Long id;

    private String orderNo;

    private Integer status;

    private BigDecimal totalAmount;

    private BigDecimal payAmount;

    private BigDecimal freightAmount;

    private LocalDateTime createdAt;

    private LocalDateTime payTime;

    private LocalDateTime cancelTime;
}
