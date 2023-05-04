package com.group1.group1_backend.sys.controller;


import com.group1.group1_backend.comm.vo.Result;
import com.group1.group1_backend.sys.entity.Menu;


import com.group1.group1_backend.sys.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laocai
 * @since 2023-02-07
 */
@Api(tags = "菜单控制")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @ApiOperation("获取所有菜单")
    @GetMapping
    public Result<List<Menu>> getAllMenu() {
        List<Menu> menuList = menuService.getAllMenu();
        return Result.success(menuList);
    }

}
