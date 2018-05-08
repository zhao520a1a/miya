package com.miya.redis;


public interface RedisService {

    void set(String key, String value, long expire);

    void set(String key, String value);

    String get(String key);

    void expire(String key, long expire);

    void delete(String key);

    Long incr(String key);

    Boolean exists(String key);

}