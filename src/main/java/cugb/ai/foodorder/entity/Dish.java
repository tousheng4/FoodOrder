package cugb.ai.foodorder.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Dish {
    private Long id;

    private Long categoryId;

    private String name;

    private BigDecimal price;

    private String image;

    private String description;

    /** 0=下架，1=上架 */
    private Integer status;

    private Integer stock;

    private Integer sales;

    private Integer deleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
