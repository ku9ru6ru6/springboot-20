package me.cai.demo.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * me.cai.demo.rocketmq.producer
 *
 * @author caiguangzheng
 * date: 2019-07-11
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "transactionalTest")
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.debug("TransactionListenerImpl executeLocalTransaction !!!");
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return null;
    }
}
