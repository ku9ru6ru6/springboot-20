package me.cai.demo.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * me.cai.demo.dubbo
 *
 * @author caiguangzheng
 * date: 2019-07-20
 * mail: caiguangzheng@terminus.io
 * description:
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class ProviderSpringBootStarter {
    public static void main(String[] args) {
        SpringApplication.run(ProviderSpringBootStarter.class);
    }
}
