package cugb.ai.foodorder.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItem {

    private Long id;

    private Long orderId;

    private Long dishId;

    private String dishName;

    private String dishImage;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal subTotal;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
