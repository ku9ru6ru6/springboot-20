package me.cai.demo.dubbo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * me.cai.demo.dubbo.aop
 *
 * @author caiguangzheng
 * date: 2019-08-20
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Aspect
@Component
public class MyAop {

    @Before("services()")
    public void before(JoinPoint joinPoint) {
        log.debug("*********** before ***********");
    }

    @Pointcut("execution(* me.cai.demo.dubbo.provider..*.*(..))")
    public void services() {
    }
}
