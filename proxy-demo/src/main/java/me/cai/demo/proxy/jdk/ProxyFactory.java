package me.cai.demo.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * me.cai.demo.proxy.jdk
 *
 * @author caiguangzheng
 * date: 2019-08-19
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
public class ProxyFactory {

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance(InvocationHandler invocationHandler) {
        Class tClass = target.getClass();
        return Proxy.newProxyInstance(tClass.getClassLoader(), tClass.getInterfaces(), invocationHandler);
    }
}
