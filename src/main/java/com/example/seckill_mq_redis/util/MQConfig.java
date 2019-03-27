package com.example.seckill_mq_redis.util;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";
    public static final String SECKILL_QUEUE = "queue";
    public static final String TOPIC_EXCHANGE = "queue";
    public static final String FANOUT_EXCHANGE = "queue";
    public static final String HEADERS_EXCHANGE = "queue";
    public static final String TOPIC_QUEUE1 = "queue";
    public static final String TOPIC_QUEUE2 = "queue";
    public static final String HEADERS_QUEUE = "queue";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE, true);
    }

}
