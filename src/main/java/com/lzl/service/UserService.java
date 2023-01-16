package com.lzl.service;

import com.lzl.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.resp.LoginResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;

/**
* @author 26605
* @description 针对表【user】的数据库操作Service
* @createDate 2022-11-01 11:04:42
*/
public interface UserService extends IService<User> {

    LoginResp login(String account, String password);

    PageResp queryUserList(Integer page, Integer limit, String userName, String userMobile);

    Resp saveOrUpdate(Long userId, String userName, String account, String userMobile, Long roleId, Long deptId);

    Resp delete(Long userId);

    Resp resetPwd(Long userId);
}
