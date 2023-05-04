package com.coriander.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.coriander.model.system.SysMenu;

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
}
