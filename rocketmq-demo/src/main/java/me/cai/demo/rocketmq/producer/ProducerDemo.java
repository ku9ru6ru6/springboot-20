package me.cai.demo.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.rocketmq.message.MessageDTO;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * me.cai.demo.rocketmq.producer
 *
 * @author caiguangzheng
 * date: 2019-07-10
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@Component
public class ProducerDemo {

    @Autowired
    private RocketMQTemplate rocketmqtemplate;

    public void send(String topic, String tag, MessageDTO message) {
        SendResult sendResult = rocketmqtemplate.syncSend(topic + ":" + tag, MessageBuilder.withPayload(message).build());
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.debug("sendResult success, msgID:{}", sendResult.getMsgId());
        }
    }
}
