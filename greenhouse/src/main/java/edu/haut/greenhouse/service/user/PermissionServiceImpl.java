package edu.haut.greenhouse.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.haut.greenhouse.bean.PageResult;
import edu.haut.greenhouse.bean.user.PermissionDetail;
import edu.haut.greenhouse.mapper.user.PermissionMapper;
import edu.haut.greenhouse.mapper.user.ResourceObjMapper;
import edu.haut.greenhouse.pojo.user.Permission;
import edu.haut.greenhouse.pojo.user.ResourceObj;
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
	
	@Autowired
	private ResourceObjMapper resourceObjMapper;
	
	@Override
	public PageResult pageList(int page, int pageSize) {
		
		PageHelper.startPage(page, pageSize);
		Example example = new Example(Permission.class);
		example.setOrderByClause("objId asc, createTime desc");
		List<Permission> perList = permissionMapper.selectByExample(example);
		PageInfo<Permission> pageInfo = new PageInfo<>(perList);
		
		List<PermissionDetail> result = new ArrayList<>();
		for (Permission per : perList) {
			PermissionDetail pd = new PermissionDetail();
			pd.setPermission(per);
			result.add(pd);
		}
		
		//填充对应资源对象
		fillResourceObj(result);
		
		return new PageResult(pageInfo, result);
	}
	
	/**
	 * 填充对应的资源对象
	 * @param result
	 */
	public void fillResourceObj(List<PermissionDetail> result) {
		if(result == null || result.isEmpty()) {
			return;
		}
		
		List<Object> objIdList = new ArrayList<>();
		for (PermissionDetail pd : result) {
			objIdList.add(pd.getPermission().getObjId());
		} 
		
		Example example = new Example(ResourceObj.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", objIdList);
		List<ResourceObj> objList = resourceObjMapper.selectByExample(example);
		
		if (objList == null || objList.isEmpty()) {
			return;
		}
		
		for (PermissionDetail p : result) {
			for (ResourceObj r : objList) {
				if (p.getPermission().getObjId().intValue() == r.getId().intValue()) {
					p.setResourceObj(r);
					break;
				}
			}
		}
		
	}

}
