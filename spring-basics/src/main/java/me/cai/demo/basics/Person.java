package me.cai.demo.basics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * me.cai.demo.basics
 *
 * @author caiguangzheng
 * date: 2020/4/7
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Component
public class Person implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person() {
        log.info("Constructor of person bean is invoked!");
    }

    @Override
    public void setBeanName(String name) {
        log.info("setBeanName method of person is invoked, name:{}", name);
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("setBeanFactory method of person is invoked, beanFactory:{}", beanFactory);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet method of person bean is invoked!");
    }

    @Override
    public void destroy() throws Exception {
        log.info("DisposableBean Destroy method of person bean is invoked!");
    }

    @PostConstruct
    public void init() {
        log.info("custom init method of person bean is invoked!");
    }

    @PreDestroy
    public void destroyMethod() {
        log.info("custom Destroy method of person bean is invoked!");
    }
}
