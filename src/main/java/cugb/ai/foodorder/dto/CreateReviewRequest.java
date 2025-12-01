package cugb.ai.foodorder.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateReviewRequest {

    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @NotNull(message = "菜品ID不能为空")
    private Long dishId;

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分不能小于 1")
    @Max(value = 5, message = "评分不能大于 5")
    private Integer rating;

    @Size(max = 500, message = "评价内容不能超过 500 字")
    private String content;

    // 预留：图片URL列表
    // private List<String> images;
}
