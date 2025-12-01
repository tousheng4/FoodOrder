package cugb.ai.foodorder.service;

import cugb.ai.foodorder.dto.LoginRequest;
import cugb.ai.foodorder.dto.RegisterRequest;
import cugb.ai.foodorder.vo.LoginResponse;

public interface UserService {
    void register(RegisterRequest req);

    LoginResponse login(LoginRequest req);
}
