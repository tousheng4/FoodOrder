package cugb.ai.foodorder.security;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;

public class AdminAuth {

    public static UserContext.LoginUser requireAdmin() {
        UserContext.LoginUser user = UserContext.get();
        if (user == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录");
        }
        if (user.getRole() == null || user.getRole() != 1) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "需要管理员权限");
        }
        return user;
    }
}
