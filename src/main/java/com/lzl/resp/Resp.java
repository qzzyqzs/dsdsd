package com.lzl.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resp {
    //操作是否成功
    private Boolean success=true;

    //消息
    private String msg;

    public static Resp toReturn(String msg,Boolean success){
        Resp resp = new Resp();
        resp.setSuccess(success);
        resp.setMsg(msg);
        return resp;
    }
}
