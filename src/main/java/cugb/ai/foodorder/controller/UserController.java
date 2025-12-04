package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.UpdateUserInfoRequest;
import cugb.ai.foodorder.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息管理控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 更新用户信息（昵称、头像）
     * 支持表单提交：nickname（文本）+ avatarFile（文件）
     */
    @PutMapping("/info")
    public Result<Void> updateUserInfo(
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) MultipartFile avatarFile) {

        // 参数校验：至少要提供一个更新字段
        if ((nickname == null || nickname.trim().isEmpty()) && (avatarFile == null || avatarFile.isEmpty())) {
            return Result.error(ErrorCode.PARAM_ERROR.getCode(), "至少需要提供昵称或头像中的一个进行更新");
        }

        UpdateUserInfoRequest request = new UpdateUserInfoRequest();
        request.setNickname(nickname);
        request.setAvatarFile(avatarFile);

        userService.updateUserInfo(request);
        return Result.success();
    }
}
