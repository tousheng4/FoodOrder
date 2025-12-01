package cugb.ai.foodorder.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailVO {

    private Long id;

    private String orderNo;

    private Integer status;

    private BigDecimal totalAmount;

    private BigDecimal payAmount;

    private BigDecimal freightAmount;

    private LocalDateTime createdAt;

    private LocalDateTime payTime;

    private LocalDateTime cancelTime;

    private String cancelReason;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private String remark;

    private List<OrderItemVO> items;
}
