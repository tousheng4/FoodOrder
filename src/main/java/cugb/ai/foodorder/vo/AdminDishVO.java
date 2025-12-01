package cugb.ai.foodorder.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminDishVO {

    private Long id;

    private String name;

    private String description;

    private String image;

    private BigDecimal price;

    private Long categoryId;

    private Integer status;

    private LocalDateTime createdAt;
}
