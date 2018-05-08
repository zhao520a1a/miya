package com.miya.redis;


import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Set;

public interface RedisService {

    void set(String key, String value, long expire);

    String get(String key);

    void expire(String key, long expire);

    void delete(String key);





}