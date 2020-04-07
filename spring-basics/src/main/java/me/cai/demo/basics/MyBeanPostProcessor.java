package me.cai.demo.basics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

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
public class MyBeanPostProcessor implements BeanPostProcessor {

    private final String targetBeanName = "person";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (targetBeanName.equals(beanName)) {
            log.info("post Process Before Initialization is invoked");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (targetBeanName.equals(beanName)) {
            log.info("post Process after Initialization is invoked");
        }
        return bean;
    }
}
