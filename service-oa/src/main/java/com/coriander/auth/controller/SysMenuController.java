package com.coriander.auth.controller;


import com.coriander.auth.service.SysMenuService;
import com.coriander.common.core.BaseController;
import com.coriander.common.result.AjaxResult;
import com.coriander.model.system.SysMenu;
import com.coriander.vo.system.AssignMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 * @author 姓陈的
 * @since 2023-05-04
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController extends BaseController {

    @Resource
    private SysMenuService sysMenuService;


    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public AjaxResult toAssign(@PathVariable Long roleId) {
        List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
        return success(list);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public AjaxResult doAssign(@RequestBody AssignMenuVo assignMenuVo) {
        sysMenuService.doAssign(assignMenuVo);
        return success();
    }

    /**
     * 菜单列表接口
     * @return
     */
    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public AjaxResult findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return success(list);
    }


    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public AjaxResult save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return success();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public AjaxResult updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return success();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        sysMenuService.removeMenuById(id);
        return success();
    }

}
