package cugb.ai.foodorder.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressUpdateRequest {

    @NotBlank(message = "收货人姓名不能为空")
    private String receiverName;

    @NotBlank(message = "收货人电话不能为空")
    private String receiverPhone;

    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    /** 是否设为默认地址，允许修改 */
    private Boolean isDefault = false;
}
