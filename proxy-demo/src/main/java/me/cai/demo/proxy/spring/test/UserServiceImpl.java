package me.cai.demo.proxy.spring.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * me.cai.demo.proxy.spring.test
 *
 * @author caiguangzheng
 * date: 2019-08-19
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Override
    @ProxyTarget
    public void sayHello() {
        log.debug("Hello!");
    }

    @Override
    @SpElParamTest(spel = "#{#person.name}")
    public void paramSpElTest(Person person) {
        log.debug("complete paramSpElTest");
    }
}
