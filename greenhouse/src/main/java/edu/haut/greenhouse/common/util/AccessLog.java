package edu.haut.greenhouse.common.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @Description
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
public class AccessLog {
    
    private static final Log logger = LogFactory.getLog("access");
    
    private static final String ACCESS_START_TAG = "parallel.web.access.start";
    
    public static void startAccess(HttpServletRequest request) {
        request.setAttribute(ACCESS_START_TAG, System.currentTimeMillis());
    }
    
    public static Long getAccessElapse(HttpServletRequest request) {
        Long start = (Long)request.getAttribute(ACCESS_START_TAG);
        Long elapse = null;
        if (start != null) {
            elapse = System.currentTimeMillis() - start;
        }
        return elapse;
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response) {
        try {
            Date timestamp = new Date();
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String ip = WebUtils.getRealIp(request);
            String userAgent = WebUtils.getUserAgent(request);
            String csv = CSVUtils.toCSV(timestamp, method, uri, ip, userAgent, getAccessElapse(request));
            logger.info(csv);
        } catch(Exception e) {
        }
    }

}
