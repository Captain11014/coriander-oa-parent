package com.coriander.common.utils.page;

import com.coriander.common.utils.Convert;
import com.coriander.common.utils.ServletUtil;

/**
 * @author 姓陈的
 * 2023/4/5 13:11
 * 表格数据处理
 */
public class TableSupport {

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 分页参数合理化
     */
    public static final String REASONABLE = "reasonable";

    public static PageDomain getPageDomain(){

        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(Convert.toInt(ServletUtil.getParameter(PAGE_NUM),1));
        pageDomain.setPageSize(Convert.toInt(ServletUtil.getParameter(PAGE_SIZE),10));
        pageDomain.setOrderByColumn(ServletUtil.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtil.getParameter(IS_ASC));
        pageDomain.setReasonable(ServletUtil.getParameterToBool(REASONABLE));
        return pageDomain;
    }


    public static PageDomain buildPageRequest(){
        return getPageDomain();
    }


}
