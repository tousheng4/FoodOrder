package cugb.ai.foodorder.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUpdateOrderStatusRequest {

    @NotNull(message = "状态不能为空")
    private Integer status; // 1=已支付, 2=已取消, 3=已完成

    private String cancelReason; // 当 status=2 时可填
}
