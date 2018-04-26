package edu.haut.greenhouse.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.mapper.user.PermissionMapper;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.service.BaseServiceImpl;
/**
 * 
 * @Description 
 * @author chen haijian
 * @date 2018年4月14日
 * @version 1.0
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	@Override
	public PageResult pageList(int page, int pageSize) {
		
		PageHelper.startPage(page, pageSize);
		Example example = new Example(Permission.class);
		example.setOrderByClause("createTime desc");
		List<Permission> perList = permissionMapper.selectByExample(example);
		PageInfo<Permission> pageInfo = new PageInfo<>(perList);
		
		return new PageResult(pageInfo, perList);
	}

}
