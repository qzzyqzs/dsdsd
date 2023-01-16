package com.lzl.resp;

import lombok.Data;

@Data
public class UserResp {

    private Long id;
    private String userName;
    private Long deptId;
    private Long roleId;
    private String phone;
    private String token;
}
