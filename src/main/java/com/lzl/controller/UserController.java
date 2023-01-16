package com.lzl.controller;

import com.lzl.constant.Const;
import com.lzl.mdc.MDCKey;
import com.lzl.resp.LoginResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;
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
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("获取用户列表")
    @PostMapping("User/list")
    public PageResp queryUserList(@RequestParam("page") Integer page,
                                  @RequestParam("limit") Integer limit,
                                  @RequestParam(value = "userName",required = false) String userName,
                                  @RequestParam(value = "userMobile",required = false) String userMobile
    ) {
        PageResp pageResp = userService.queryUserList(page,limit,userName, userMobile);
        return pageResp;
    }

    @ApiOperation("添加用户")
    @PostMapping("User/save")
    public Resp saveOrUpdate(@RequestParam(value = "userId",required = false) Long userId,
                             @RequestParam(value = "userName") String userName,
                             @RequestParam(value = "account") String account,
                             @RequestParam(value = "userMobile") String userMobile,
                             @RequestParam(value = "roleId") Long roleId,
                             @RequestParam(value = "deptId") Long deptId
    ) {
        Resp resp = userService.saveOrUpdate(userId,userName,account, userMobile,roleId,deptId);
        return resp;
    }



    @GetMapping("test")
    @ApiOperation("测试")
    public Result test(@RequestParam Long id) {
        String s = MDC.get(MDCKey.USER_NAME);
        Result result = Result.success().setData("s", s);
        return result;
    }

    @ApiOperation("删除用户")
    @DeleteMapping("User/delete")
    public Resp delete(@RequestParam(value = "ids") Long userId){
        return userService.delete(userId);
    }

    @ApiOperation("重置密码")
    @PostMapping("User/pwd")
    public Resp resetPwd(@RequestParam(value = "userId") Long userId) {
        Resp resp = userService.resetPwd(userId);
        return resp;
    }
}
