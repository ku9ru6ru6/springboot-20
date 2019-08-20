package me.cai.demo.proxy.spring;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.proxy.spring.test.UserService;
import me.cai.demo.proxy.spring.test.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * me.cai.demo.proxy.spring
 *
 * @author caiguangzheng
 * date: 2019-08-19
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStart.class)
public class ApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testA() {
        userService.sayHello();
        log.debug(userService.toString());
    }

    @Test
    public void testB() {
        UserService userService = ProxyFactory.getProxy(UserService.class, new TargetSource() {
            @Override
            public Class<?> getTargetClass() {
                return UserService.class;
            }

            @Override
            public boolean isStatic() {
                return false;
            }

            @Override
            public Object getTarget() throws Exception {
                return new UserServiceImpl();
            }

            @Override
            public void releaseTarget(Object target) throws Exception {

            }
        });
        userService.sayHello();
        log.debug(userService.toString());
    }
}
