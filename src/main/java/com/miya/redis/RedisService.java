package com.miya.redis;

import java.util.List;
import java.util.Set;

public interface RedisService<T> {


    void put(String key, T doamin, long expire);

    void remove(String key);

    T get(String key);

    List<T> getAll();

    Set<String> getKeys();

    boolean isKeyExists(String key);

    long count();

    void empty();


}
