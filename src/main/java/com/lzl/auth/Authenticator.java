package com.lzl.auth;

public interface Authenticator {
    AuthInfo auth(String token);
}
