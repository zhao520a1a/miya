package com.miya.jedis;


public interface JedisClient {

//	String set(String key, String value);
	void set(String key, String value);
	String get(String key);
	boolean expire(String key, int seconds);
//	boolean exists(String key);
//	Long ttl(String key);
//	Long incr(String key);
//	Long hset(String key, String field, String value);
//	String hget(String key, String field);
//	Long hdel(String key, String... field);
}
