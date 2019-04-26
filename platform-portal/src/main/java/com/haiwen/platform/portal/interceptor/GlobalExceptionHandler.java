package com.haiwen.platform.portal.interceptor;

import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData<?> handPlatformException(Throwable e) {
        log.error("系统异常：ex", e);
        if(e instanceof PlatformException) {
            return ResultData.errot();
        }
        return ResultData.errot();
    }

}
