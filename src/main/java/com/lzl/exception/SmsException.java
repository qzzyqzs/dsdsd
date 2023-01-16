package com.lzl.exception;

public class SmsException extends RuntimeException{

    public SmsException(){
        super();
    }

    public SmsException(String message){
        super(message);
    }

    public SmsException(String message,Throwable cause){
        super(message,cause);
    }

    public SmsException(Throwable cause){
        super(cause);
    }
}
