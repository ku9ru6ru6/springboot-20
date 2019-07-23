package me.cai.demo.dubbo.provider.impl;

import me.cai.demo.dubbo.provider.ProviderDemo;
import org.apache.dubbo.config.annotation.Service;


/**
 * me.cai.demo.dubbo.provider.impl
 *
 * @author caiguangzheng
 * date: 2019-07-20
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Service
public class ProviderDemoImpl implements ProviderDemo {

    @Override
    public String sayHello() {
        return "Hello";
    }
}
