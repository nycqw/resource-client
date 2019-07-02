package com.eden.resource.client.common.dto;

import com.eden.resource.client.common.constants.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public class Result<T> implements Serializable {

    @Getter
    private Integer code;
    @Getter
    private String message;
    @Getter
    private T data;

    public static <T> Result<T> success() {
        return success("成功", null);
    }

    public static <T> Result<T> success(T data) {
        return success("成功", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> fail(String message) {
        return fail(ResultEnum.SYSTEM_ERROR.getCode(), message, null);
    }

    public static <T> Result<T> fail(String message,T data) {
        return fail(ResultEnum.SYSTEM_ERROR.getCode(), message, data);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return fail(code, message, null);
    }

    public static <T> Result<T> fail(Integer code, String message, T data) {
        return new Result(code, message, data);
    }

}
