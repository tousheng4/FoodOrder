package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.LoginRequest;
import cugb.ai.foodorder.dto.RegisterRequest;
import cugb.ai.foodorder.service.UserService;
import cugb.ai.foodorder.vo.LoginResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest req) {
        userService.register(req);
        return Result.success();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        LoginResponse resp = userService.login(req);
        return Result.success(resp);
    }
}
