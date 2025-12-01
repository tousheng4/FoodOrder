package cugb.ai.foodorder.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAM_ERROR(40001, "参数错误"),
    UNAUTHORIZED(40100, "未登录"),
    FORBIDDEN(40300, "没有权限"),

    USER_EXISTS(40010, "用户名已存在"),
    USER_NOT_FOUND(40011, "用户不存在"),
    PASSWORD_ERROR(40012, "密码错误"),

    DISH_NOT_FOUND(40020, "菜品不存在"),
    DISH_OFF_SHELF(40021, "菜品已下架"),
    CART_ITEM_NOT_FOUND(40030, "购物车中没有该商品"),
    CART_EMPTY(40031, "购物车为空，无法下单"),
    ORDER_NOT_FOUND(40040, "订单不存在"),
    ORDER_STATUS_ERROR(40041, "当前订单状态不允许该操作"),

    ADDRESS_NOT_FOUND(40050, "地址不存在"),

    REVIEW_ALREADY_EXISTS(40060, "已评价过该菜品"),
    ORDER_ITEM_NOT_FOUND(40042, "订单中没有该菜品"),

    SERVER_ERROR(50000, "系统异常");

    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
