package com.group1.group1_backend.sys.service;

import com.group1.group1_backend.comm.vo.Result;
import com.group1.group1_backend.sys.entity.Conversation;
import com.group1.group1_backend.sys.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 日志信息表 服务类
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
public interface ILogService extends IService<Log> {
    Result<?> add(Log log);
    Result<?> deleteById(Integer id);
    Result<?> update(Log log);
}
