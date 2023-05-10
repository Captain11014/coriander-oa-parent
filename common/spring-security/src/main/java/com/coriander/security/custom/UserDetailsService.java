package com.coriander.security.custom;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author 姓陈的
 * 2023/5/10 22:06
 */
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

    /**
     * 根据用户名获取用户对象（获取不到直接抛异常）
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
