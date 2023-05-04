package com.coriander.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coriander.model.system.SysRole;
import com.coriander.vo.system.AssginRoleVo;

import java.util.Map;

/**
 * @author 姓陈的
 * 2023/4/4 16:02
 */
public interface SysRoleService extends IService<SysRole> {


    /**
     * 根据用户获取角色数据
     * @param userId
     * @return
     */
    Map<String, Object> findRoleByAdminId(Long userId);

    /**
     * 分配角色
     * @param assginRoleVo
     */
    void doAssign(AssginRoleVo assginRoleVo);


}
