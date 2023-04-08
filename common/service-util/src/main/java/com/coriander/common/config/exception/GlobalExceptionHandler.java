package com.coriander.common.config.exception;

import com.coriander.common.result.AjaxResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 姓陈的
 * 2023/4/8 15:14
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult error(Exception e){
        e.printStackTrace();
        return AjaxResult.error();
    }


    @ExceptionHandler(CorianderException.class)
    @ResponseBody
    public AjaxResult error(CorianderException e){
        e.printStackTrace();
        return AjaxResult.error(e.getCode(),e.getMsg());
    }


}
