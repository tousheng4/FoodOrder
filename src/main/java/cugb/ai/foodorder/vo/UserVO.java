package cugb.ai.foodorder.vo;

import lombok.Data;

@Data
public class UserVO {

    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String avatar;

    private Integer role;

    private Integer status;
}
