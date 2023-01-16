package com.lzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzl.mapper.UserMapper;
import com.lzl.mdc.MDCKey;
import com.lzl.pojo.Dept;
import com.lzl.pojo.Role;
import com.lzl.pojo.User;
import com.lzl.resp.ListResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;
import com.lzl.service.DeptService;
import com.lzl.mapper.DeptMapper;
import org.h2.util.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 26605
* @description 针对表【dept】的数据库操作Service实现
* @createDate 2022-11-03 09:54:19
*/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
    implements DeptService{

    @Resource
    private DeptMapper deptMapper;


    @Override
    public PageResp queryDeptList(Integer page, Integer limit, String deptName, String deptNo) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isNullOrEmpty(deptName), "dept_name", deptName)
                .like(!StringUtils.isNullOrEmpty(deptNo), "dept_code", deptNo);

        PageHelper.startPage(page, limit);
        List<Dept> list = deptMapper.selectList(queryWrapper);

        PageInfo<Dept> pageInfo = new PageInfo(list);

        PageResp<List<Dept>> pageResp = new PageResp<>();
        pageResp.setCount(pageInfo.getTotal());
        pageResp.setData(list);
        return pageResp;
    }

    @Override
    public Resp saveOrUpdate(Long deptId, String deptName, String deptNo) {
        Dept dept = new Dept();
        dept.setDeptName(deptName);
        dept.setDeptNo(deptNo);
        if (deptId == null) {
            dept.setCreateTime(new Date());
            dept.setCreateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
            int insert = deptMapper.insert(dept);
            return Resp.toReturn(insert > 0 ? "成功" : "失败", insert > 0);
        }
        dept.setId(deptId);
        dept.setUpdateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
        dept.setEditTime(new Date());
        int update = deptMapper.updateById(dept);
        return Resp.toReturn(update > 0 ? "成功" : "失败", update > 0);
    }

    @Override
    public Resp delete(Long deptId) {
        int delete = deptMapper.deleteById(deptId);
        return Resp.toReturn(delete > 0 ? "成功" : "失败", delete > 0);
    }

    @Override
    public ListResp queryDeptListNoParams() {
        List<Dept> roles = deptMapper.selectList(null);
        ListResp listResp = new ListResp<>();
        listResp.setData(roles);
        return listResp;
    }
}




