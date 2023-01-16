package com.lzl.controller;

import com.lzl.constant.Const;
import com.lzl.mdc.MDCKey;
import com.lzl.resp.ListResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;
import com.lzl.service.DeptService;
import com.lzl.service.UserService;
import com.lzl.tool.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api
@RestController
@RequestMapping(Const.URL)
public class DeptController {

    @Resource
    private DeptService deptService;

    @ApiOperation("获取部门列表")
    @PostMapping("Dept/list")
    public PageResp queryDeptList(@RequestParam("page") Integer page,
                                  @RequestParam("limit") Integer limit,
                                  @RequestParam(value = "deptName",required = false) String deptName,
                                  @RequestParam(value = "deptNo",required = false) String deptNo
    ) {
        PageResp pageResp = deptService.queryDeptList(page,limit,deptName, deptNo);
        return pageResp;
    }


    @ApiOperation("获取部门列表无参")
    @PostMapping("Dept/list/no/params")
    public ListResp queryDeptListNoParams() {
        ListResp listResp = deptService.queryDeptListNoParams();
        return listResp;
    }



    @ApiOperation("添加或修改部门")
    @PostMapping("Dept/save")
    public Resp saveOrUpdate(@RequestParam(value = "deptId",required = false) Long deptId,
                             @RequestParam(value = "deptName") String deptName,
                             @RequestParam(value = "deptNo") String deptNo
    ) {
        Resp resp = deptService.saveOrUpdate(deptId,deptName, deptNo);
        return resp;
    }


    @ApiOperation("删除部门")
    @DeleteMapping("Dept/delete")
    public Resp delete(@RequestParam(value = "ids") Long deptId){
        return deptService.delete(deptId);
    }

}
