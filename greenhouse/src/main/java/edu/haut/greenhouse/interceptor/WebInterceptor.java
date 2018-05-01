package edu.haut.greenhouse.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.haut.greenhouse.common.util.AccessLog;
import edu.haut.greenhouse.common.util.ServiceDegradeManager;
import edu.haut.greenhouse.common.util.StringUtil;
import edu.haut.greenhouse.common.util.WebUtils;

/**
 * 
 * @Description 拦截器 ， 处理访问异常的IP
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
public class WebInterceptor extends HandlerInterceptorAdapter {
	
	//服务器IP
	private static final String IP = "39.106.184.51";
	
	private static final String[] BLACK_IP_ARR = {};
	
	private static final String[] WHITE_IP_ARR = {};
	
	private static final Set<String> BLACK_IPS = new HashSet<>();
	private static final Set<String> WHITE_IPS = new HashSet<>();
	
	
	static {
		for (String ip : BLACK_IP_ARR) {
			BLACK_IPS.add(ip);
		}
		for (String ip : WHITE_IP_ARR) {
			WHITE_IPS.add(ip);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AccessLog.startAccess(request);
		
		String ip = WebUtils.getRealIp(request);
		if (BLACK_IPS.contains(ip)) {
			//黑名单中包含此IP
			return false;
		}
		
		if (!request.getRequestURI().contains("static") &&
        		!request.getRequestURI().contains("images") &&
            !request.getRequestURI().contains("src") &&
            !request.getRequestURI().contains("css") &&
            !request.getRequestURI().contains("js") &&
            !request.getRequestURI().contains("error") &&
            !WHITE_IPS.contains(ip)) {
            if (IpSessionManager.isRequestLimited(ip)) {
                addBlackIp(ip);
                return false;
            }
        }
		
		ServiceDegradeManager.startRequest(request);
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		ServiceDegradeManager.finishRequest(request);
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (!request.getRequestURI().contains("static") &&
        		!request.getRequestURI().contains("images") &&
            !request.getRequestURI().contains("src") &&
            !request.getRequestURI().contains("css") &&
            !request.getRequestURI().contains("js") &&
            !request.getRequestURI().contains("error") ) {
			AccessLog.log(request, response);
		}
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * 添加IP至黑名单
	 * @param ip
	 */
	public static void addBlackIp(String ip) {
		if (StringUtil.isNotEmpty(ip) && !WHITE_IPS.contains(ip)) {
			BLACK_IPS.add(ip);
		}
	}
}
