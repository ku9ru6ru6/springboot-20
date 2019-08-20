package me.cai.demo.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

/**
 * me.cai.demo.proxy.jdk
 *
 * @author caiguangzheng
 * date: 2019-08-19
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
public class JDKProxyMainTest {

    public static void main(String[] args) {
        OrderService target = new OrderServiceImpl();
        log.debug(target.getClass().toString());

        Object proxy = new ProxyFactory(target).getProxyInstance((proxy1, method, args1) -> {
            log.debug("before");
            Object value = method.invoke(target, args1);
            log.debug("after");
            return value;
        });
        log.debug(proxy.getClass().toString() + "\n\n");


        OrderService orderServiceProxy = (OrderService) proxy;
        orderServiceProxy.save();
        orderServiceProxy.update();
        System.out.println(orderServiceProxy);

        OtherService otherServiceProxy = (OtherService) proxy;
        otherServiceProxy.sayHello();
    }
}
