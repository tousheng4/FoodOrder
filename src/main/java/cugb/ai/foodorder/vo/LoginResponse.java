package cugb.ai.foodorder.vo;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;

    private UserVO user;
}
