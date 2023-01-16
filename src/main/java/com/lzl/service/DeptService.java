package com.lzl.service;

import com.lzl.pojo.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.resp.ListResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;

/**
* @author 26605
* @description 针对表【dept】的数据库操作Service
* @createDate 2022-11-03 09:54:19
*/
public interface DeptService extends IService<Dept> {

    PageResp queryDeptList(Integer page, Integer limit, String deptName, String deptNo);

    Resp saveOrUpdate(Long deptId, String deptName, String deptNo);

    Resp delete(Long deptId);

    ListResp queryDeptListNoParams();
}
