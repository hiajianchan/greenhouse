package edu.haut.greenhouse.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.service.user.UserService;
/**
 * 
 * @Description 自定义realm
 * @author chen haijian
 * @date 2018年4月19日
 * @version 1.0
 */
public class MyRealm extends AuthorizingRealm {
	
	public final static String SALT = "fireinthehole";
	
	@Autowired
	private UserService userService;

	/**
	 * 提供用户信息，返回权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String userEmail = principals.toString();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		User record = new User();
		record.setEmail(userEmail);
		User user = userService.queryOne(record);
		if (user == null) {
			return null;
		}
		
		//用户的角色
		Set<Role> roles = userService.getRoles(user.getId());
		
		if (roles != null) {
			Set<String> roleNames = new HashSet<>();
			for (Role role : roles) {
				roleNames.add(role.getRolename());
			}
			info.setRoles(roleNames);
		}
		
		//用户的权限
		Set<Permission> permissions = userService.getPermissions(user.getId());
		
		if (permissions != null) {
			Set<String> perNames = new HashSet<>();
			for (Permission per : permissions) {
				perNames.add(per.getPerName());
			}
			info.setStringPermissions(perNames);
		}
		
		return info;
	}

	/**
	 * 首先执行这个登陆验证，根据账号查询用户信息，放入SimpleAuthenticationInfo进行账号密码匹配，匹配不上抛出异常
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取登录用户的用户名
		String userEmail = token.getPrincipal().toString();
		
		/**
		 * 邮箱登录，使用邮箱查询用户
		 */
		User record = new User();
		record.setEmail(userEmail);
		User user = userService.queryOne(record);
		
		if (user != null) {
			AuthenticationInfo info = new SimpleAuthenticationInfo(user.getEmail(), user.getPasswd(), ByteSource.Util.bytes(SALT), getName());
			return info;
		}
		
		return null;
	}

}
