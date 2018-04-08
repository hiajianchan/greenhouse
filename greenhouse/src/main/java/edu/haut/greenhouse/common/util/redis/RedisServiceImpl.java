package edu.haut.greenhouse.common.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired(required = false)                 //不是必须从spring中构造bean对象
	private ShardedJedisPool shardedJedisPool;
	
	private <T> T execute(Function<T, ShardedJedis> fun){
		ShardedJedis shardedJedis = null;
		try {
			//从连接池中获取jedis分片对象
			shardedJedis = shardedJedisPool.getResource();
			return fun.callback(shardedJedis);
		} finally {
			if (shardedJedis != null) {
				//关闭，检测连接是否有效，有效则放回连接池中，无效则重置
				shardedJedis.close();
			}
		}
	}
	
	/**
	 * redis的set指令操作
	 */
	public String set(final String key, final String value) {
		return this.execute(new Function<String, ShardedJedis>() {

			public String callback(ShardedJedis e) {
				return e.set(key, value);
			}
		});
	}

	/**
	 * 执行redis的get指令操作
	 */
	public String get(final String key) {
		return this.execute(new Function<String, ShardedJedis>() {

			public String callback(ShardedJedis e) {
				return e.get(key);
			}
		});
	}

	/**
	 * 执行del操作
	 */
	public Long del(final String key) {
		return this.execute(new Function<Long, ShardedJedis>() {

			public Long callback(ShardedJedis e) {
				return e.del(key);
			}
		});
	}

	/**
	 * 设置数据生存时间，单位为： 秒
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(final String key, final Integer seconds) {
		return this.execute(new Function<Long, ShardedJedis>() {

			public Long callback(ShardedJedis e) {
				return e.expire(key, seconds);
			}
		});
	}

	/**
	 * 执行set操作，并未数据设置生存时间
	 */
	public String set(String key, String value, Integer seconds) {
		return this.execute(new Function<String, ShardedJedis>() {

			public String callback(ShardedJedis e) {
				String setStr = e.set(key, value);
				e.expire(key, seconds);
				return setStr;
			}
		});
	}
	


}
