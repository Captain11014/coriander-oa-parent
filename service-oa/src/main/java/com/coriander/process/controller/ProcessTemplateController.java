package com.coriander.process.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coriander.common.core.BaseController;
import com.coriander.common.result.AjaxResult;
import com.coriander.model.process.ProcessTemplate;
import com.coriander.process.service.ProcessTemplateService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姓陈的
 * 2023/7/14 14:49
 */
@RestController
@RequestMapping(value = "/admin/process/processTemplate")
public class ProcessTemplateController extends BaseController {

    @Autowired
    private ProcessTemplateService processTemplateService;


    @GetMapping("{page}/{limit}")
    public AjaxResult index(@PathVariable Long page, @PathVariable Long limit) {
        Page<ProcessTemplate> pageParam = new Page<>(page, limit);
        IPage<ProcessTemplate> pageModel = processTemplateService.selectPage(pageParam);
        return success(pageModel);
    }


    @GetMapping("get/{id}")
    public AjaxResult get(@PathVariable Long id) {
        ProcessTemplate processTemplate = processTemplateService.getById(id);
        return success(processTemplate);
    }


    @PostMapping("save")
    public AjaxResult save(@RequestBody ProcessTemplate processTemplate) {
        processTemplateService.save(processTemplate);
        return success();
    }


    @PutMapping("update")
    public AjaxResult updateById(@RequestBody ProcessTemplate processTemplate) {
        processTemplateService.updateById(processTemplate);
        return success();
    }


    @DeleteMapping("remove/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        processTemplateService.removeById(id);
        return success();
    }

}
