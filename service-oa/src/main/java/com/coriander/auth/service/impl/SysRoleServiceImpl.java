package com.coriander.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coriander.auth.mapper.SysRoleMapper;
import com.coriander.auth.service.SysRoleService;
import com.coriander.model.system.SysRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 姓陈的
 * 2023/4/4 16:03
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;



}
