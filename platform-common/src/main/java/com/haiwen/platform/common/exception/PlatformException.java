package com.haiwen.platform.common.exception;

/**
 * 后台异常
 **/
public class PlatformException extends RuntimeException {

    private int code;
    private String msg;
    private CodeMsg codeMsg;

    public PlatformException(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public PlatformException(String msg) {
        this.msg = msg;
    }

    public PlatformException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
