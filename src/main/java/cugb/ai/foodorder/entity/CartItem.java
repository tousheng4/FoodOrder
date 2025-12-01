package cugb.ai.foodorder.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartItem {

    private Long id;

    private Long userId;

    private Long dishId;

    private Integer quantity;

    /** 0=未选中，1=选中 */
    private Integer checked;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
