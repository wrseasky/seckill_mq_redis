package com.example.seckill_mq_redis.mq;

import com.example.seckill_mq_redis.entity.SeckillMessage;
import com.example.seckill_mq_redis.service.RedisService;
import com.example.seckill_mq_redis.util.MQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    private Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendSeckillMessage(SeckillMessage mm) {
        String msg = redisService.beanToString(mm);
        logger.info("send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);
    }

    /**
     * Direct 模式交换机
     *
     * @param obj
     */
    public void send(Object obj) {
        String msg = redisService.beanToString(obj);
        logger.info("sender send:" + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }

    public void sendTopic(Object message) {
        String msg = redisService.beanToString(message);
        logger.info("send topic message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
    }

    public void sendFanout(Object message) {
        String msg = redisService.beanToString(message);
        logger.info("send fanout message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
    }

    public void sendHeaders(Object message) {
        String msg = redisService.beanToString(message);
        logger.info("send sendHeaders message:" + msg);

        MessageProperties props = new MessageProperties();
        props.setHeader("header1", "value1");
        props.setHeader("header2", "value2");
        Message obj = new Message(msg.getBytes(), props);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
    }

}
