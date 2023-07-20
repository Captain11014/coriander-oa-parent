package com.coriander.process.controller;

import com.coriander.common.core.BaseController;
import com.coriander.common.result.AjaxResult;
import com.coriander.common.utils.page.TableDataInfo;
import com.coriander.model.process.ProcessType;
import com.coriander.process.service.ProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 姓陈的
 * 2023/7/14 14:25
 */
@RestController
@RequestMapping(value = "/admin/process/processType")
public class ProcessTypeController extends BaseController {

    @Autowired
    private ProcessTypeService processTypeService;

    @GetMapping("{page}/{limit}")
    public TableDataInfo index() {
        List<ProcessType> list = processTypeService.list(null);
        return getDataTable(list);
    }

    @GetMapping("get/{id}")
    public AjaxResult get(@PathVariable Long id) {
        ProcessType processType = processTypeService.getById(id);
        return success(processType);
    }


    @PostMapping("save")
    public AjaxResult save(@RequestBody ProcessType processType) {
        processTypeService.save(processType);
        return success();
    }

    @PutMapping("update")
    public AjaxResult updateById(@RequestBody ProcessType processType) {
        processTypeService.updateById(processType);
        return success();
    }

    @DeleteMapping("remove/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        processTypeService.removeById(id);
        return success();
    }

}
