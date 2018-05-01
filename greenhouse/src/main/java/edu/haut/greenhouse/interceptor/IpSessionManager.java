package edu.haut.greenhouse.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import edu.haut.greenhouse.common.cache.CacheableList;
import edu.haut.greenhouse.common.cache.PrimitiveCacheable;
import edu.haut.greenhouse.common.util.SerializeUtils;
import edu.haut.greenhouse.common.util.redis.RedisManager;

public class IpSessionManager {
	
	private static Logger log = LoggerFactory.getLogger(IpSessionManager.class);
    
    public static final int SESSION_EXPIRE_TIME = 60 * 60 * 24;

    public static final String VERIFYCODE_PREFIX = "ip_verifyCode_";
    
    public static final String REQUEST_PREFIX = "ip_request_";
    
    public static boolean isVerifyCodeLimited(String ip) {
        if (StringUtils.isEmpty(ip)) return true;
        try {
            byte[] key = (VERIFYCODE_PREFIX + ip).getBytes();
            byte[] result = RedisManager.get(key);
            Integer count = 0;
            if (result != null) {
                PrimitiveCacheable origin = SerializeUtils.deseialize(result, PrimitiveCacheable.class);
                count = origin.get();
            }
            count++;
            RedisManager.set(key, SerializeUtils.serialize(new PrimitiveCacheable(count)), SESSION_EXPIRE_TIME);
            if (count <= 5) {
                return false;
            } else {
                log.warn("verify code trigger threshold, ip = " + ip + ", count = " + count);
            }
        } catch(Exception e) {
            log.warn("get verify code count failed, ip = " + ip, e);
        }
        return true;
    }
    
    public static boolean isRequestLimited(String ip) {
        if (StringUtils.isEmpty(ip)) return false;
        try {
            byte[] key = (REQUEST_PREFIX + ip).getBytes();
            byte[] result = RedisManager.get(key);
            List<Long> requests;
            if (result != null) {
                CacheableList list = SerializeUtils.deseialize(result, CacheableList.class);
                requests = list.get();
            } else {
                requests = new ArrayList<Long>();
            }
            long now = System.currentTimeMillis();
            requests.add(now);
            int requestPerMinute = 0, requestPerHour = 0;
            Iterator<Long> it = requests.iterator();
            while (it.hasNext()) {
                long time = it.next();
                if (now - time < 60 * 1000L) {
                    requestPerMinute++;
                }
                if (now - time < 60 * 60 * 1000L) {
                    requestPerHour++;
                } else {
                    it.remove();
                }
            }
            RedisManager.set(key, SerializeUtils.serialize(new CacheableList(requests, Long.class)), SESSION_EXPIRE_TIME);
            if (requestPerMinute > 180) {
                log.warn("request frequency trigger minutely threshold, ip = " + ip
                        + ", request per minute = " + requestPerMinute + ", requests = " + requests);
                return true;
            } else if (requestPerHour > 1440) {
                log.warn("request frequency trigger hourly threshold, ip = " + ip
                        + ", request per hour = " + requestPerHour + ", requests = " + requests);
                return true;
            }
            return false;
        } catch(Exception e) {
            log.warn("get verify code count failed, ip = " + ip, e);
            return false;
        }
    }

}
