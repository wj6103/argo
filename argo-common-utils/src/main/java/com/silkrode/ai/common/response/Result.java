package com.silkrode.ai.common.response;

import com.silkrode.ai.common.enums.ResultCode;
import lombok.Data;

@Data
public class Result<T> {
    private Integer status;
    private String message;
    private T data;

    public static Result<Object> suc(Object data) {
        Result<Object> result = new Result<>();
        result.setResultCode(ResultCode.SUCESSS);
        result.setData(data);
        return result;
    }

    public static Result fail(Integer status, String message) {
        Result<Object> result = new Result<>();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }

    private void setResultCode(ResultCode code) {
        this.status = code.code();
        this.message = code.message();
    }
}
