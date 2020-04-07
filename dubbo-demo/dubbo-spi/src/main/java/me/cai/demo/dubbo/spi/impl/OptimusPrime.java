package me.cai.demo.dubbo.spi.impl;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.dubbo.spi.Robot;

/**
 * me.cai.demo.dubbo.spi.impl
 *
 * @author caiguangzheng
 * date: 2019-08-21
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
public class OptimusPrime implements Robot {

    @Override
    public void sayHello() {
        log.debug("Hello, I am Optimus Prime.");
    }
}
