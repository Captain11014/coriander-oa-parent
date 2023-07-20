package com.coriander.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coriander.model.process.ProcessTemplate;
import com.coriander.model.process.ProcessType;
import com.coriander.process.mapper.ProcessTemplateMapper;
import com.coriander.process.service.ProcessTemplateService;
import com.coriander.process.service.ProcessTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 姓陈的
 * 2023/7/14 14:45
 */
@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {

    @Resource
    private ProcessTemplateMapper processTemplateMapper;

    @Resource
    private ProcessTypeService processTypeService;

    @Override
    public IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam) {
        //1 调用mapper的方法实现分页查询
        Page<ProcessTemplate> processTemplatePage = baseMapper.selectPage(pageParam, null);

        //2 第一步分页查询返回分页数据，从分页数据获取列表list集合
        List<ProcessTemplate> processTemplateList = processTemplatePage.getRecords();

        //3 遍历list集合，得到每个对象的审批类型id
        for(ProcessTemplate processTemplate : processTemplateList) {
            //得到每个对象的审批类型id
            Long processTypeId = processTemplate.getProcessTypeId();
            //4 根据审批类型id，查询获取对应名称
            LambdaQueryWrapper<ProcessType> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessType::getId,processTypeId);
            ProcessType processType = processTypeService.getOne(wrapper);
            if(processType == null) {
                continue;
            }
            //5 完成最终封装processTypeName
            processTemplate.setProcessTypeName(processType.getName());
        }
        return processTemplatePage;
    }


}
