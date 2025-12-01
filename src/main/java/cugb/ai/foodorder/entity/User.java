package cugb.ai.foodorder.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;

    private String username;

    private String passwordHash;

    private String nickname;

    private String phone;

    private String avatar;

    /** 0=普通用户，1=管理员 */
    private Integer role;

    /** 0=禁用，1=正常 */
    private Integer status;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
