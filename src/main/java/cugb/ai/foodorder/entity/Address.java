package cugb.ai.foodorder.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Address {

    private Long id;

    private Long userId;

    private String receiverName;

    private String receiverPhone;

    private String detailAddress;

    /** 0=否，1=是 */
    private Integer isDefault;

    private Integer deleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
