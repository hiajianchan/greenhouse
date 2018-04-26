package edu.haut.greenhouse.common.util.user;
/**
 * 
 * @Description 系统用户的相关工具类
 * @author chen haijian
 * @date 2018年4月21日
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Md5Hash;

import edu.haut.greenhouse.common.util.JsonStatus;
import edu.haut.greenhouse.common.util.ValidateUtils;
import edu.haut.greenhouse.common.util.WebUtils;
import edu.haut.greenhouse.pojo.user.User;

public class UserUtil {
	
	public static final String SALT = "fireinthehole";
	public static final int HASHITERATIONS = 512;

	/**
	 * 将页面传来的数据填充为user对象
	 * @param request
	 * @return
	 */
	public static User fillUser(HttpServletRequest request) {
		
		User user = new User();
		
		Integer id = WebUtils.getInt(request, "id", null);
		user.setId(id);
		
		//姓名
		String name = WebUtils.getNullIfEmpty(request, "name");
		user.setName(name);
		//性别
		Integer gender = WebUtils.getInt(request, "gender", null);
		user.setGender(gender);
		//密码
		String passwd = WebUtils.getNullIfEmpty(request, "passwd");
		user.setPasswd(passwd);
		//邮箱
		String email = WebUtils.getNullIfEmpty(request, "email");
		user.setEmail(email);
		//手机号
		String mobile = WebUtils.getNullIfEmpty(request, "mobile");
		user.setMobile(mobile);
		
		return user;
	}
	
	/**
	 * 校验传过来的用户信息
	 * @param user
	 * @return
	 */
	public static Map<Object, Object> verifyUser(User user) {
		Map<Object, Object> map = new HashMap<>();
		
		if (user == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "用户为空");
			return map;
		}
		
		//校验姓名
		if (user.getName() == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "用户名不能为空");
			return map;
		}
		if (user.getName().length() > 25) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "用户名不能超过25个字符");
			return map;
		}
		//性别
		if (user.getGender() == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "性别不能为空");
			return map;
		}
		
		//校验邮箱
		if (user.getEmail() == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "邮箱不能为空，登录要用的");
			return map;
		} else {
			if (!ValidateUtils.validateEmail(user.getEmail())) {
				map.put(JsonStatus.STATUS, JsonStatus.ERROR);
				map.put(JsonStatus.MSG, "邮箱格式错误");
				return map;
			}
		}
		
		//校验密码
		if (user.getPasswd() == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "密码不能为空");
			return map;
		}
		
		//校验手机号
		if (user.getMobile() == null) {
			map.put(JsonStatus.STATUS, JsonStatus.ERROR);
			map.put(JsonStatus.MSG, "手机号不能为空");
			return map;
		} else {
			if (!ValidateUtils.validateMobile(user.getMobile())) {
				//手机号格式错误
				map.put(JsonStatus.STATUS, JsonStatus.ERROR);
				map.put(JsonStatus.MSG, "手机号格式错误");
				return map;
			}
		}
		map.put(JsonStatus.STATUS, JsonStatus.SUCCESS);
		
		return map;
	}
	
    /**
     * 将用户的密码加密
     * @param passwd
     * @return
     */
	public static String pwd2Md5Hash(String passwd) {
		
		Md5Hash hash = new Md5Hash(passwd, SALT, HASHITERATIONS);
		return hash.toString();
	}
}
