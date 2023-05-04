package com.group1.group1_backend.sys.controller;

import com.group1.group1_backend.comm.vo.Result;
import com.group1.group1_backend.sys.entity.Conversation;
import com.group1.group1_backend.sys.entity.Log;
import com.group1.group1_backend.sys.entity.User;
import com.group1.group1_backend.sys.service.IConversationService;
import com.group1.group1_backend.sys.service.ILogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 日志信息表 前端控制器
 * </p>
 *
 * @author linqingchuan
 * @since 2023-05-04
 */
@Api(tags = "日志")
@RestController
@RequestMapping("/sys/log")
public class LogController {
    @Autowired
    private ILogService logService;

    @GetMapping("/all")
    public Result<?> getAll() {
        System.out.println(logService.list());
        return Result.success("获取所有日志信息成功", logService.list());
    }

    @PostMapping("/add")
    public Result<?> addConversation(@RequestBody Log log) {
        logService.add(log);
        return Result.success("添加日志成功");
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteConversationById(@PathVariable("id") Integer id) {
        logService.deleteById(id);
        return Result.success("删除日志成功");
    }

    @PutMapping("/update")
    public Result<?> updateConversation(@RequestBody Log log) {
        logService.update(log);
        return Result.success("更新日志成功");
    }
}
