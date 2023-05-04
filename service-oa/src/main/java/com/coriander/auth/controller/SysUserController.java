package com.coriander.auth.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coriander.auth.service.SysUserService;
import com.coriander.common.core.BaseController;
import com.coriander.common.result.AjaxResult;
import com.coriander.common.utils.StringUtil;
import com.coriander.common.utils.page.TableDataInfo;
import com.coriander.model.system.SysUser;
import com.coriander.vo.system.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 姓陈的
 * @since 2023-04-09
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 查询全部用户
     * @param sysUserQueryVo
     * @return
     */
    @ApiOperation("查询全部用户")
    @GetMapping("/list")
    public TableDataInfo list(SysUserQueryVo sysUserQueryVo){

        LambdaQueryWrapper<SysUser> qw = new LambdaQueryWrapper<>();

        if(StringUtil.isNotEmpty(sysUserQueryVo.getKeyword())){
            qw.like(SysUser::getUsername,sysUserQueryVo.getKeyword());
        }
        if(StringUtil.isNotEmpty(sysUserQueryVo.getCreateTimeBegin())){
            qw.ge(SysUser::getCreateTime,sysUserQueryVo.getCreateTimeBegin());
        }
        if(StringUtil.isNotEmpty(sysUserQueryVo.getCreateTimeEnd())){
            qw.le(SysUser::getCreateTime,sysUserQueryVo.getCreateTimeEnd());
        }

        startPage();
        List<SysUser> list = sysUserService.list(qw);
        return getDataTable(list);

    }

    /**
     * 根据Id获取用户信息
     * @param id
     * @return
     */
    @ApiOperation("根据Id获取用户信息")
    @GetMapping("/getSysUserById/{id}")
    public AjaxResult getSysUserById(@PathVariable Integer id){
        SysUser sysUser = sysUserService.getById(id);
        return success(sysUser);
    }

    @ApiOperation("添加用户")
    @PostMapping("/addSysUser")
    public AjaxResult addSysUser(@RequestBody SysUser sysUser){
        boolean b = sysUserService.save(sysUser);
        return toAjax(b);
    }


    @ApiOperation("修改用户")
    @PostMapping("/updateSysUser")
    public AjaxResult updateSysUser(@RequestBody SysUser sysUser){
        boolean b = sysUserService.updateById(sysUser);
        return toAjax(b);
    }


    @ApiOperation("删除用户")
    @PostMapping("/batchRemoveSysUser/{ids}")
    public AjaxResult batchRemoveSysUser(@PathVariable Long[] ids){
        List<Long> idList = Arrays.asList(ids);
        boolean b = sysUserService.removeByIds(idList);
        return toAjax(b);
    }


    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public AjaxResult updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        sysUserService.updateStatus(id, status);
        return success();
    }



}
