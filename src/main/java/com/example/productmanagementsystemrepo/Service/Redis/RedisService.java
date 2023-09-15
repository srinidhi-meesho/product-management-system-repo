package com.example.productmanagementsystemrepo.Service.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String>redisTemplate;
    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public void setKeyValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
