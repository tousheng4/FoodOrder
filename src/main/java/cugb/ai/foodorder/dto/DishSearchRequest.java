package cugb.ai.foodorder.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishSearchRequest {
    // 分页参数
    private Integer page = 1;
    private Integer size = 10;

    // 搜索参数
    private String keyword;
    private Long categoryId;
    private Integer status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}