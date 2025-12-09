package cugb.ai.foodorder.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOrderRequest {

    /** 可选：前端直接传 addressId，则优先按地址表填充 */
    private Long addressId;

    @NotBlank(message = "收货人姓名不能为空")
    private String receiverName;

    @NotBlank(message = "收货人电话不能为空")
    private String receiverPhone;

    @NotBlank(message = "收货地址不能为空")
    private String receiverAddress;

    /** 用户备注，可选 */
    private String remark;
}

