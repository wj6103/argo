package com.silkrode.ai.common.handler;

import com.silkrode.ai.common.enums.ResultCode;
import com.silkrode.ai.common.response.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable ex, HttpServletRequest request) {
        ErrorResult errorResult = ErrorResult.fail(ResultCode.SYSTEM_ERROR, ex);
        log.error("Request URL: {}, System exception: {}", request.getRequestURI(), ex.getStackTrace());
        return errorResult;
    }

    /**
     * validator
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String msgs = this.handle(ex.getBindingResult().getFieldErrors());
        ErrorResult error = ErrorResult.fail(ResultCode.PARAM_IS_INVALID, ex, msgs);
        log.warn("Request URL: {}, Parameter validation exception: {}", request.getRequestURI(), msgs);
        return error;
    }

    private String handle(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();
        for (FieldError obj : fieldErrors) {
            sb.append(obj.getField());
            sb.append("=[");
            sb.append(obj.getDefaultMessage());
            sb.append("]  ");
        }
        return sb.toString();
    }

    /**
     * Assert
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResult error = ErrorResult.fail(ResultCode.DATA_IS_INVALID, ex, ex.getMessage());
        log.warn("Request URL: {}, Data validation exception: {}", request.getRequestURI(), ex.getMessage());
        return error;
    }
}
