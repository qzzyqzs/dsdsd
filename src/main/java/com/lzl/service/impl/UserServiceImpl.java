package com.lzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzl.auth.AuthInfo;
import com.lzl.mdc.MDCKey;
import com.lzl.pojo.User;
import com.lzl.resp.LoginResp;
import com.lzl.resp.PageResp;
import com.lzl.resp.Resp;
import com.lzl.resp.UserResp;
import com.lzl.service.UserService;
import com.lzl.mapper.UserMapper;
import com.lzl.tool.util.JwtUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import org.h2.util.StringUtils;

/**
 * @author 26605
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2022-11-01 11:04:42
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Value("${jwt.auth.expireDate}")
    private Integer expire;

    @Value("${jwt.auth.secret}")
    private String secret;

    @Override
    public LoginResp login(String account, String password) {
        LoginResp loginResp = new LoginResp();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account)
                .eq("password", password);
        User user = getOne(queryWrapper);
        if (user == null) {
            loginResp.setSuccess(false);
            loginResp.setMsg("账号或密码不存在");
            return loginResp;
        }
        //生成token
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserId(user.getId());
        authInfo.setUserName(user.getUserName());
        authInfo.setRoleId(user.getRoleId());
        Date date = new Date(new Date().getTime() + expire);
        String token = JwtUtil.getToken(authInfo, date, secret);

        UserResp userResp = new UserResp();
        userResp.setId(user.getId());
        userResp.setUserName(user.getUserName());
        userResp.setDeptId(user.getDeptId());
        userResp.setRoleId(user.getRoleId());
        userResp.setPhone(user.getPhone());
        userResp.setToken(token);
        loginResp.setData(userResp);

        return loginResp;
    }

    @Override
    public PageResp queryUserList(Integer page, Integer limit, String userName, String userMobile) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isNullOrEmpty(userName), "user_name", userName)
                .like(!StringUtils.isNullOrEmpty(userMobile), "phone", userMobile);

        PageHelper.startPage(page, limit);
        List<User> list = userMapper.selectList(queryWrapper);

        PageInfo<User> pageInfo = new PageInfo(list);

        PageResp<List<User>> pageResp = new PageResp<>();
        pageResp.setCount(pageInfo.getTotal());
        pageResp.setData(list);
        return pageResp;
    }

    @Override
    public Resp saveOrUpdate(Long userId, String userName, String account, String userMobile, Long roleId, Long deptId) {
        User user = new User();
        user.setUserName(userName);
        user.setAccount(account);
        user.setPhone(userMobile);
        user.setDeptId(deptId);
        user.setRoleId(roleId);
        if (userId == null) {
            user.setCreateTime(new Date());
            user.setCreateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
            user.setPassword("123456");
            int insert = userMapper.insert(user);
            return Resp.toReturn(insert > 0 ? "成功" : "失败", insert > 0);
        }
        user.setId(userId);
        user.setUpdateUser(Long.valueOf(MDC.get(MDCKey.USER_ID)));
        user.setEditTime(new Date());
        int update = userMapper.updateById(user);
        return Resp.toReturn(update > 0 ? "成功" : "失败", update > 0);
    }

    @Override
    public Resp delete(Long userId) {
        int delete = userMapper.deleteById(userId);
        return Resp.toReturn(delete > 0 ? "成功" : "失败", delete > 0);
    }

    @Override
    public Resp resetPwd(Long userId) {
        //UpdateWrapper<Object> updateWrapper = new UpdateWrapper<>();
        User user = new User();
        user.setId(userId);
        user.setPassword("123456");
        int update = userMapper.updateById(user);
        return Resp.toReturn(update > 0 ? "成功" : "失败", update > 0);
    }
}




