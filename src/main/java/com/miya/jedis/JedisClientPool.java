package com.miya.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service("JedisClientPool")
public class JedisClientPool implements JedisClient {

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	@Resource
	protected ValueOperations<String, String> valueOperations;

	@Override
	public void set(String key, String value) {
		  valueOperations.set(key, value);
	}

	@Override
	public String get(String key) {
		String result = valueOperations.get(key);
		return result;
	}


	@Override
	public boolean expire(String key, int seconds) {
		return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}





}
