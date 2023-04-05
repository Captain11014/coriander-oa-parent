package com.coriander.common.core;

import com.coriander.common.result.AjaxResult;
import com.coriander.common.result.HttpStatus;
import com.coriander.common.utils.PageUtil;
import com.coriander.common.utils.page.TableDataInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 姓陈的
 * 2023/4/5 13:38
 */
public class BaseController {


    /**
     *  分页处理
     */
    protected void startPage(){
        PageUtil.startPage();
    }


    /**
     * 封装分页数据
     * @param list
     * @return
     */
    protected TableDataInfo getDataTable(List<?> list){
        PageInfo<?> pageInfo = new PageInfo<>(list);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setTotal(pageInfo.getTotal());
        return rspData;
    }

    /**
     * 返回成功消息
     * @return
     */
    protected AjaxResult success(){
        return AjaxResult.success();
    }

    /**
     * 返回成功消息
     * @return
     */
    protected AjaxResult success(String msg){
        return AjaxResult.success(msg);
    }

    /**
     * 返回成功消息
     * @return
     */
    protected AjaxResult success(Object data){
        return AjaxResult.success(data);
    }

    /**
     * 返回成功消息
     * @return
     */
    protected AjaxResult success(String msg ,Object data){
        return AjaxResult.success(msg,data);
    }

    /**
     * 返回失败消息
     * @return
     */
    protected AjaxResult error(){
        return AjaxResult.error();
    }

    /**
     * 返回失败消息
     * @return
     */
    protected AjaxResult error(String msg){
        return AjaxResult.error(msg);
    }


    /**
     * 返回失败消息
     * @return
     */
    protected AjaxResult error(String msg,Object data){
        return AjaxResult.error(msg, data);
    }

    /**
     * 返回失败消息
     * @return
     */
    protected AjaxResult error(int code,String msg){
        return AjaxResult.error(code, msg);
    }


    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows){
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result){
        return result ? success() : error();
    }







}
