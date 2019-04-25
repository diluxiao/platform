package com.haiwen.platform.portal.interceptor;

import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlatformException.class)
    public ResultData<?> handPlatformException(PlatformException ex) {
        log.error(ex.getMessage(), ex);
        return ResultData.errot();
    }

}
