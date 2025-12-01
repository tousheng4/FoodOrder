package cugb.ai.foodorder.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminUserVO {

    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String avatar;

    private Integer role;

    private Integer status;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;
}
