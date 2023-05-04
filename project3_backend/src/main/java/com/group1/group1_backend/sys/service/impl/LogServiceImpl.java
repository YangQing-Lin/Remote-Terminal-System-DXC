package com.group1.group1_backend.sys.service.impl;

import com.group1.group1_backend.sys.entity.Log;
import com.group1.group1_backend.sys.mapper.LogMapper;
import com.group1.group1_backend.sys.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志信息表 服务实现类
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Override
    public void add(Log log) {
        this.baseMapper.insert(log);
    }

    @Override
    public void deleteById(Integer id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public void update(Log log) {
        this.baseMapper.updateById(log);
    }
}
