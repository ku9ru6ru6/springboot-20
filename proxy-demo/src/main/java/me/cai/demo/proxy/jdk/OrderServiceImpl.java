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
public class OrderServiceImpl implements OrderService, OtherService {
    @Override
    public void save() {
        log.debug("保存数据");
    }

    @Override
    public void update() {
        log.debug("更新数据");
    }

    @Override
    public void sayHello() {
        log.debug("Hello");
    }

    @Override
    public String toString() {
        return "OrderServiceImpl{}";
    }
}
