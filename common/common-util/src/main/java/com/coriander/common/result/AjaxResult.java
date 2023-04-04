package com.coriander.common.result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 姓陈的
 * 2023/4/4 16:21
 * 封装统一返回对象
 */
public class AjaxResult extends HashMap<String,Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private static final String CODE_TAG = "code";
    /**
     *返回内容
     */
    private static final String MSG_TAG = "msg";
    /**
     * 返回数据
     */
    private static final String DATA_TAG = "data";


    public AjaxResult(){}

    /**
     *初始化一个新的对象
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code,String msg){
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
    }


    /**
     * 初始化一个新的对象
     * @param code 状态码
     * @param msg 返回内容
     * @param data 返回数据
     */
    public AjaxResult(int code,String msg,Object data){
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
        if(data != null){
            super.put(DATA_TAG,data);
        }
    }

    /**
     *返回成功消息
     * @return 成功消息
     */
    public static AjaxResult success(){
        return success("操作成功");
    }

    /**
     * 返回成功数据
     * @param data 数据
     * @return
     */
    public static AjaxResult success(Object data){
        return success("操作成功",data);
    }

    /**
     * 返回成功消息
     * @param msg 返回内容
     * @return
     */
    public static AjaxResult success(String msg){
        return success("操作成功",null);
    }

    /**
     * 返回成功消息
     * @param msg 返回内容
     * @param data 数据
     * @return
     */
    public static AjaxResult success(String msg,Object data){
        return new AjaxResult(HttpStatus.SUCCESS,msg,data);
    }


    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult warn(String msg)
    {
        return AjaxResult.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult warn(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.WARN, msg, data);
    }


    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     * @param msg 返回内容
     * @param data 返回数据
     * @return
     */
    public static AjaxResult error(String msg, Object data){
        return new AjaxResult(HttpStatus.ERROR,msg,data);
    }

    /**
     * 返回错误信息
     * @param code 状态码
     * @param msg 返回内容
     * @return
     */
    public static AjaxResult error(int code,String msg){
        return new AjaxResult(code,msg,null);
    }





    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }




}
