package com.coriander.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 姓陈的
 * 2023/4/5 10:24
 * 封装Servlet工具类
 */
public class ServletUtil {

    public static ServletRequestAttributes getRequestAttributes(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }


    /**
     * 获取request
     * @return
     */
    public static HttpServletRequest getRequest(){
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     * @return
     */
    public static HttpServletResponse getResponse(){
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession(){
        return getRequest().getSession();
    }


    /**
     * 获取String参数
     * @param name
     * @return
     */
    public static String getParameter(String name){
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     * @param name 参数名
     * @param defaultValue 默认值
     * @return
     */
    public static String getParameter(String name,String defaultValue){
        return Convert.toStr(getRequest().getParameter(name),defaultValue);
    }

    /**
     * 获取Integer参数
     * @param name
     * @return
     */
    public static Integer getParameterToInt(String name){
        return Convert.toInt(getRequest().getParameter(name));
    }


    /**
     * 获取Integer参数
     * @param name
     * @return
     */
    public static Integer getParameterToInt(String name,Integer defaultVale){
        return Convert.toInt(getRequest().getParameter(name),defaultVale);
    }


    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(String name)
    {
        return Convert.toBool(getRequest().getParameter(name));
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(String name, Boolean defaultValue)
    {
        return Convert.toBool(getRequest().getParameter(name), defaultValue);
    }






}
