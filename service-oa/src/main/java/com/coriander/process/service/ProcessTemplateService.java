package com.coriander.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coriander.model.process.ProcessTemplate;

/**
 * @author 姓陈的
 * 2023/7/14 14:43
 */
public interface ProcessTemplateService extends IService<ProcessTemplate> {

    IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam);

}
