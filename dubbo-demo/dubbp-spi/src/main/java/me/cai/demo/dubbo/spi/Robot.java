package me.cai.demo.dubbo.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * me.cai.demo.dubbo.spi
 *
 * @author caiguangzheng
 * date: 2019-08-21
 * mail: caiguangzheng@terminus.io
 * description:
 */
@SPI
public interface Robot {

    void sayHello();
}
