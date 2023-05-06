package com.group1.group1_backend.sys.controller;

import com.group1.group1_backend.comm.vo.Result;
import com.group1.group1_backend.sys.entity.Conversation;
import com.group1.group1_backend.sys.entity.User;
import com.group1.group1_backend.sys.service.IConversationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 会话信息表 前端控制器
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
@Api(tags = "会话")
@RestController
@RequestMapping("/sys/conversation")
public class ConversationController {
    @Autowired
    private IConversationService conversationService;

    @GetMapping("/list")
    public Result<?> getList() {
        System.out.println(conversationService.list());
        return Result.success("获取所有会话信息成功", conversationService.list());
    }

    @PostMapping("/add")
    public Result<?> addConversation(@RequestBody Conversation conversation) {
        conversationService.add(conversation);
        return Result.success("添加会话成功");
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteConversationById(@PathVariable("id") Integer id) {
        conversationService.deleteById(id);
        return Result.success("删除会话成功");
    }

    @PutMapping("/update")
    public Result<?> updateConversation(@RequestBody Conversation conversation) {
        conversationService.update(conversation);
        return Result.success("更新会话成功");
    }
}
