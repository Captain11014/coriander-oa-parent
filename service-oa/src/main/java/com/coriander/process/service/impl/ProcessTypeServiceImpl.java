package com.coriander.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coriander.model.process.ProcessType;
import com.coriander.process.mapper.ProcessTypeMapper;
import com.coriander.process.service.ProcessTypeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 姓陈的
 * 2023/7/14 14:23
 */
@Service
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper, ProcessType> implements ProcessTypeService {

}
