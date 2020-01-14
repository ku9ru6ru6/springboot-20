package me.cai.demo.proxy.spring.test;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

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

    @Before("@annotation(spElParamTest)")
    public void before(JoinPoint joinPoint, SpElParamTest spElParamTest) {
        String result = this.executeTemplate(spElParamTest.spel(), joinPoint, String.class);
        log.debug("result is {}", result);
    }

    private <T> T executeTemplate(String spelStr, JoinPoint joinPoint, Class<T> tClass) {

        ExpressionParser parser = new SpelExpressionParser();

        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        String[] params = discoverer.getParameterNames(method);
        if (Objects.isNull(params)) {
            return null;
        }

        Object[] args = joinPoint.getArgs();

        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        return parser.parseExpression(spelStr, new TemplateParserContext()).getValue(context, tClass);
    }

}
