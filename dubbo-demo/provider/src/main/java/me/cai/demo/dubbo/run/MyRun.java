package me.cai.demo.dubbo.run;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.dubbo.provider.ProviderDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * me.cai.demo.dubbo.run
 *
 * @author caiguangzheng
 * date: 2019-08-20
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Component
public class MyRun implements CommandLineRunner {

    @Autowired
    private ProviderDemo providerDemo;

    @Override
    public void run(String... args) throws Exception {
        log.debug(providerDemo.sayHello());
    }
}
