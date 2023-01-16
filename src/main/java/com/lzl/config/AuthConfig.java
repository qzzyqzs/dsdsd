package com.lzl.config;

import com.google.common.base.Joiner;
import com.lzl.auth.AuthFilter;
import com.lzl.auth.AuthenticatorImpl;
import com.lzl.constant.Const;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;

@Configuration
public class AuthConfig {

    @Resource
    ApplicationContext applicationContext;

    @Bean
    public FilterRegistrationBean authFilter(){
        FilterRegistrationBean filterFilterRegistrationBean = new FilterRegistrationBean();
        AuthFilter authFilter = new AuthFilter(authenticatorImpl());
        filterFilterRegistrationBean.setFilter(authFilter);
        filterFilterRegistrationBean.addUrlPatterns("/api/*");

        //设置放行参数
        filterFilterRegistrationBean.addInitParameter(Const.UN_FILTER_KEY,
                Joiner.on(",").join(Const.URL+"login",Const.URL+"xxx"));

        filterFilterRegistrationBean.setEnabled(true);

        return filterFilterRegistrationBean;
    }

    @Bean
    public AuthenticatorImpl authenticatorImpl(){
        //

        String secret = applicationContext.getEnvironment().getProperty("jwt.auth.secret");

        return new AuthenticatorImpl(secret);
    }
}
