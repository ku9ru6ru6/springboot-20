package me.cai.demo.proxy.spring.test;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * me.cai.demo.proxy.spring.test
 *
 * @author caiguangzheng
 * date: 2019-08-19
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Aspect
@Component
public class MyInterceptor {

    @Around("@annotation(proxyTarget)")
    public void around(ProceedingJoinPoint joinPoint, ProxyTarget proxyTarget) {
        log.debug("around before");
        try {
            joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            log.error("get error:{}", Throwables.getStackTraceAsString(throwable));
        }
        log.debug("around after");
    }

    @Before("@annotation(proxyTarget)")
    public void before(JoinPoint joinPoint, ProxyTarget proxyTarget) {
        log.debug("before");
    }

    @After("@annotation(ProxyTarget)")
    public void after(JoinPoint joinPoint) {
        log.debug("after");
    }

    @Before("target(me.cai.demo.proxy.spring.test.UserService)")
    public void beforeTarget() {
        log.debug("beforeTarget");
    }

}
