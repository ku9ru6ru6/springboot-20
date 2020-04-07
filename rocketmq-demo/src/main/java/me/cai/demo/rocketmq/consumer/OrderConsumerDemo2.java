package me.cai.demo.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
@RocketMQMessageListener(topic = "testTopic", consumerGroup = "OrderConsumerDemo",
        selectorExpression = "order", consumeMode = ConsumeMode.ORDERLY
)
public class OrderConsumerDemo2 implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        log.debug("OrderConsumerDemo receive msgID:{}", messageExt.getMsgId());
        String payload = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.debug("OrderConsumerDemo receive message:{}", payload);
    }
}
