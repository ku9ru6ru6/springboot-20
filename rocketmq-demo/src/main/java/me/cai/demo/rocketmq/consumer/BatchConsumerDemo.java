package me.cai.demo.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * me.cai.demo.rocketmq.consumer
 *
 * @author caiguangzheng
 * date: 2019-07-11
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "testTopic", consumerGroup = "BatchConsumerDemo", selectorExpression = "batch")
public class BatchConsumerDemo implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        log.debug("BatchConsumerDemo receive msgID:{}", messageExt.getMsgId());
        String payload = new String(messageExt.getBody(), Charset.forName("utf-8"));
        log.debug("BatchConsumerDemo receive message:{}", payload);
    }
}
