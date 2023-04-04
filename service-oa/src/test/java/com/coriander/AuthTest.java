package com.coriander;


import com.coriander.auth.mapper.SysRoleMapper;
import com.coriander.model.system.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 姓陈的
 * 2023/4/3 23:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {

    @Resource
    private SysRoleMapper sysRoleMapper;


    @Test
    public void testSysRoleMapper(){

        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);

    }


}
