package com.coriander.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coriander.auth.mapper.SysMenuMapper;
import com.coriander.auth.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coriander.auth.util.MenuHelper;
import com.coriander.common.config.exception.CorianderException;
import com.coriander.model.system.SysMenu;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
}
