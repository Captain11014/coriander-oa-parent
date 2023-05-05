package com.coriander.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coriander.auth.mapper.SysMenuMapper;
import com.coriander.auth.mapper.SysRoleMenuMapper;
import com.coriander.auth.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coriander.auth.util.MenuHelper;
import com.coriander.common.config.exception.CorianderException;
import com.coriander.common.utils.StringUtil;
import com.coriander.model.system.SysMenu;
import com.coriander.model.system.SysRoleMenu;
import com.coriander.vo.system.AssignMenuVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 姓陈的
 * @since 2023-05-04
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 菜单列表接口
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {

        List<SysMenu> list = baseMapper.selectList(null);
        if(CollectionUtils.isEmpty(list)) return null;
        List<SysMenu> sysMenus = MenuHelper.buildTree(list);
        return sysMenus;
    }

    /**
     * 删除菜单
     * @param id
     */
    @Override
    public void removeMenuById(Long id) {
        LambdaQueryWrapper<SysMenu> qw = new LambdaQueryWrapper<>();
        qw.eq(SysMenu::getParentId,id);
        int count = baseMapper.selectCount(qw);
        if(count > 0){
            throw new CorianderException(201,"菜单不能删除");
        }
        baseMapper.deleteById(id);

    }

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //全部菜单列表
        List<SysMenu> allSysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));

        //根据角色id获取角色菜单权限
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        //转换给角色id与角色权限对应Map对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());

        allSysMenuList.forEach(sysMenu -> {
            if (menuIdList.contains(sysMenu.getId())) {
                sysMenu.setSelect(true);
            } else {
                sysMenu.setSelect(false);
            }
        });

        List<SysMenu> sysMenuList = MenuHelper.buildTree(allSysMenuList);
        return sysMenuList;
    }

    /**
     * 给角色分配权限
     * @param assignMenuVo
     */
    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {

        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, assignMenuVo.getRoleId()));

        for (Long menuId : assignMenuVo.getMenuIdList()) {
            if (StringUtil.isNull(menuId)) continue;
            SysRoleMenu rolePermission = new SysRoleMenu();
            rolePermission.setRoleId(assignMenuVo.getRoleId());
            rolePermission.setMenuId(menuId);
            sysRoleMenuMapper.insert(rolePermission);
        }

    }
}
