package edu.haut.greenhouse.service.user;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.service.BaseService;
/**
 * 
 * @Description 权限的service 接口
 * @author chen haijian
 * @date 2018年4月14日
 * @version 1.0
 */
public interface PermissionService extends BaseService<Permission> {

	/**
	 * 分页查询
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageResult pageList(int page, int pageSize);
}
