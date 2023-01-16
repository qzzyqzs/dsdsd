package com.lzl.tool.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Preconditions;
import com.lzl.auth.AuthInfo;
import com.lzl.exception.SmsAuthException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    public static final String USER_ID="userId";
    public static final String USER_NAME="userName";
    public static final String USER_ROLE="roles";

    /**
     * 使用jwt生成token
     * @param authInfo
     * @param expireDate
     * @param secret
     * @return
     */
    public static String  getToken(AuthInfo authInfo, Date expireDate, String secret){
        Preconditions.checkArgument(authInfo!=null,"加密内容不能为null");
        Preconditions.checkArgument(expireDate!=null,"过期时间异常");
        Preconditions.checkArgument(secret!=null,"加密密码不能为null");

        Map<String, Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");

        String token=null;
        try {
            token = JWT.create()
                    .withHeader(map) //头
                    .withIssuedAt(new Date())   //签名时间
                    .withClaim(USER_ID,authInfo.getUserId())
                    .withClaim(USER_NAME,authInfo.getUserName())
                    .withClaim(USER_ROLE,authInfo.getRoleId())
                    .withExpiresAt(expireDate)  //过期时间
                    .sign(Algorithm.HMAC256(secret));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return token;
    }


    /**
     * 使用token获取用户信息
     * @param token
     * @param secret
     * @return
     */
    public static AuthInfo verifyToken(String token,String secret){
        JWTVerifier jwtVerifier=null;
        try {
            jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        DecodedJWT jwt=null;
        try {
            jwt = jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new SmsAuthException("凭证已过期，请重新登录");// 统一处理异常
        }
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserId(jwt.getClaim(USER_ID).asLong());
        authInfo.setUserName(jwt.getClaim(USER_NAME).asString());
        authInfo.setRoleId(jwt.getClaim(USER_ROLE).asLong());

        return authInfo;

    }

    public static void main(String[] args) {
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserId(1L);
        authInfo.setUserName("sd");
        //authInfo.setRoleId("ds");

        String token = getToken(authInfo, new Date(), "123456");
        System.out.println(token);
        System.out.println(verifyToken(token,"123456"));
    }
}
