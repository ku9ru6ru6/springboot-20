package me.cai.demo.valid.service;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.valid.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;


@Slf4j
@Service
@Validated(Default.class)
public class HelloServiceImpl implements HelloService {

    @Lazy
    @Autowired
    private HelloService helloService;

    @Override
    public Object testCallSelf(Integer id, String name) {
        // 调用本类方法
        helloService.cascade(null, null);
        return null;
    }

    @Override
    public String cascade(Person father, Person mother) {
        return "hello cascade...";
    }
}
