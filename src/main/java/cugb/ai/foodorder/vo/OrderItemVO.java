package cugb.ai.foodorder.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemVO {

    private Long id;

    private Long dishId;

    private String dishName;

    private String dishImage;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal subTotal;
}
