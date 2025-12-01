package cugb.ai.foodorder.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCartItemRequest {

    @NotNull(message = "菜品ID不能为空")
    private Long dishId;

    @Min(value = 1, message = "数量至少为 1")
    private Integer quantity = 1;
}
