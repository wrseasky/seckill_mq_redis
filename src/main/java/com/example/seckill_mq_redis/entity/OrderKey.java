package com.example.seckill_mq_redis.entity;

public class OrderKey {
    public static KeyPrefix getSeckillOrderByUidGid;

    public static KeyPrefix getSeckillOrderByUidGid() {
        return new KeyPrefix();
    }

}
