package edu.haut.greenhouse.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.bean.user.PermissionStatus;
import edu.haut.greenhouse.bean.user.RoleDetail;
import edu.haut.greenhouse.bean.user.RolePermissionStatus;
import edu.haut.greenhouse.bean.user.RoleStatus;
import edu.haut.greenhouse.mapper.user.PermissionMapper;
import edu.haut.greenhouse.mapper.user.RoleMapper;
import edu.haut.greenhouse.mapper.user.RolePermissionMapper;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.Role;
import edu.haut.greenhouse.pojo.user.RolePermission;
import edu.haut.greenhouse.service.BaseServiceImpl;
/**
 * 
 * @Description 角色的service实现类
 * @author chen haijian
 * @date 2018年4月14日
 * @version 1.0
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	
	@Override
	public boolean saveRole(Role role, String[] perList) {
		if (role == null) {
			return false;
		}
		
		role.setStatus(RoleStatus.USEABLE.getStatus());
		role.setCreateTime(new Date());
		int res = roleMapper.insert(role);
		
		if (perList == null || perList.length == 0) {
			if (res == 1) {
				return true;
			} else {
				return false;
			}
		}
		
		int i = 0;
		for (String str : perList) {
			RolePermission rp = new RolePermission();
			rp.setCreateTime(new Date());
			rp.setPerId(Integer.valueOf(str));
			rp.setRoleId(role.getId());
			rp.setStatus(RolePermissionStatus.USEABLE.getStatus());
			rolePermissionMapper.insert(rp);
			i++;
		}
		
		if (res == 1 && i == perList.length) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean updateRole(Role role, String[] perList) {
		if (role == null) {
			return false;
		}
		
		//删除该角色下的角色关系数据
		Example example = new Example(RolePermission.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("roleId", role.getId());
		rolePermissionMapper.deleteByExample(example);
		
		//更新角色
		role.setUpdateTime(new Date());
		int res = roleMapper.updateByPrimaryKeySelective(role);
		
		if (perList == null || perList.length == 0) {
			if (res == 1) {
				return true;
			} else {
				return false;
			}
		}
		
		int i = 0;
		for (String str : perList) {
			RolePermission rp = new RolePermission();
			rp.setCreateTime(new Date());
			rp.setPerId(Integer.valueOf(str));
			rp.setRoleId(role.getId());
			rp.setStatus(RolePermissionStatus.USEABLE.getStatus());
			rolePermissionMapper.insert(rp);
			i++;
		}
		
		if (res == 1 && i == perList.length) {
			return true;
		}
		
		return false;
	}

	@Override
	public PageResult pageList(int page, int pageSize) {
		
		PageHelper.startPage(page, pageSize);
		Example example = new Example(Role.class);
		example.setOrderByClause("createTime desc");
		List<Role> roleList = roleMapper.selectByExample(example);
		PageInfo<Role> pageInfo = new PageInfo<>(roleList);
		
		List<RoleDetail> result = new ArrayList<>();
		for (Role r : roleList) {
			RoleDetail rd = new RoleDetail();
			rd.setRole(r);
			result.add(rd);
		}
		
		//填充权限
		fillPermissions(result);
		
		return new PageResult(pageInfo, result);
	}


	/**
	 * 为角色详情填充权限
	 * @param result
	 */
	public void fillPermissions(List<RoleDetail> result) {
		if (result == null) {
			return;
		}
		
		//获取所有的角色ID
		List<Object> ridList = new ArrayList<>();
		for (RoleDetail rd : result) {
			ridList.add(rd.getRole().getId());
		}
		
		//获取所有的权限ID
		Example example = new Example(RolePermission.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("roleId", ridList);
		criteria.andEqualTo("status", RolePermissionStatus.USEABLE.getStatus());
		List<RolePermission> rpList = rolePermissionMapper.selectByExample(example);
		
		if (rpList == null || rpList.isEmpty()) {
			return;
		}
		
		List<Object> pidList = new ArrayList<>();
		for (RolePermission rp : rpList) {
			pidList.add(rp.getPerId());
		}
		
		//查询出相关所有的权限
		Example example2 = new Example(Permission.class);
		Criteria criteria2 = example2.createCriteria();
		criteria2.andIn("id", pidList);
		criteria2.andEqualTo("status", PermissionStatus.USEABLE.getStatus());
		List<Permission> perList = permissionMapper.selectByExample(example2);
		
		Map<Integer, List<Permission>> map = new HashMap<>();
		for (RolePermission r : rpList) {
			for(Permission p : perList) {
				if (r.getPerId().intValue() == p.getId().intValue()) {
					if (map.containsKey(r.getRoleId())) {
						List<Permission> list = map.get(r.getRoleId());
						list.add(p);
						map.put(r.getRoleId(), list);
						break;
					} else {
						List<Permission> list = new ArrayList<>();
						list.add(p);
						map.put(r.getRoleId(), list);
						break;
					}
				}
			}
		}
		
		for (RoleDetail r : result) {
			if (map.containsKey(r.getRole().getId())) {
				r.setPerList(map.get(r.getRole().getId()));
			}
		}
	}
}
