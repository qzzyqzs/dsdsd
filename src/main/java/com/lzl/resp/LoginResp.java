package com.lzl.resp;

import lombok.Data;

@Data
public class LoginResp extends Resp{
    private UserResp data;
}
