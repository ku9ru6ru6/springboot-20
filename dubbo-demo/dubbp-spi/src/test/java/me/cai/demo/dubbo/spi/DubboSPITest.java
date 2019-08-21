package me.cai.demo.dubbo.spi;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

/**
 * me.cai.demo.dubbo.spi
 *
 * @author caiguangzheng
 * date: 2019-08-21
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
public class DubboSPITest {

    @Test
    public void sayHello() {
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();

        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}
