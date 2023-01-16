package com.lzl;

import com.lzl.mapper.UserMapper;
import com.lzl.pojo.User;
import com.lzl.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest//(classes = StorageManageSystemApplication.class)
class StorageManageSystemApplicationTests {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User byId = userService.getById("1");
        List<User> users = userMapper.selectList(null);
        System.out.println(byId);
        System.out.println(users);
    }

}
