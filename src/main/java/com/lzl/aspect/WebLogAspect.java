package com.lzl.aspect;

import com.lzl.tool.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
@Order(1)
public class WebLogAspect {

    @Pointcut("execution(* com.lzl.controller..*.*(..))") //"execution(* com.xzit.aspect.*.showMenu(..))"
    public void request() {
    }

    @Before("request()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("请求地址:{}", request.getRequestURI().toString());
        log.info("请求类型:{}", request.getMethod());
        log.info("请求方法:{},{}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        log.info("请求参数:{}", JsonUtil.getJsonString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "request()", returning = "ret")
    public void doAfter(Object ret) {
        log.debug("返回值:{}", JsonUtil.getJsonString(ret));
    }

    @Around("request()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("业务层耗时:{} ms", endTime - startTime);
        return proceed;
    }

}
