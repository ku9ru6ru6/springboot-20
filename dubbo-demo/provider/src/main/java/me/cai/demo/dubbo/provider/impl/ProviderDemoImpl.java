package me.cai.demo.dubbo.provider.impl;

import me.cai.demo.dubbo.provider.ProviderDemo;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.TimeUnit;


/**
 * me.cai.demo.dubbo.provider.impl
 *
 * @author caiguangzheng
 * date: 2019-07-20
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Service(timeout = 1000, retries = 0)
public class ProviderDemoImpl implements ProviderDemo {

    @Override
    public String sayHello() {
        return "Hello";
    }

    @Override
    public String timeOut() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello";
    }
}
