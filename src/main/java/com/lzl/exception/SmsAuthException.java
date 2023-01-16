package com.lzl.exception;

import com.lzl.resp.Resp;

public class SmsAuthException extends SmsException{
    private Resp errorResp;

    public SmsAuthException(String message){
        super(message);
    }

    public SmsAuthException(Resp errorResp){
        super(errorResp.getMsg());
        this.errorResp=errorResp;

    }

    public SmsAuthException(String message,Throwable cause){
        super(message,cause);
    }

    public SmsAuthException(Throwable cause){
        super(cause);
    }
}
