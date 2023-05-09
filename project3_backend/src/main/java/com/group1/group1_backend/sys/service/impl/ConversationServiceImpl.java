package com.group1.group1_backend.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group1.group1_backend.comm.vo.Result;
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
    public Result<?> add(Conversation conversation) {
        conversation.setId(null);
        int insert = this.baseMapper.insert(conversation);
        if (insert == 1) {
            return Result.success("添加会话成功");
        }
        return Result.fail("添加会话失败");
    }

    @Override
    public Result<?> deleteById(Integer id) {
        int i = this.baseMapper.deleteById(id);
        if (i == 1) {
            return Result.success("删除会话成功");
        }
        return Result.fail("删除会话失败");
    }

    @Override
    public Result<?> update(Conversation conversation) {
        if (conversation.getId() == null || conversation.getId() <= 0) {
            return Result.fail("会话id不存在或不合法");
        }
        int result = this.baseMapper.updateById(conversation);
        System.out.println("result: " + result);
        if (result == 1) {
            return Result.success("更新会话成功");
        }
        return Result.fail("更新会话失败");
    }
}
