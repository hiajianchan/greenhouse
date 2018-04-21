package edu.haut.greenhouse.common.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


public class WebUtils {
	
	private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    private static final int COOKIE_DEFAULT_TTL = 3600 * 24 * 3650; // 10 years

    public static final String ROBOT_JUDGE_CID = "RCID";
    
    public static final String COOKIE_LOGIN_USERNAME = "LOGIN_USERNAME";
    
   
    public static Boolean getBoolean(HttpServletRequest request, String param,
            Boolean defaultValue) {
        try {
            String v = getNullIfEmpty(request, param);
            return !(v.equals("0") || v.equals("false"));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer getInt(HttpServletRequest request, String param,
            Integer defaultValue) {
        try {
            return Integer.parseInt(request.getParameter(param));
        } catch (Exception e) {
            try {
                if (request.getAttribute(param) != null) {
                    return (Integer)request.getAttribute(param);
                } else {
                    return defaultValue;
                }
            } catch(Exception e1) {
                return defaultValue;
            }
        }
    }
    
    public static Long getLong(HttpServletRequest request, String param,
            Long defaultValue) {
        try {
            return Long.parseLong(request.getParameter(param));
        } catch (Exception e) {
        	try {
        		 if (request.getAttribute(param) != null) {
                     return (Long)request.getAttribute(param);
                 } else {
                     return defaultValue;
                 }
			} catch (Exception e2) {
				return defaultValue;
			}
        	
        }
    }
    
    public static Double getDouble(HttpServletRequest request, String param,
            Double defaultValue) {
        try {
            return Double.parseDouble(request.getParameter(param));
        } catch(Exception e) {
            return defaultValue;
        }
    }
    
    public static Float getFloat(HttpServletRequest request, String param,
            Float defaultValue) {
        try {
            return Float.parseFloat(request.getParameter(param));
        } catch(Exception e) {
            return defaultValue;
        }
    }
    
   
    
    private static String[] whiteParamList = new String[]{"signature", "nonce", "password", "token", "euid", "echostr", "iid"};
    
    private static String filterXss(String content, String param) {
        if (content == null) return null;
        for (String white : whiteParamList) {
            if (white.equals(param)) {
                return content;
            }
        }
        return content.replaceAll("<", "").replaceAll(">", "");
    }
    
    public static void main(String[] args) {
        String s = "\\u003cimg src=1 onerror=alert(/xss/)\\u003e";
        System.out.println(s.replaceAll("<", ""));
    }

    public static String getNullIfEmpty(HttpServletRequest request, String param) {
        String result = StringUtil.trimSpace(request.getParameter(param));
        if (result == null || "".equals(result)) {
            result = StringUtil.trimSpace((String)request.getAttribute(param));
        }
        return filterXss(result, param);
    }
    
    public static String getNullIfEmptyWithDecode(HttpServletRequest request, String param) {
        String str = StringUtil.trimSpace(request.getParameter(param));
        if (str != null) {
            try {
                str = URLDecoder.decode(str, "utf-8");
                return filterXss(str, param);
            } catch(Exception e) {}
        }
        return str;
    }

    
    public static String getWithHeaders(String url, Map<String, String> headersMap, Map<String, String> params){    	
    	HttpClient client = new DefaultHttpClient();
    	HttpGet request;
    	List<NameValuePair> paramArray = new ArrayList<NameValuePair>();
    	URI uri = null;
    	String responseText = null;
    	
    	if(params != null){    		    		
    		for(Map.Entry<String, String> param : params.entrySet()){
    			paramArray.add( new BasicNameValuePair(param.getKey(), param.getValue()));			    			
    		}    		    		
    	}
    	
        try {
            uri = new URI( url + "?" + URLEncodedUtils.format( paramArray, "utf-8" ));
        } catch (URISyntaxException e1) {
            return null;
		}
        request = new HttpGet(uri);    	    	    
    	    	
    	if(headersMap != null){
        	for(Map.Entry<String, String> header : headersMap.entrySet()){    		
        		request.addHeader(header.getKey(), header.getValue());	
        	}    		
    	}
    	
    	try{
    		HttpResponse response = client.execute(request);
    		HttpEntity entity = response.getEntity();
    		if(entity != null){
    			responseText = EntityUtils.toString(entity);
    		} else {
    			responseText = "{\"code\":500, \"msg\":\"empty response\"}";
    		}
    	}catch(Exception e){
            log.error(e.toString(), e.toString());
            responseText = "{\"code\":500, \"msg\":\"" + e.getMessage() + "\"}";            
    	}finally{
            try {
                request.releaseConnection();
            } catch (Exception e) {
            	log.error(e.toString(), e.toString());
            }    		
    	}
    	return responseText;    	
    }

    /**
     * @return null when sum failed
     */
    public static Integer getSum(HttpServletRequest request, String param,
            Integer defaultValue) {
        String[] values = request.getParameterValues(param);
        if (values != null) {
            try {
                int result = 0;
                for (String v : values) {
                    result += Integer.parseInt(v);
                }
                return result;
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Called before view returned by controllers which may be embedded in other
     * pages.
     * 
     * @param request
     *            assert non-null
     * @param map
     *            model, assert non-null
     */
    public static void checkEmbedded(HttpServletRequest request,
            Map<String, Object> map) {
        String key = "embedded";
        if (getBoolean(request, key, false)) {
            map.put(key, true);
        }
    }

    public static Cookie setCookie(HttpServletResponse response, String key,
            String value) {
        Cookie result = new Cookie(key, value);
        result.setPath("/"); // global
        result.setMaxAge(COOKIE_DEFAULT_TTL);
        if (log.isDebugEnabled()) {
            log.debug("Set cookie: key=" + key + ", value=" + value);
        }
        response.addHeader("P3P", "CP=CAO PSA OUR"); // fix for ie6/ie7
        response.addCookie(result);
        return result;
    }

    public static Cookie getCookie(HttpServletRequest request, String key) {
        if (key == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (key.equals(c.getName())) {
                    return c;
                }
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie cookie = getCookie(request, key);
        return cookie == null ? null : cookie.getValue();
    }

    /**
     * 通过代理获取真实IP
     * @param request
     * @return
     */
    public static String getRealIpContainsProxy(HttpServletRequest request){
    	String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    }
    public static String getRealIp(HttpServletRequest request) {
        String result = request.getRemoteAddr();
        //if ("127.0.0.1".equals(result)) {
        String s = request.getHeader("X-Real-IP");
        if (s != null) {
            result = s;
        }
        //}
        return result;
    }
    
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
    
    public static int getContentLength(HttpServletRequest request) {
        int len = request.getContentLength();
        if (len < 0) len = 0;
        return len;
    }
    
    public static String getUsernameInApp(HttpServletRequest request) {
        String username = getNullIfEmpty(request, "username");
        if (StringUtils.isEmpty(username)) {
            username = getDeviceId(request);
        }
        return username;
    }
    
    public static String getDeviceId(HttpServletRequest request) {
        String deviceId = getNullIfEmpty(request, "deviceId");
        String platform = getNullIfEmpty(request, "platform");
        if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(platform)) {
            return null;
        }
        return platform + "_" + deviceId;
    }
    
    public static Integer getChannel(HttpServletRequest request) {
        Integer channel = getInt(request, "channel", null);
        if (channel == null) {
            try {
                channel = (Integer)request.getAttribute("channel");
            } catch(Exception e) {}
        }
        return channel;
    }

}
