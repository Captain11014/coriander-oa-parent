package com.coriander.common.utils;

import com.coriander.common.utils.page.PageDomain;
import com.coriander.common.utils.page.TableSupport;
import com.github.pagehelper.PageHelper;

/**
 * @author 姓陈的
 * 2023/4/5 13:32
 * 分页工具类
 */
public class PageUtil extends PageHelper {


    /**
     * 设置请求分页数据
     */
    public static void startPage(){

        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum,pageSize).reasonable(reasonable);

    }

    public static void clearPage(){
        PageHelper.clearPage();
    }


}
