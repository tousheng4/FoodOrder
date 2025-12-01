package cugb.ai.foodorder.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminOrderSummaryVO {

    private Long id;

    private String orderNo;

    private Long userId;

    private Integer status;

    private BigDecimal totalAmount;

    private BigDecimal payAmount;

    private LocalDateTime createdAt;

    private LocalDateTime payTime;
}
