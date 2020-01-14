package me.cai.demo.proxy.spring.run;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.proxy.spring.test.Person;
import me.cai.demo.proxy.spring.test.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * me.cai.demo.proxy.spring.run
 *
 * @author caiguangzheng
 * date: 2019-08-20
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.paramSpElTest(new Person("cai", 25));
    }
}
