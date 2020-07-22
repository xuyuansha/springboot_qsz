package com.qsz.bmss.model;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(0),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未登陆
    ACCESS_ERROR(403),//不允许访问
    LOGIN_ERROR(500),//登陆错误
    USER_INSERT_ERROR(1001),
    USER_ROlE_INSERT_ERROR(1002),
    USER_DELETE_ERROR(1003),
    USER_STATUS_SET_ERROR(1004),
    ROLE_DELETE_ERROR(1005);

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
