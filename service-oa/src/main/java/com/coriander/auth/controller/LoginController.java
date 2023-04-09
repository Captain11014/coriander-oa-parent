package com.coriander.auth.controller;

import com.coriander.common.core.BaseController;
import com.coriander.common.result.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 姓陈的
 * 2023/4/8 22:45
 */
@Api(tags = "登录模块")
@RestController
@RequestMapping("/admin/system/user")
public class LoginController extends BaseController {

    @ApiOperation("登录")
    @PostMapping("/login")
    public AjaxResult login(){
        Map<String, Object> map = new HashMap<>();
        map.put("token","admin");
        return success(map);
    }


    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public AjaxResult info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return success(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public AjaxResult logout(){
        return success();
    }





}
