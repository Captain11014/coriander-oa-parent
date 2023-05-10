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
import com.coriander.vo.system.MetaVo;
import com.coriander.vo.system.RouterVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private SysMenuMapper sysMenuMapper;

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

    /**
     * 查询数据库动态构建路由结构，进行显示
     * @return
     */
    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {

        List<SysMenu> sysMenuList = null;

        //1.判断当前用户是否管理员 userId = 1是管理员
        if(userId.longValue() == 1){
            //如果是管理员，查询所有菜单列表
            LambdaQueryWrapper<SysMenu> qw = new LambdaQueryWrapper<>();
            qw.eq(SysMenu::getStatus,1);
            qw.orderByAsc(SysMenu::getSortValue);
            sysMenuList = baseMapper.selectList(qw);
        }else{
            //2.如果不是管理员，根据userId查询可操作菜单列表
            //多表关联查询：用户角色关系表、角色菜单关系表、菜单表
            sysMenuList = baseMapper.findUserMenuListByUserId(userId);
        }
        
        //把查询出来数据列表构建成框架要求的路由数据结构
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        List<RouterVo> routerVoList = this.buildRouter(sysMenuTreeList);

        return routerVoList;
    }

    private List<RouterVo> buildRouter(List<SysMenu> menus) {

        List<RouterVo> routers = new ArrayList<>();

        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            //下一层数据部分
            List<SysMenu> children = menu.getChildren();
            if(menu.getType().intValue() == 1) {
                //加载隐藏路由
                List<SysMenu> hiddenMenuList = children.stream().filter(item -> !StringUtil.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVo hiddenRouter = new RouterVo();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouter(children));
                }
            }
            routers.add(router);

        }

        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }

    /**
     * 根据用户id获取用户可操作按钮列表
     * @param userId
     * @return
     */
    @Override
    public List<String> findMenuPermsByUserId(Long userId) {

        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId.longValue() == 1) {
            sysMenuList = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        } else {
            sysMenuList = baseMapper.findUserMenuListByUserId(userId);
        }
        List<String> permsList = sysMenuList.stream().filter(item -> item.getType() == 2).map(item -> item.getPerms()).collect(Collectors.toList());
        return permsList;

    }
}
