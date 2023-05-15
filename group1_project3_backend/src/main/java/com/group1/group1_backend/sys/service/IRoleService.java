package com.group1.group1_backend.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group1.group1_backend.sys.entity.Role;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linqingchuan
 * @since 2023-03-29
 */
public interface IRoleService extends IService<Role> {

    void addRole(Role role);

    Role getRoleById(Integer id);

    void updateRole(Role role);

    void deleteRoleById(Integer id);
}
