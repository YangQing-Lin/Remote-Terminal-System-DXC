package com.group1.group1_backend.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group1.group1_backend.sys.entity.Conversation;
import com.group1.group1_backend.sys.entity.UserRole;
import com.group1.group1_backend.sys.mapper.ConversationMapper;
import com.group1.group1_backend.sys.service.IConversationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会话信息表 服务实现类
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation> implements IConversationService {
    @Override
    public void add(Conversation conversation) {
        conversation.setId(null);
        this.baseMapper.insert(conversation);
    }

    @Override
    public void deleteById(Integer id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public void update(Conversation conversation) {
        this.baseMapper.updateById(conversation);
    }
}
