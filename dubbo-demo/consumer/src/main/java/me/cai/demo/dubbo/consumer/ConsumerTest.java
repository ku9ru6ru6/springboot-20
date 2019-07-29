package me.cai.demo.dubbo.consumer;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.dubbo.provider.ProviderDemo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * me.cai.demo.dubbo.consumer
 *
 * @author caiguangzheng
 * date: 2019-07-29
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Component
public class ConsumerTest implements CommandLineRunner {

    @Reference(version = "1.0", mock = "me.cai.demo.dubbo.provider.ProviderDemoMock")
    private ProviderDemo providerDemo;

    @Override
    public void run(String... args) {
        testSayHello();
        testTimeOut();
    }

    private void testSayHello() {
        log.debug(providerDemo.sayHello());
    }

    private void testTimeOut() {
        log.debug(providerDemo.timeOut());
    }
}
