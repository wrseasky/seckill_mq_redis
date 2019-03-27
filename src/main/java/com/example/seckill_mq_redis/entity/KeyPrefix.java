package com.example.seckill_mq_redis.entity;

public class KeyPrefix {
    private String Prefix;

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public int expireSeconds(){
        return 0;
    }
}
