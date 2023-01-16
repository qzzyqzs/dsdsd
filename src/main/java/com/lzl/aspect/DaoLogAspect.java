package com.lzl.aspect;

import com.lzl.tool.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class DaoLogAspect {

    @Pointcut("execution(* com.lzl.mapper..*.*(..))") //"execution(* com.xzit.aspect.*.showMenu(..))"
    public void request() {
    }


    @Around("request()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("数据库开始执行:{}",proceedingJoinPoint.getSignature().toShortString());
        Object proceed = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("数据库执行耗时:{} ms", endTime - startTime);
        return proceed;
    }

}
