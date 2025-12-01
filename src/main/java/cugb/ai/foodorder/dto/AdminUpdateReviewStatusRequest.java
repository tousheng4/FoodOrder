package cugb.ai.foodorder.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUpdateReviewStatusRequest {

    @NotNull(message = "状态不能为空")
    private Integer status; // 0=隐藏，1=显示
}
