package com.coriander.common.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 姓陈的
 * 2023/4/8 15:27
 * 自定义异常处理类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CorianderException extends RuntimeException{

    private Integer code;
    private String msg;

}
