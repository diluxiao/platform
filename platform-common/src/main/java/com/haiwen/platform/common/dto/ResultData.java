package com.haiwen.platform.common.dto;

import com.haiwen.platform.common.exception.CodeMsg;

import java.io.Serializable;

/**
 * 返回结果封装类
 **/
public final class ResultData<T> implements Serializable {

    private Integer code;

    private String msg;

    private T result;

    private ResultData(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    private ResultData(CodeMsg codeMsg, T data) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
        this.result = data;
    }

    public static <T>ResultData<T> success() {
        return new ResultData<>(CodeMsg.SUCCESS);
    }

    public static <T>ResultData<T> errot() {
        return new ResultData<>(CodeMsg.ERROR);
    }

    public static <T>ResultData<T> success(T data) {
        return new ResultData<>(CodeMsg.SUCCESS,data);
    }
}