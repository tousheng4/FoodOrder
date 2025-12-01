package cugb.ai.foodorder.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderInfo {

    private Long id;

    private String orderNo;

    private Long userId;

    /**
     * 订单状态：0=待支付，1=已支付，2=已取消，3=已完成
     */
    private Integer status;

    private BigDecimal totalAmount;

    private BigDecimal payAmount;

    private BigDecimal freightAmount;

    private LocalDateTime payTime;

    private LocalDateTime cancelTime;

    private String cancelReason;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private String remark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer deleted;
}
