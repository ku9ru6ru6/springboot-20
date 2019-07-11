package me.cai.demo.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import javax.annotation.PostConstruct;

/**
 * me.cai.demo.rocketmq
 *
 * @author caiguangzheng
 * date: 2019-07-11
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Configuration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type=FilterType.ANNOTATION, classes = RocketMQMessageListener.class)
})
public class RocketMqTestConfig {

    @PostConstruct
    public void init() {
        log.debug("RocketMqTestConfig init!!!");
    }
}
