package com.coriander.auth.service.impl;

import com.coriander.auth.service.SysMenuService;
import com.coriander.auth.service.SysUserService;
import com.coriander.model.system.SysUser;
import com.coriander.security.custom.CustomUser;
import com.coriander.security.custom.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 姓陈的
 * 2023/5/10 22:08
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("账号已停用");
        }


        //根据用户id查询用户操作权限数据
        List<String> userPermsList = sysMenuService.findMenuPermsByUserId(sysUser.getId());
        //封装权限数据
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        for (String perm:userPermsList) {
            authList.add(new SimpleGrantedAuthority(perm.trim()));
        }

        return new CustomUser(sysUser, authList);
    }

}
