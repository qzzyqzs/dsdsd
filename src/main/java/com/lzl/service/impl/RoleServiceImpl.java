package com.lzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzl.mapper.DeptMapper;
import com.lzl.mapper.RoleMenuMapper;
import com.lzl.mdc.MDCKey;
import com.lzl.pojo.Dept;
import com.lzl.pojo.Role;
import com.lzl.pojo.RoleMenu;
import com.lzl.resp.ListResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;
import com.lzl.resp.TreeResp;
import com.lzl.service.MenuService;
import com.lzl.service.RoleService;
import com.lzl.mapper.RoleMapper;
import org.h2.util.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 26605
* @description 针对表【role】的数据库操作Service实现
* @createDate 2022-11-03 10:53:38
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public PageResp queryRoleList(Integer page, Integer limit, String roleName, String roleNo) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isNullOrEmpty(roleName), "role_name", roleName)
                .like(!StringUtils.isNullOrEmpty(roleNo), "role_code", roleNo);

        PageHelper.startPage(page, limit);
        List<Role> list = roleMapper.selectList(queryWrapper);

        PageInfo<Role> pageInfo = new PageInfo(list);

        PageResp<List<Role>> pageResp = new PageResp<>();
        pageResp.setCount(pageInfo.getTotal());
        pageResp.setData(list);
        return pageResp;
    }

    @Override
    public Resp saveOrUpdate(Long roleId, String roleName, String roleNo) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleNo(roleNo);
        if (roleId == null) {
            role.setCreateTime(new Date());
            role.setCreateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
            int insert = roleMapper.insert(role);
            return Resp.toReturn(insert > 0 ? "成功" : "失败", insert > 0);
        }
        role.setId(roleId);
        role.setUpdateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
        role.setEditTime(new Date());
        int update = roleMapper.updateById(role);
        return Resp.toReturn(update > 0 ? "成功" : "失败", update > 0);
    }

    @Override
    @Transactional
    public Resp delete(Long deptId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", deptId);
        int delete1 = roleMenuMapper.delete(queryWrapper);
        int delete = roleMapper.deleteById(deptId);
        return Resp.toReturn(delete > 0 ? "成功" : "失败", delete > 0);
    }

    @Override
    public TreeResp roleMenus(Long id) {
        ListResp menus = menuService.menus();
        TreeResp treeResp = new TreeResp<>();
        treeResp.setData(menus.getData());

        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(queryWrapper);
        treeResp.setList(roleMenus);

        return treeResp;
    }

    @Override
    @Transactional
    public Resp roleMenusSave(Long roleId, List<Long> menuIds) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        int delete = roleMenuMapper.delete(queryWrapper);

        int insert =0;
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            insert += roleMenuMapper.insert(roleMenu);
        }
        return Resp.toReturn(insert == menuIds.size() ? "成功" : "失败", insert == menuIds.size());
    }

    @Override
    public ListResp queryRoleListNoParams() {
        List<Role> roles = roleMapper.selectList(null);
        ListResp listResp = new ListResp<>();
        listResp.setData(roles);
        return listResp;
    }
}




