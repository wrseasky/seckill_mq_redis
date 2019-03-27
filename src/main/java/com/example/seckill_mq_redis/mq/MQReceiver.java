package com.example.seckill_mq_redis.mq;


import com.example.seckill_mq_redis.entity.GoodsVo;
import com.example.seckill_mq_redis.entity.SeckillMessage;
import com.example.seckill_mq_redis.entity.SeckillOrder;
import com.example.seckill_mq_redis.entity.SeckillUser;
import com.example.seckill_mq_redis.service.GoodsService;
import com.example.seckill_mq_redis.service.OrderService;
import com.example.seckill_mq_redis.service.RedisService;
import com.example.seckill_mq_redis.service.SeckillService;
import com.example.seckill_mq_redis.util.MQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    private Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;


    @RabbitListener(queues= MQConfig.SECKILL_QUEUE)
    public void receiveSeckill(String message) {
        logger.info("receive message:"+message);
        SeckillMessage mm  = redisService.stringToBean(message, SeckillMessage.class);
        SeckillUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        seckillService.seckill(user, goods);
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String msg){
        logger.info("receive:" + msg);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String msg){
        logger.info("receiveTopic1:" + msg);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String msg){
        logger.info("receiveTopic2:" + msg);
    }

    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void receiveHeaders(byte[] msg){
        logger.info("receiveHeaders:" + new String(msg));
    }

}
