package edu.haut.greenhouse.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @Description
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
public class ServiceDegradeManager {
	
	private static Logger log = LoggerFactory.getLogger(ServiceDegradeManager.class);
    
    private static ConcurrentHashMap<Integer, Long> id2Timestamp = new ConcurrentHashMap<Integer, Long>();
    
    private static Random rand = new Random();
    
    private static final String REQUEST_ID = "REQUEST_ID";
    
    private static final int DEGRADE_THRESHOLD = 10;
    
    private static final long MAX_REQUEST_TIME = 1000L * 10;
    
    static {
        Timer t = new Timer("RequestQueueManager");
        t.schedule(new TimerTask(){
            @Override
            public void run() {
                try {
                    List<Integer> ids = new ArrayList<Integer>();
                    ids.addAll(id2Timestamp.keySet());
                    for (Integer id : ids) {
                        Long timestamp = id2Timestamp.get(id);
                        if (timestamp != null && System.currentTimeMillis() - timestamp > MAX_REQUEST_TIME) {
                            id2Timestamp.remove(id);
                        }
                    }
                    log.info("Request queue size = " + id2Timestamp.size());
                } catch(Exception e) {
                    log.error(e.toString(), e.toString());
                }
            }
        }, 1000L * 30, 1000L * 10);
    }
    
    public static void startRequest(HttpServletRequest request) {
        try {
            int id = rand.nextInt();
            request.setAttribute(REQUEST_ID, id);
            id2Timestamp.put(id, System.currentTimeMillis());
        } catch(Exception e) {
            log.error(e.toString(), e.toString());
        }
    }
    
    public static void finishRequest(HttpServletRequest request) {
        try {
            Integer id = (Integer)request.getAttribute(REQUEST_ID);
            if (id != null) {
                id2Timestamp.remove(id);
            }
        } catch(Exception e) {
            log.error(e.toString(), e.toString());
        }
    }
    
    public static boolean isDegrade() {
        try {
            Integer cnt = id2Timestamp.size();
            if (cnt != null && cnt > DEGRADE_THRESHOLD) {
                return true;
            }
        } catch(Exception e) {
            log.error(e.toString(), e.toString());
        }
        return false;
    }

}
