package cugb.ai.foodorder.vo;

import lombok.Data;

@Data
public class AddressVO {

    private Long id;

    private String receiverName;

    private String receiverPhone;

    private String detailAddress;

    /** 0=否，1=是 */
    private Integer isDefault;
}
