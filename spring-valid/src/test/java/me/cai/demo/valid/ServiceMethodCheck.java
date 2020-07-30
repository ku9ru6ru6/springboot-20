package me.cai.demo.valid;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.valid.service.HelloService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringValidStarter.class)
public class ServiceMethodCheck {

    @Autowired
    private HelloService helloService;

    @Test
    @DisplayName("简单参数测试")
    public void testParamCheck() {
        Assert.assertThrows(ConstraintViolationException.class, () ->  helloService.testCallSelf(1, "a"));
    }

    @Test
    public void testCallSelf() {
        Assert.assertThrows(ConstraintViolationException.class, () -> helloService.testCallSelf(10, "a"));
    }
}
