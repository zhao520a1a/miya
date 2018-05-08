package com.miya.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Service
public class RedisServiceImpl  implements  RedisService{


    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Resource
    protected ValueOperations<String, String> valueOperations;


    /**
     * 添加
     */
    public void set(String key, String value, long expire) {
        valueOperations.set(key, value, expire,TimeUnit.SECONDS);
    }

    /**
     * 添加
     */
    public void set(String key, String value) {
        valueOperations.set(key, value);
    }

    /**
     * 设置过期时间
     */
    public void expire(String key,  long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 查询
     */
    public String get(String key) {
        return valueOperations.get(key);
    }


    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Long incr (String key) {
//        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
//        return counter.addAndGet(1);
        return valueOperations.increment(key, 1);
    }

    public Boolean exists (String key) {
       return redisTemplate.hasKey(key);
    }




    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ValueOperations<String, String> getValueOperations() {
        return valueOperations;
    }

    public void setValueOperations(ValueOperations<String, String> valueOperations) {
        this.valueOperations = valueOperations;
    }
}