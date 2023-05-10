package com.coriander.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coriander.auth.service.SysMenuService;
import com.coriander.auth.service.SysUserService;
import com.coriander.common.config.exception.CorianderException;
import com.coriander.common.core.BaseController;
import com.coriander.common.result.AjaxResult;
import com.coriander.common.utils.MD5;
import com.coriander.common.utils.jwt.JwtHelper;
import com.coriander.model.system.SysUser;
import com.coriander.vo.system.LoginVo;
import com.coriander.vo.system.RouterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 姓陈的
 * 2023/4/8 22:45
 */
@Api(tags = "登录模块")
@RestController
@RequestMapping("/admin/system/index")
public class LoginController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginVo loginVo){
        Map<String, Object> map = new HashMap<>();
//        map.put("token","admin-token");

        String username = loginVo.getUsername();
        LambdaQueryWrapper<SysUser> qw = new LambdaQueryWrapper();
        qw.eq(SysUser::getUsername,username);
        SysUser sysUser = sysUserService.getOne(qw);

        if(sysUser == null){
            throw new CorianderException(201,"用户不存在");
        }

        //判断密码是否相等
        if(!sysUser.getPassword().equals(MD5.encrypt(loginVo.getPassword()))){
            throw new CorianderException(201,"用户名或密码错误");
        }

        //判断用户是否被禁用
        if(sysUser.getStatus().intValue() == 0){ //0被禁用
            throw new CorianderException(201,"用户被禁用");
        }

        //生成token
        String token = JwtHelper.createToken(sysUser.getId(),sysUser.getUsername());

        map.put("token",token);

        return success(map);
    }


    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public AjaxResult info(HttpServletRequest request) {

        //1.从请求头获取用户信息
        String token = request.getHeader("token");
        //2.从token字符串获取用户id
        Long userId = JwtHelper.getUserId(token);
        //3.根据用户id查询数据库，获取用户信息
        SysUser sysUser = sysUserService.getById(userId);
        //4.根据用户id获取用户可以操作的菜单列表
        //查询数据库动态构建路由结构，进行显示
        List<RouterVo> routerVoList = sysMenuService.findUserMenuListByUserId(userId);
        //5.根据用户id获取用户可操作按钮列表
        List<String> permsList = sysMenuService.findMenuPermsByUserId(userId);


        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name",sysUser.getName());
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        map.put("buttons", permsList);
        map.put("routers", routerVoList);


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
