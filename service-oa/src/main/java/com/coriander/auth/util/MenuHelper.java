package com.coriander.auth.util;

import com.coriander.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 姓陈的
 * 2023/5/4 22:32
 */
public class MenuHelper {


    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {

        List<SysMenu> trees = new ArrayList<>();


        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getParentId().longValue() == 0) {
                trees.add(findChildren(sysMenu, sysMenuList));
            }
        }

        return trees;

    }

    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treesNode) {

        sysMenu.setChildren(new ArrayList<>());

        for (SysMenu s : treesNode) {
            if(sysMenu.getId().longValue() == s.getParentId().longValue()){

//                if(sysMenu.getChildren() == null){
//                    sysMenu.setChildren(new ArrayList<>());
//                }

                sysMenu.getChildren().add(findChildren(s,treesNode));
            }
        }

        return sysMenu;
    }


}
