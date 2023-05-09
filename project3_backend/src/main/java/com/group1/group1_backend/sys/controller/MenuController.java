package com.group1.group1_backend.sys.controller;


import com.group1.group1_backend.comm.vo.Result;
import com.group1.group1_backend.sys.entity.Menu;


import com.group1.group1_backend.sys.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public Result<Menu> addMenu(@RequestBody Menu menu){
        boolean save = menuService.save(menu);
        if(save){
            return Result.success("新增成功!");
        }
        return Result.fail("新增失败!");
    }
    @PutMapping
    public Result<Menu> editMenu(@RequestBody Menu menu){
        boolean save = menuService.updateById(menu);
        if(save){
            return Result.success("编辑成功!");
        }
        return Result.fail("编辑失败!");
    }
    @DeleteMapping("/{menuId}")
    public Result<Menu> deleteMenu(@PathVariable("menuId") String menuId){
        boolean save = menuService.removeById(menuId);
        if(save){
            return Result.success("删除成功!");
        }
        return Result.fail("删除失败!");
    }
    @GetMapping
    public Result<List<Menu>> getAllMenu(){
        List<Menu> menuList = menuService.getAllMenu();
        return Result.success(menuList);
    }
}
