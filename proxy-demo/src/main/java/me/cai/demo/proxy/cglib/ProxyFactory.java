package me.cai.demo.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * me.cai.demo.proxy.cglib
 *
 * @author caiguangzheng
 * date: 2019-08-19
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
public class ProxyFactory<T> implements MethodInterceptor {

    private T target;

    public ProxyFactory(T target) {
        this.target = target;
    }

    public T getProxyInstance() {
        //工具类
        Enhancer en = new Enhancer();
        //设置父类
        en.setSuperclass(target.getClass());
        //设置回调函数
        en.setCallback(this);
        //创建子类对象代理
        return (T) en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.debug("开启事务");
        Object returnValue = methodProxy.invokeSuper(o, args);
        log.debug("结束事务");
        return returnValue;
    }
}
