package com.lzl.controller;


import com.lzl.constant.Const;
import com.lzl.resp.ListResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;
import com.lzl.resp.TreeResp;
import com.lzl.service.DeptService;
import com.lzl.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api
@RestController
@RequestMapping(Const.URL)
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation("获取角色列表")
    @PostMapping("Role/list")
    public PageResp queryRoleList(@RequestParam("page") Integer page,
                                  @RequestParam("limit") Integer limit,
                                  @RequestParam(value = "roleName",required = false) String roleName,
                                  @RequestParam(value = "roleNo",required = false) String roleNo
    ) {
        PageResp pageResp = roleService.queryRoleList(page,limit,roleName, roleNo);
        return pageResp;
    }


    @ApiOperation("获取角色列表无参")
    @PostMapping("Role/list/no/params")
    public ListResp queryRoleListNoParams() {
        ListResp listResp = roleService.queryRoleListNoParams();
        return listResp;
    }


    @ApiOperation("添加或修改角色")
    @PostMapping("Role/save")
    public Resp saveOrUpdate(@RequestParam(value = "roleId",required = false) Long roleId,
                             @RequestParam(value = "roleName") String roleName,
                             @RequestParam(value = "roleNo") String roleNo
    ) {
        Resp resp = roleService.saveOrUpdate(roleId,roleName, roleNo);
        return resp;
    }


    @ApiOperation("删除角色")
    @DeleteMapping("Role/delete")
    public Resp delete(@RequestParam(value = "ids") Long deptId){
        System.out.println(deptId);
        return roleService.delete(deptId);
    }

    @ApiOperation("角色菜单权限")
    @GetMapping("RoleRight/tree/{id}")
    public TreeResp roleMenus(@PathVariable("id") Long id) {
        TreeResp treeResp = roleService.roleMenus(id);
        return treeResp;
    }

    @ApiOperation("角色菜单权限保存")
    @PostMapping("RoleRight/save")
    public Resp roleMenusSave(
            @RequestParam(value = "roleId") Long roleId,
            @RequestParam(value = "moduleIds") List<Long> menuIds
    ) {
        Resp resp = roleService.roleMenusSave(roleId,menuIds);
        return resp;
    }
}
