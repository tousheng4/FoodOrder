package cugb.ai.foodorder.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressCreateRequest {

    @NotBlank(message = "收货人姓名不能为空")
    private String receiverName;

    @NotBlank(message = "收货人电话不能为空")
    private String receiverPhone;

    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    /** 是否设为默认地址，默认为 false */
    private Boolean isDefault = false;
}
