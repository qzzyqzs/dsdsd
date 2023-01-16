package com.lzl.auth;

import com.lzl.exception.SmsAuthException;
import com.lzl.tool.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class AuthenticatorImpl implements Authenticator{


    private String secret;

    public AuthenticatorImpl(){

    }

    public AuthenticatorImpl(String secret){
        this.secret=secret;
    }

    @Override
    public AuthInfo auth(String token) {
        String authToken;
        int index=token.indexOf(" ");
        if (index==-1){
            authToken=token;
        }else {
            String tokenType=token.substring(0,index);
            if (!"Bearer".equals(tokenType)){
                throw new SmsAuthException(String.format("无法识别的token类型[%s]",token));// 统一异常处理
            }else {
                authToken=token.substring(index).trim();
            }
        }
        AuthInfo authInfo = JwtUtil.verifyToken(authToken, secret);
        return authInfo;
    }

}
