package edu.haut.greenhouse.service.temhum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.haut.greenhouse.bean.temhum.TemHumItem;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.redis.RedisManager;
import edu.haut.greenhouse.mapper.temhum.TemAndHumMapper;
import edu.haut.greenhouse.pojo.temhum.TemAndHum;
import edu.haut.greenhouse.service.BaseService;
import edu.haut.greenhouse.service.BaseServiceImpl;

/**
 * 
 * @Description 温湿度数据的service实现类
 * @author chen haijian
 * @date 2018年4月14日
 * @version 1.0
 */
@Service
public class TemAndHumServiceImpl extends BaseServiceImpl<TemAndHum> implements TemAndHumService {

	@Autowired
	private TemAndHumMapper mapper;
	
	@Override
	public List<TemHumItem> getToday() {
		Set<String> keys = RedisManager.keys("tem&hum*");
		
		Set<byte[]> keySet = new HashSet<>();
		for (String str : keys) {
			keySet.add(str.getBytes());
		}
		
		List<byte[]> list = RedisManager.mget(keySet);
		
		List<TemHumItem> result = new ArrayList<>();
		for (byte[] val : list) {
			String json = new String(val);
			TemHumItem item = JsonUtils.fromJson(json, TemHumItem.class);
			result.add(item);
		}
		
		/**
		 * 根据时间排序
		 */
		sortByTime(result);
		
		return result;
	}
	
	/**
	 * 按照时间排序
	 * @param result
	 */
	public void sortByTime(List<TemHumItem> result) {
		Collections.sort(result, new Comparator<TemHumItem>() {

			@Override
			public int compare(TemHumItem o1, TemHumItem o2) {
				if (o1.getTime() == null || o2.getTime() == null) {
					if (o1.getTime() == null) {
						return -1;
					} else {
						return 1;
					}
				}
				if (o1.getTime().getTime() < o2.getTime().getTime()) {
					return -1;
				} 
				if (o1.getTime().getTime() > o2.getTime().getTime()) {
					return 1;
				}
				return 0;
			}
		});
	}

	@Override
	public void insertData(TemAndHum temAndHum) {
		temAndHum.setCreateTime(new Date());
		mapper.insert(temAndHum);
	}
	
}
