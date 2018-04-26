package edu.haut.greenhouse.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.bean.user.RoleStatus;
import edu.haut.greenhouse.bean.user.UserDetail;
import edu.haut.greenhouse.bean.user.UserRoleStatus;
import edu.haut.greenhouse.bean.user.UserStatus;
import edu.haut.greenhouse.common.util.user.UserUtil;
import edu.haut.greenhouse.mapper.user.PermissionMapper;
import edu.haut.greenhouse.mapper.user.RoleMapper;
import edu.haut.greenhouse.mapper.user.RolePermissionMapper;
import edu.haut.greenhouse.mapper.user.UserInfoMapper;
import edu.haut.greenhouse.mapper.user.UserMapper;
import edu.haut.greenhouse.mapper.user.UserRoleMapper;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.RolePermission;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.pojo.user.UserInfo;
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
	private UserInfoMapper userInfoMapper;

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
	public boolean saveUser(User user, String[] roleList) {
		if (user == null) {
			return false;
		}
		
		//将用户密码加密
		user.setPasswd(UserUtil.pwd2Md5Hash(user.getPasswd()));
		
		user.setStatus(UserStatus.USEABLE.getStatus());
		user.setRegisterTime(new Date());
		int res1 = userMapper.insert(user);
		
		int i = 0;
		if (roleList == null || roleList.length == 0) {
			if (res1 == 1) {
				return true;
			} else {
				return false;
			}
		}
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
	
	@Override
	public boolean updateUser(User user, String[] roleList) {
		
		if (user == null) {
			return false;
		}
		
//		List<Integer> roleidList = new ArrayList<>();
//		for (String str : roleList) {
//			roleidList.add(Integer.valueOf(str));
//		}
		
		Integer uid = user.getId();
		
		//删除该用户下的用户关系数据
		Example example = new Example(UserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", uid);
		userRoleMapper.deleteByExample(example);
			
		/*
		 * 更新用户数据
		 */
		//先判断密码，与之前的密码值进行对比，如果一致则不更改密码
		User user2 = userMapper.selectByPrimaryKey(uid);
		if (user2.getPasswd().equals(user.getPasswd())) {
			//密码一致，不更改密码
			user.setPasswd(null);
		} else {
			user.setPasswd(UserUtil.pwd2Md5Hash(user.getPasswd()));
		}
		user.setUpdateTime(new Date());
		int res = userMapper.updateByPrimaryKeySelective(user);
		
		if (roleList == null || roleList.length == 0) {
			if (res == 1) {
				return true;
			} else {
				return false;
			}
		}
		//将新的角色写入
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
		
		if (res == 1 && i == roleList.length) {
			return true;
		}
		
		return false;
	}


	@Override
	public PageResult pageList(int page, int pageSize) {
		
		PageHelper.startPage(page, pageSize);
		Example example = new Example(User.class);
		example.setOrderByClause("registerTime desc");
		List<User> userList = userMapper.selectByExample(example);
		PageInfo<User> pageInfo = new PageInfo<>(userList);
		
		List<UserDetail> resList = new ArrayList<>();
		for (User user : userList) {
			UserDetail ui = new UserDetail();
			ui.setUser(user);
			resList.add(ui);
		}
		
		//填充用户信息
		fillInfo(resList);
		
		//填充juese
		fillRoles(resList);
		
		return new PageResult(pageInfo, resList);
	}
	
	/**
	 * 填充用户的info信息
	 * @param resList
	 */
	public void fillInfo(List<UserDetail> resList) {
		
		if (resList == null || resList.isEmpty()) {
			return;
		}
		
		List<Object> idList = new ArrayList<>();
		for (UserDetail ui : resList) {
			idList.add(ui.getUser().getId());
		}
		
		Example example = new Example(UserInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("userId", idList);
		
		List<UserInfo> infos = userInfoMapper.selectByExample(example);
		
		for (UserDetail uf : resList) {
			for (UserInfo info : infos) {
				if (uf.getUser().getId().intValue() == info.getUserId().intValue()) {
					uf.setInfo(info);
					break;
				}
			}
		}
	}
	
	/**
	 * 填充用户的角色
	 * @param resList
	 */
	public void fillRoles(List<UserDetail> resList) {
		if(resList == null || resList.isEmpty()) {
			return;
		}
		
		List<Object> userList = new ArrayList<>();
		for (UserDetail ud : resList) {
			userList.add(ud.getUser().getId());
		}
		
		//根据用户ID集合查询出所有的用户角色关系
		Example example = new Example(UserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("userId", userList);
		criteria.andEqualTo("status", UserRoleStatus.USEABLE.getStatus());
		List<UserRole> userRoles = userRoleMapper.selectByExample(example);
		
		if(userRoles == null || userRoles.isEmpty()) {
			return;
		}
		
		List<Object> roleList = new ArrayList<>();
		for (UserRole ur : userRoles) {
			roleList.add(ur.getRoleId());
		}
		
		//根据角色ID查询出角色集合
		Example example2 = new Example(Role.class);
		Criteria criteria2 = example2.createCriteria();
		criteria2.andIn("id", roleList);
		criteria2.andEqualTo("status", RoleStatus.USEABLE.getStatus());
		List<Role> roles = roleMapper.selectByExample(example2);
		
		Map<Integer, List<Role>> map = new HashMap<>();
		
		for (UserRole ur : userRoles) {
			for (Role role : roles) {
				if (ur.getRoleId().intValue() == role.getId().intValue()) {  //命中role
					if (map.containsKey(ur.getUserId())) {
						List<Role> list = map.get(ur.getUserId());
						list.add(role);
						map.put(ur.getUserId(), list);
						break;
					} else {
						List<Role> list = new ArrayList<>();
						list.add(role);
						map.put(ur.getUserId(), list);
						break;
					}
				}
			}
		}
		
		for (UserDetail ud : resList) {
			if (map.containsKey(ud.getUser().getId())) {
				ud.setRoles(map.get(ud.getUser().getId()));
			}
		}
		
	}

}
