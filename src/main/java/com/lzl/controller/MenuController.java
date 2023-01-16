package com.lzl.controller;

import com.lzl.constant.Const;
import com.lzl.resp.ListResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;
import com.lzl.service.MenuService;
import com.lzl.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api
@RestController
@RequestMapping(Const.URL)
public class MenuController {

    @Resource
    private MenuService menuService;

    @ApiOperation("获取菜单列表")
    @GetMapping("menu")
    public ListResp menus() {
        ListResp listResp = menuService.menus();
        return listResp;
    }

    @ApiOperation("获取菜单管理列表")
    @PostMapping("Module/list")
    public ListResp menuList() {
        ListResp listResp = menuService.menus();
        return listResp;
    }

    @ApiOperation("根据id获取菜单信息")
    @GetMapping("Module/get/{id}")
    public ListResp getMenus(@PathVariable("id")Long menuId){
        ListResp listResp = menuService.getMenus(menuId);
        return listResp;
    }

    @ApiOperation("节点列表")
    @PostMapping("Module/nodes")
    public ListResp nodes() {
        ListResp listResp = menuService.nodes();
        return listResp;
    }

    @ApiOperation("添加菜单")
    @PostMapping("Module/save")
    public Resp saveOrUpdate(@RequestParam(value = "moduleId",required = false) Long menuId,
                             @RequestParam(value = "parentId",required = false) Long parentId,
                             @RequestParam(value = "moduleIcon") String menuIcon,
                             @RequestParam(value = "moduleUrl") String menuUrl,
                             @RequestParam(value = "moduleName") String menuName,
                             @RequestParam(value = "moduleOrder") Integer menuOrder
    ) {
        Resp resp = menuService.saveOrUpdate(menuId,parentId, menuIcon,menuUrl,menuName,menuOrder);
        return resp;
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("Module/delete")
    public Resp delete(@RequestParam(value = "ids") List<Long> menuId){
        return menuService.delete(menuId);
    }

}
