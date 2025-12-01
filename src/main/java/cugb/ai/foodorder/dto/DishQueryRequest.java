package cugb.ai.foodorder.dto;

import lombok.Data;

@Data
public class DishQueryRequest {

    private Integer page = 1;
    private Integer size = 10;

    private Long categoryId;
    private String keyword;
}
