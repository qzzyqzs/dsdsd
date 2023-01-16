package com.lzl.auth;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.lzl.constant.Const;
import com.lzl.exception.SmsAuthException;
import com.lzl.mdc.MDCKey;
import com.lzl.mdc.MDCScope;
import com.lzl.resp.Resp;
import com.lzl.tool.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
public class AuthFilter implements Filter {

    private AuthenticatorImpl authenticator;

    public AuthFilter(AuthenticatorImpl authenticator){
        this.authenticator=authenticator;
    }

    private Set<String> unfilter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String initParameter = filterConfig.getInitParameter(Const.UN_FILTER_KEY);
        List<String> list;
        if (StringUtils.isEmpty(initParameter)){
            list= Collections.emptyList();
        }else {
            list= Lists.newArrayList(initParameter.split(","));
            list.removeIf(String::isEmpty);
        }
        unfilter= ImmutableSet.copyOf(list);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (unfilter.stream().anyMatch(uri::startsWith)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        try(MDCScope mdcScope = new MDCScope()){
            String header = request.getHeader("token");
            String token = StringUtils.isEmpty(header) ? request.getParameter("token") : header;

            if (StringUtils.isEmpty(token)){
                //统一异常处理
                throw new SmsAuthException("未携带token");
            }
            AuthInfo authInfo = authenticator.auth(token);
            mdcScope.put(MDCKey.USER_ID,String.valueOf(authInfo.getUserId()));
            mdcScope.put(MDCKey.USER_NAME,authInfo.getUserName());
            mdcScope.put(MDCKey.ROLE_ID,String.valueOf(authInfo.getRoleId()));

            filterChain.doFilter(servletRequest,servletResponse);

            //重点
        }catch (SmsAuthException e){

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            String jsonString = JsonUtil.getJsonString(Resp.toReturn(e.getMessage(), false));

            response.getOutputStream().write(jsonString.getBytes());

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
