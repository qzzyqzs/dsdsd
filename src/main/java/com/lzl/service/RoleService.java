package com.lzl.service;

import com.lzl.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.resp.ListResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;
import com.lzl.resp.TreeResp;

import java.util.List;

/**
* @author 26605
* @description 针对表【role】的数据库操作Service
* @createDate 2022-11-03 10:53:38
*/
public interface RoleService extends IService<Role> {

    PageResp queryRoleList(Integer page, Integer limit, String roleName, String roleNo);

    Resp saveOrUpdate(Long roleId, String roleName, String roleNo);

    Resp delete(Long deptId);

    TreeResp roleMenus(Long id);

    Resp roleMenusSave(Long roleId, List<Long> menuIds);

    ListResp queryRoleListNoParams();
}
