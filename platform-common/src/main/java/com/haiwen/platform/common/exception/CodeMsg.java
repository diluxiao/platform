package com.haiwen.platform.common.exception;

import lombok.Data;

import java.io.Serializable;

/**
 * 错误消息定义
 **/
@Data
public final class CodeMsg implements Serializable {

    private int code;
    private String msg;

    // 通用异常
    public static final CodeMsg SUCCESS = new CodeMsg(0, "调用成功");
    public static final CodeMsg ERROR = new CodeMsg(-1, "系统异常");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(100100, "服务端异常:%s");
    public static final CodeMsg BIND_ERROR = new CodeMsg(100101, "参数校验异常:%s");
    public static final CodeMsg LOCK_ERROR = new CodeMsg(100102, "获取redis锁异常:%s");
    public static final CodeMsg DUBBO_CALL_ERROR = new CodeMsg(100103,"%s服务调用失败(Code:%s)%s");

    /**
     * 构造方法
     * @param code
     * @param msg
     */
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 加入自定义msg
     * @param args
     * @return
     */
    public CodeMsg fillArgs(Object... args) {
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

}
