package com.lzl.controller;

import com.lzl.constant.Const;
import com.lzl.resp.LoginResp;
import com.lzl.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api("登录")
@RequestMapping(Const.URL)
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping("login")
    @ApiOperation("登录验证")
    public LoginResp login(@RequestParam("username") String account, @RequestParam String password) {
        LoginResp loginResp = userService.login(account, password);
        return loginResp;
    }
}
