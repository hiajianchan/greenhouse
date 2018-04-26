package edu.haut.greenhouse.service.user;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.service.BaseService;
/**
 * 
 * @Description 角色的service接口
 * @author chen haijian
 * @date 2018年4月14日
 * @version 1.0
 */
public interface RoleService extends BaseService<Role> {

	/**
	 * 新增角色
	 * @param role
	 * @param perList
	 * @return
	 */
	boolean saveRole(Role role, String[] perList);
	
	/**
	 * 修改角色
	 * @param role
	 * @param perList
	 * @return
	 */
	boolean updateRole(Role role, String[] perList);
	
	/**
	 * 角色的分页查询
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageResult pageList(int page, int pageSize);
}
