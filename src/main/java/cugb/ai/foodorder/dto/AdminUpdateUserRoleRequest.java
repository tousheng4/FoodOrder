package cugb.ai.foodorder.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUpdateUserRoleRequest {

    @NotNull(message = "角色不能为空")
    private Integer role; // 0=普通用户，1=管理员
}
