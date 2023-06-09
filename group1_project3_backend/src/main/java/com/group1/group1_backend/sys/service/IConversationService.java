package com.group1.group1_backend.sys.service;

import com.group1.group1_backend.comm.vo.Result;
import com.group1.group1_backend.sys.entity.Conversation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会话信息表 服务类
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
public interface IConversationService extends IService<Conversation> {
    Result<?> add(Conversation conversation);
    Result<?> deleteById(Integer id);
    Result<?> update(Conversation conversation);
}
