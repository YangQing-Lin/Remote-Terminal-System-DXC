package com.group1.group1_backend.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group1.group1_backend.comm.vo.Result;
import com.group1.group1_backend.sys.entity.User;
import com.group1.group1_backend.sys.service.IUserService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linqingchuan
 * @since 2023-03-16
 */
@Api(tags = {"用户接口"})
@RestController
@RequestMapping("/user")
//@CrossOrigin  // 跨域处理
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public Result<?> addUser(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userService.getOne(wrapper);
        if (null != loginUser) {
            return Result.fail("账号已存在，请重新输入");
        }
        // 加密用户密码
        if (user.getPassword() != null) {
            user.setPassword((passwordEncoder.encode(user.getPassword())));
        }
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    @GetMapping("/all")
    public Result<List<User>> getAllUser() {
        List<User> list = userService.list();
        list.forEach(System.out::println);
        return Result.success("查询成功", list);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = userService.login(user);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }

    @ApiOperation("获取用户信息，包括可以查看哪些Menu")
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestHeader("X-Token") String token) {
//        根据token获取用户信息，从redis里获取
        Map<String, Object> data = userService.getUserInfo(token);
        System.out.printf("in user controller. data: " + data);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "登录信息无效，请重新登陆");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")

    public Result<Map<String, Object>> getUserList(@RequestParam(value = "username", required = false) String username,
                                                   @RequestParam(value = "phone", required = false) String phone,
                                                   @RequestParam(value = "startTime", required = false) String startTime,
                                                   @RequestParam(value = "endTime", required = false) String endTime,
                                                   @RequestParam(value = "createTime",required = false) Date createTime,
                                                   @RequestParam(value = "pageNo") Long pageNo,
                                                   @RequestParam(value = "pageSize") Long pageSize) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);
        wrapper.between(startTime != null && endTime != null, User::getCreateTime, startTime, endTime);

        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);

    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        user.setPassword(null);
        userService.updateUser(user);
        return Result.success("修改用户成功");
    }

    @PostMapping("/password")
    public Result<Map<String, Object>> updatePassword(@RequestParam("token") String token,
                                                      @RequestParam("username") String username,
                                                      @RequestParam("oldPassword") String oldPassword,
                                                      @RequestParam("newPassword") String newPassword) {
        System.out.println("update password: " + token + " " + username + " " + oldPassword + " " + newPassword);
        Map<String, Object> data = userService.updatePassword(token, username, oldPassword, newPassword);
        if(data != null){
            userService.updateById((User) data.get("loginUser"));
            return Result.success(data);
        }
        return Result.fail(20004,"更改密码失败");
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return Result.success("删除用户成功");
    }

}