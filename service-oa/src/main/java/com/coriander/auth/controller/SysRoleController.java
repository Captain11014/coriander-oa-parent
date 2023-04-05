package com.coriander.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coriander.auth.service.SysRoleService;
import com.coriander.common.core.BaseController;
import com.coriander.common.result.AjaxResult;
import com.coriander.common.utils.page.TableDataInfo;
import com.coriander.model.system.SysRole;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 姓陈的
 * 2023/4/4 16:07
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;


//    @GetMapping("/findAll")
//    public List<SysRole> findAll(){
//        List<SysRole> list = sysRoleService.list();
//        return list;
//    }

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("/findAll")
    public AjaxResult findAll(){
        List<SysRole> list = sysRoleService.list();
        return success(list);
    }

    @ApiOperation(value = "测试分页接口")
    @GetMapping("/test")
    public TableDataInfo test(){
        QueryWrapper qw = new QueryWrapper();
        qw.orderByDesc("id");
        startPage();
        List<SysRole> list = sysRoleService.list(qw);
        return getDataTable(list);
    }


}
