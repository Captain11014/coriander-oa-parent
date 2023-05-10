package com.coriander.auth.mapper;

import com.coriander.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 姓陈的
 * @since 2023-05-04
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findUserMenuListByUserId(@Param("userId") Long userId);

}
