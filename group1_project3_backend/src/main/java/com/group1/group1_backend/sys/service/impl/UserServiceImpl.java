package com.group1.group1_backend.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.group1.group1_backend.comm.utils.JwtUtil;
import com.group1.group1_backend.comm.vo.Result;
import com.group1.group1_backend.sys.entity.Menu;
import com.group1.group1_backend.sys.entity.User;
import com.group1.group1_backend.sys.entity.UserRole;
import com.group1.group1_backend.sys.mapper.UserMapper;
import com.group1.group1_backend.sys.mapper.UserRoleMapper;
import com.group1.group1_backend.sys.service.IMenuService;
import com.group1.group1_backend.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author linqingchuan
 * @since 2023-03-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IMenuService menuService;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录逻辑
     *
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> login(User user) {
//        根据用户名和密码查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);  // 从数据库中获取用户名相同的用户的信息

//        结果不为空，并且密码和传入密码匹配，则生成一个token，并且将用户信息存入redis
        if (loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
//            if (loginUser != null && loginUser.getPassword().equals(user.getPassword())) {
//            暂时用UUID，终极方案是jwt
//            String key = "user:" + UUID.randomUUID();

//            存入redis
            loginUser.setPassword(null);
            //redisTemplate.opsForValue().set(key, loginUser,10, TimeUnit.MINUTES);
//            返回数据
            String token = jwtUtil.createToken(loginUser);
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);

            return data;
        }

        return null;
    }

    @Override
    public Map<String, Object> updateRedisUserInfo(String token, User userInfo) {
        redisTemplate.opsForValue().set(token, userInfo, 10, TimeUnit.MINUTES);
        return null;
    }

    @Override
    public Map<String, Object> updatePassword(String token, String username, String oldPassword, String newPassword) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User loginUser = this.baseMapper.selectOne(wrapper);  // 从数据库中获取用户名相同的用户的信息

        if (loginUser != null && passwordEncoder.matches(oldPassword, loginUser.getPassword())) {
            redisTemplate.delete(token);  // 成功修改密码时需要删除之前的登录信息，重新登录
            loginUser.setPassword(passwordEncoder.encode(newPassword));
            System.out.println("save login user: " + loginUser);
//            save(loginUser);
            System.out.println("save new password success");
            Map<String, Object> data = new HashMap<>();
            data.put("loginUser", loginUser);
            data.put("access", "update password SUCCESS");

            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        User loginUser = null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loginUser != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getName());
            data.put("username", loginUser.getUsername());
            data.put("url", loginUser.getUrl());
            data.put("phone", loginUser.getPhone());
            data.put("type", loginUser.getType());
            data.put("email", loginUser.getEmail());
            data.put("createTime", loginUser.getCreateTime());
            data.put("id", loginUser.getId());

            // 角色
            List<String> roleList = this.baseMapper.getRoleNamesByUserId(loginUser.getId());
            data.put("roles", roleList);

            // 权限列表
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList", menuList);

            System.out.println("data: " + data);

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        // 写入用户表
        this.baseMapper.insert(user);
        // 写入用户角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if (roleIdList != null) {
            for (Integer roleId : roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        System.out.println("update user:" + user);
        // 更新用户表
        this.baseMapper.updateById(user);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userRoleMapper.delete(wrapper);
        // 设置新的角色
        List<Integer> roleIdList = user.getRoleIdList();
        if (roleIdList != null) {
            for (Integer roleId : roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }
    }

    @Override
    public void deleteUserById(Integer id) {
        this.baseMapper.deleteById(id);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        userRoleMapper.delete(wrapper);
    }

    @ApiOperation("获取用户的信息，包括权限")
    @Override
    public User getUserById(Integer id) {
        User user = this.baseMapper.selectById(id);

        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);

        List<Integer> roleIdList = userRoleList.stream()
                .map(userRole -> {
                    return userRole.getRoleId();
                })
                .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);

        return user;
    }
}