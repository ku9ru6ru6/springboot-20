package me.cai.demo.dubbo.provider.impl;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.dubbo.bean.MySpringBean;
import me.cai.demo.dubbo.provider.ProviderDemo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * me.cai.demo.dubbo.provider.impl
 *
 * @author caiguangzheng
 * date: 2019-07-20
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Service(timeout = 1000, retries = 0)
public class ProviderDemoImpl implements ProviderDemo {

    @Autowired
    private MySpringBean mySpringBean;

    @Override
    public String sayHello() {
        log.info(mySpringBean.toString());
        return "Hello";
    }

    @Override
    public String timeOut() {
        return "Hello";
    }
}
