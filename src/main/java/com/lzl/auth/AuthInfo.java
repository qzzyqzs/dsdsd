package com.lzl.auth;

import lombok.Data;

@Data
public class AuthInfo {
    private Long userId;
    private String userName;
    private Long roleId;
}
