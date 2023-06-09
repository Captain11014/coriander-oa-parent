package com.coriander.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.coriander.model.system.SysMenu;
import com.coriander.vo.system.AssignMenuVo;
import com.coriander.vo.system.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 姓陈的
 * @since 2023-05-04
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单列表接口
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 删除菜单
     * @param id
     */
    void removeMenuById(Long id);

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 给角色分配权限
     * @param assignMenuVo
     */
    void doAssign(AssignMenuVo assignMenuVo);

    /**
     * 查询数据库动态构建路由结构，进行显示
     * @return
     */
    List<RouterVo> findUserMenuListByUserId(Long userId);

    /**
     * 根据用户id获取用户可操作按钮列表
     * @param userId
     * @return
     */
    List<String> findMenuPermsByUserId(Long userId);
}
