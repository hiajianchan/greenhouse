package edu.haut.greenhouse.common.util.redis;

public interface RedisService {

	//set操作
	public String set(String key, String value);
	//get操作
	public String get(String key);
	//删除操作
	public Long del(String key);
	//设置生存时间
	public Long expire(String key, Integer seconds);
	//执行set方法，并设置生存时间
	public String set(String key, String value, Integer seconds);
	
}
