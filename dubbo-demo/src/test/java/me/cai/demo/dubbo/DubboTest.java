package me.cai.demo.dubbo;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.dubbo.provider.ProviderDemo;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * me.cai.demo.dubbo
 *
 * @author caiguangzheng
 * date: 2019-07-20
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboSpringBootStarter.class)
public class DubboTest {

    @Reference(version = "1.0")
    private ProviderDemo providerDemo;

    @Test
    public void providerDemoTest() {
        log.debug(providerDemo.sayHello());
    }
}
