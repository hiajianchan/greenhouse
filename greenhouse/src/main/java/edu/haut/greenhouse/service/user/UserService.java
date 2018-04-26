package edu.haut.greenhouse.service.user;

import java.util.Set;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.service.BaseService;
/**
 * 
 * @Description 用户的service 接口
 * @author chen haijian
 * @date 2018年4月14日
 * @version 1.0
 */
public interface UserService extends BaseService<User> {

	/**
	 * 根据用户ID查询所有的角色
	 * @param userId
	 * @return
	 */
	Set<Role> getRoles(Integer userId);
	
	/**
	 * 根据用户ID，查询出所有的权限
	 * @param userId
	 * @return
	 */
	Set<Permission> getPermissions(Integer userId);
	
	/**
	 * 新增用户
	 * @param user
	 * @param roleList
	 * @return
	 */
	boolean saveUser(User user, String[] roleList);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param roleList
	 * @return
	 */
	boolean updateUser(User user, String[] roleList);
	
	/**
	 * 用户详情的分页查询
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageResult pageList(int page, int pageSize);
	
}
