package cugb.ai.foodorder.security;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserContext {

    private static final ThreadLocal<LoginUser> CONTEXT = new ThreadLocal<>();

    @Data
    @AllArgsConstructor
    public static class LoginUser {
        private Long userId;
        private Integer role;
    }

    public static void set(LoginUser user) {
        CONTEXT.set(user);
    }

    public static LoginUser get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
