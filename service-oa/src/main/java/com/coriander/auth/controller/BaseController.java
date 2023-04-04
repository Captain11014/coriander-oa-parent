package com.coriander.auth.controller;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 姓陈的
 * 2023/4/4 23:56
 */
public class BaseController {


    public void startPage(){

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;

        HttpServletRequest request = requestAttributes.getRequest();

        System.out.println(request.getRequestURI());

        String pageNum = request.getParameter("pageNum");
        System.out.println(pageNum);


    }


}
