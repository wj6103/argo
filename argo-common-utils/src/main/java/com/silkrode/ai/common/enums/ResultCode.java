package com.silkrode.ai.common.enums;


public enum ResultCode {

    SUCESSS(200, "ok"),
    PARAM_IS_INVALID(400, "Invalid parameter"),
    DATA_IS_INVALID(400, "Invalid data"),
    UNAUTHORIZED(401, "Invalid token or token is expired"),
    FORBIDDEN(403, "Your token has no permission to do this operation"),
    SYSTEM_ERROR(500, "System error");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
