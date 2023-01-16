package com.lzl.exception;

import com.lzl.resp.Resp;

public class SmsBadRequestException extends SmsException{
    private Resp errorResp;

    public SmsBadRequestException(String message){
        super(message);
    }

    public SmsBadRequestException(Resp errorResp){
        super(errorResp.getMsg());
        this.errorResp=errorResp;

    }

    public SmsBadRequestException(String message, Throwable cause){
        super(message,cause);
    }

    public SmsBadRequestException(Throwable cause){
        super(cause);
    }
}
