package cugb.ai.foodorder.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemVO {

    private Long cartItemId;

    private Long dishId;

    private String dishName;

    private String dishImage;

    private BigDecimal price;

    private Integer quantity;

    private Integer checked; // 0/1

    private BigDecimal subTotal; // price * quantity
}
