package edu.haut.greenhouse.common.util.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol;

public class RedisManager {
	
	private static Logger log = LoggerFactory.getLogger(RedisManager.class);
	
	private static final boolean ISONLINE = false;
    
    private static final String REDIS_HOST = "redis-server";
    
    private static final String ONLINE_HOST = "39.106.184.51";
    
    private static final String ONLINE_PASSWORD = "";
   
    private static final String TEST_HOST = "127.0.0.1"; 
    private static final String TEST_PASSWORD = "";
    
    private static final JedisPool pool;
    
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setBlockWhenExhausted(false);
        config.setTestOnReturn(true);
        if (ISONLINE) {
            pool = new JedisPool(config, ONLINE_HOST, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT);
            log.info("redis start success");
        } else {
        	pool = new JedisPool(config, TEST_HOST, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT);
        }
    }
    
    public static byte[] get(byte[] key) {
        return get(key, -1);
    }
    
    public static byte[] get(byte[] key, int expireTime) {
        if (key == null) return null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            byte[] result = jedis.get(key);
            if (result != null && expireTime > 0) {
                jedis.expire(key, expireTime);
            }
            return result;
        } catch(Exception e) {
            log.warn("get from redis failed. key = " + new String(key));
            return null;
        } finally {
            if (jedis != null) {
                pool.returnResourceObject(jedis);
            }
        }
    }
    
    public static List<byte[]> mget(Collection<byte[]> keys) {
        if (CollectionUtils.isEmpty(keys)) return Collections.emptyList();
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            byte[][] params = new byte[keys.size()][];
            params = keys.toArray(params);
            List<byte[]> results = jedis.mget(params);
            return results;
        } catch(Exception e) {
            List<String> keyStrs = new ArrayList<String>();
            for (byte[] key : keys) {
                keyStrs.add(new String(key));
            }
            log.warn("mget from redis failed. keys = " + keyStrs);
            List<byte[]> results = new ArrayList<byte[]>();
            for (int i = 0; i < keys.size(); i++) {
                results.add(null);
            }
            return results;
        } finally {
            if (jedis != null) {
                pool.returnResourceObject(jedis);
            }
        }
    }
    
    public static boolean set(byte[] key, byte[] value) {
        return set(key, value, -1);
    }
    
    public static boolean set(byte[] key, byte[] value, int expireTime) {
        if (key == null || value == null) return false;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (expireTime > 0) {
                jedis.setex(key, expireTime, value);
            } else {
                jedis.set(key, value);
            }
        } catch(Exception e) {
            log.warn("set to redis failed. key = " + new String(key), e);
            return false;
        } finally {
            if (jedis != null) {
                pool.returnResourceObject(jedis);
            }
        }
        return true;
    }
    
    public static boolean mset(Map<byte[], byte[]> map) {
        return mset(map, -1);
    }
    
    public static boolean mset(Map<byte[], byte[]> map, int expireTime) {
        if (map == null || map.size() == 0) return false;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (Entry<byte[], byte[]> entry : map.entrySet()) {
                byte[] key = entry.getKey();
                byte[] value = entry.getValue();
                if (expireTime > 0) {
                    pipeline.setex(key, expireTime, value);
                } else {
                    pipeline.set(key, value);
                }
            }
            pipeline.sync();
            return true;
        } catch(Exception e) {
            log.warn("mset to redis failed. data = " + map);
            return false;
        } finally {
            if (jedis != null) {
                pool.returnResourceObject(jedis);
            }
        }
    }
    
    public static boolean del(byte[] key) {
        if (key == null) return false;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
            return true;
        } catch(Exception e) {
            log.warn("del from redis failed. key = " + new String(key));
            return false;
        } finally {
            if (jedis != null) {
                pool.returnResourceObject(jedis);
            }
        }
    }

    public static boolean mdel(Collection<byte[]> keys) {
        if (CollectionUtils.isEmpty(keys)) return false;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (byte[] key : keys) {
                jedis.del(key);
            }
            pipeline.sync();
            return true;
        } catch(Exception e) {
            List<String> keyStrs = new ArrayList<String>();
            for (byte[] key : keys) {
                keyStrs.add(new String(key));
            }
            log.warn("mdel from redis failed. keys = " + keyStrs);
            return false;
        } finally {
            if (jedis != null) {
                pool.returnResourceObject(jedis);
            }
        }
    }
    
    /**
     * 判断当前的k值是否存在
     * @param keys
     * @return
     */
    public static boolean exists(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
            if (jedis != null) {
                pool.returnResourceObject(jedis);
            }
		}
		
	}

    /**
     * redis test 
     * must set redis-server 
     * local redis-server need add 127.0.0.1  redis-server to hosts
     * @param args
     */
    public static void main(String[] args) {
		//RedisManager.set("qinshihuang".getBytes(), "yingzheng".getBytes());
    	
    	//RedisManager.set("qinshihuang3".getBytes(), "yingzheng3".getBytes(), 10);
		//System.out.println(new String(RedisManager.get("qinshihuang3".getBytes())));
		
		/*RedisManager.set("appid".getBytes(), "uuid;987654".getBytes());
		String ss=new String(RedisManager.get("appid".getBytes()));
		String[] nn=ss.split(";");
		System.out.println(nn[0]+"-----");
		System.out.println(nn[1]+"------------");*/
		
		Jedis jedis = pool.getResource();
		 
//		jedis.lpush("apikey".getBytes(), "uuid".getBytes());
//		jedis.rpush("apikey".getBytes(), "987654".getBytes());
//		
//		jedis.expire("apikey".getBytes(), 50);
//		
//		System.out.println(new String(jedis.lindex("apikey".getBytes(), 0)));
//		
//		System.out.println(new String(jedis.lindex("apikey".getBytes(), 1)));
//		
//		jedis.lrem("apikey".getBytes(), 0, "uuid".getBytes());
//		jedis.lpush("apikey".getBytes(), "uussid".getBytes());
//		jedis.lrem("apikey".getBytes(), 0, "987654".getBytes());
//		jedis.rpush("apikey".getBytes(), "333333".getBytes());
//		System.out.println(new String(jedis.lindex("apikey".getBytes(), 0))+"---");
//		
//		System.out.println(new String(jedis.lindex("apikey".getBytes(), 1))+"===");
		jedis.set("k22", "test1");
		jedis.get("k22");
		
		
	}
}
