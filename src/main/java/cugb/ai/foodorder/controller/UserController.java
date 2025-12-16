package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.UpdateUserInfoRequest;
import cugb.ai.foodorder.security.UserContext;
import cugb.ai.foodorder.service.UserService;
import cugb.ai.foodorder.vo.UserVO;
import org.springframework.web.bind.annotation.*;

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

    private Long currentUserId() {
        UserContext.LoginUser loginUser = UserContext.get();
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录");
        }
        return loginUser.getUserId();
    }
    /**
     * 更新用户信息（昵称、头像）
     * 支持表单提交：nickname（文本）+ avatarFile（文件）
     */
    @PutMapping("/info")
    public Result<Void> updateUserInfo(UpdateUserInfoRequest request) {
        // 参数校验：至少要提供一个更新字段
        String nickname = request.getNickname();
        if ((nickname == null || nickname.trim().isEmpty()) 
                && (request.getAvatarFile() == null || request.getAvatarFile().isEmpty())) {
            return Result.error(ErrorCode.PARAM_ERROR.getCode(), "至少需要提供昵称或头像中的一个进行更新");
        }

        userService.updateUserInfo(currentUserId(), request);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<UserVO> getUserInfo() {
        Long userId = currentUserId();
        return Result.success(userService.getUserInfo(userId));
    }
}
