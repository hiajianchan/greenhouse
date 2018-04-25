package edu.haut.greenhouse.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;

import edu.haut.greenhouse.bean.user.UserRoleStatus;
import edu.haut.greenhouse.bean.user.UserStatus;
import edu.haut.greenhouse.common.util.user.UserUtil;
import edu.haut.greenhouse.mapper.user.PermissionMapper;
import edu.haut.greenhouse.mapper.user.RoleMapper;
import edu.haut.greenhouse.mapper.user.RolePermissionMapper;
import edu.haut.greenhouse.mapper.user.UserMapper;
import edu.haut.greenhouse.mapper.user.UserRoleMapper;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.RolePermission;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.pojo.user.UserRole;
import edu.haut.greenhouse.service.BaseServiceImpl;
/**
 * 
 * @Description 用户数据的service实现类
 * @author chen haijian
 * @date 2018年4月14日
 * @version 1.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Override
	public Set<Role> getRoles(Integer userId) {
		
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		List<UserRole> userRoles = userRoleMapper.select(userRole);
		
		List<Object> roleIds = null;
		if (userRoles != null && !userRoles.isEmpty()) {
			roleIds = new ArrayList<>();
			for (UserRole ur : userRoles) {
				roleIds.add(ur.getRoleId());
			}
		} else {
			return null;
		}
		
		if (roleIds != null) {
			Example example = new Example(Role.class);
			Criteria criteria = example.createCriteria();
			criteria.andIn("id", roleIds);
			List<Role> roles = roleMapper.selectByExample(example);
			if (roles != null) {
				Set<Role> roleSet = new HashSet<>();
				for (Role role : roles) {
					roleSet.add(role);
				}
				return roleSet;
			}
		}
		
		return null;
	}

	@Override
	public Set<Permission> getPermissions(Integer userId) {
		
		Set<Role> roles = getRoles(userId);
		if (roles != null) {
			List<Object> roleIds = new ArrayList<>();
			for (Role role : roles) {
				roleIds.add(role.getId());
			} 
			if (roleIds.isEmpty())
				return null;
			
			//查询出角色关系数据
			Example example = new Example(RolePermission.class);
			Criteria criteria = example.createCriteria();
			criteria.andIn("roleId", roleIds);
			List<RolePermission> rolePers = rolePermissionMapper.selectByExample(example);
			if (rolePers == null)
				return null;
			
			List<Object> perIds= new ArrayList<>();
			for (RolePermission rp : rolePers) {
				perIds.add(rp.getPerId());
			}
			if (perIds.isEmpty())
				return null;
			
			Example example2 = new Example(Permission.class);
			Criteria criteria2 = example2.createCriteria();
			criteria2.andIn("id", perIds);
			List<Permission> permissions = permissionMapper.selectByExample(example2);
			if (permissions != null && !permissions.isEmpty()) {
				Set<Permission> pers = new HashSet<>();
				for (Permission p : permissions) {
					pers.add(p);
				}
				return pers;
			}
			
		}
		
		return null;
	}

	@Override
	public boolean save(User user, String[] roleList) {
		
		//将用户密码加密
		user.setPasswd(UserUtil.pwd2Md5Hash(user.getPasswd()));
		
		user.setStatus(UserStatus.USEABLE.getStatus());
		user.setRegisterTime(new Date());
		int res1 = userMapper.insert(user);
		
		int i = 0;
		for (String str : roleList) {
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId(Integer.valueOf(str));
			userRole.setStatus(UserRoleStatus.USEABLE.getStatus());
			userRole.setCreateTime(new Date());
			
			userRoleMapper.insert(userRole);
			i++;
		}
		
		if (res1 == 1 && i == roleList.length) {
			return true;
		}
		return false;

	}

}
