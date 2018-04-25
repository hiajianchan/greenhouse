package edu.haut.greenhouse.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {
	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = true;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}

	/**
	 * @return 本机IP
	 * @throws SocketException
	 */
	public static String getRealIp() throws SocketException {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP

		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}

		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}
	public static String getRealIp(HttpServletRequest request) throws SocketException {
		String realIp = null;
		realIp = getRemoteHost(request);
		return realIp;
	}
	public static String getRemoteHost(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	
	/**
	 * 地址字符串拼接
	 * @param str 可变参数
	 * @return
	 */
	public static String doStrJoin(String... str) {
		StringBuffer sb = new StringBuffer();
		
		for (String strValue : str) {
			strValue = trimAllSpace(strValue);
			if (isNotEmpty(strValue)) {
				sb.append(strValue);
			}
		}
		
		return sb.toString() ;
	}
	
	/**
	 * 将每个字符串trim后再进行拼接
	 * @param str
	 * @return
	 */
	public static String doSimpleJoin(String... str) {
		StringBuffer sb = new StringBuffer();
		
		for (String strValue : str) {
			strValue = trimSpace(strValue);
			if (isNotEmpty(strValue)) {
				sb.append(strValue);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 做字符串拼接，并用空格隔开
	 * @return
	 */
	public static String doSimpleJoinSplitBySpace(String... str) {
		StringBuffer sb = new StringBuffer();
		
		for (String strValue : str) {
			strValue = trimSpace(strValue);
			if (isNotEmpty(strValue)) {
				sb.append(strValue).append(" ");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		
		return sb.toString();
	}
	
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		
		boolean flag = false;
		
		if (str != null && !str.equals("") && str.trim().length() > 0) {
			flag =  true;
		} 
		
		return flag;
	}
	
	/**
	 * 去除字符串中的空白字符
	 * @param str
	 * @return
	 */
	public static String trimAllSpace(String str) {
		
		return str==null ? null : str.replaceAll("\\s*", "");
	}
	
	/**
	 * 去除字符串的首位空格
	 * @param str
	 * @return
	 */
	public static String trimSpace(String str) {
		return str==null ? null : str.trim();
	}
}
