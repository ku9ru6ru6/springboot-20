package me.cai.demo.rocketmq;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.rocketmq.message.MessageDTO;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * me.cai.demo.rocketmq
 *
 * @author caiguangzheng
 * date: 2019-07-11
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketMqSpringBootStarter.class)
public class RocketMqTest {

    @Autowired
    private RocketMQTemplate rocketmqtemplate;

    private final static String TOPIC = "testTopic";

    @Test
    public void testSimpleConsumer() {
        String destination = TOPIC + ":" + "testSimpleConsumer";
        SendResult sendResult = rocketmqtemplate.syncSend(destination,
                MessageBuilder.withPayload(new MessageDTO(1, "message1")).build());
        this.judgeSendResult(sendResult);
    }

    @Test
    public void testBatchConsumer() {
        String destination = TOPIC + ":" + "batch";
        List<Message<MessageDTO>> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add(MessageBuilder.withPayload(new MessageDTO(i, "message" + i)).build());
        }
        SendResult sendResult = rocketmqtemplate.syncSend(destination, messages, 6000);
        this.judgeSendResult(sendResult);
    }

    @Test
    public void testOrderConsumer() {
        String destination = TOPIC + ":" + "order";
        List<Message<MessageDTO>> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add(MessageBuilder.withPayload(new MessageDTO(i, "message" + i)).build());
        }
        for (Message<MessageDTO> it : messages) {
            SendResult sendResult = rocketmqtemplate.syncSendOrderly(destination, it, "order");
            this.judgeSendResult(sendResult);
        }
    }

    @Test
    public void testTransactionalConsumer() {
        String destination = TOPIC + ":" + "transactional";
        SendResult sendResult = rocketmqtemplate.sendMessageInTransaction("transactionalTest", destination,
                MessageBuilder.withPayload(new MessageDTO(1, "message1")).build(), null);
        this.judgeSendResult(sendResult);
    }


    private void judgeSendResult(SendResult sendResult) {
        if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
            log.debug("send success, msgID:{}", sendResult.getMsgId());
        } else {
            log.error("send failed, msgID:{}, cause:{}", sendResult.getMsgId(), sendResult.getSendStatus().name());
            Assert.fail();
        }
    }
}
