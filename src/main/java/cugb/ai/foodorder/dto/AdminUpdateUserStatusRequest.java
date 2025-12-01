package cugb.ai.foodorder.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUpdateUserStatusRequest {

    @NotNull(message = "状态不能为空")
    private Integer status; // 0=禁用，1=正常
}
