package edu.haut.greenhouse.service.temhum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;

import edu.haut.greenhouse.common.util.DateUtil;
import edu.haut.greenhouse.common.util.JsonUtils;
import edu.haut.greenhouse.common.util.StringUtil;
import edu.haut.greenhouse.common.util.redis.RedisManager;
import edu.haut.greenhouse.mapper.temhum.TemAndHumMapper;
import edu.haut.greenhouse.pojo.temhum.TemAndHum;
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
	public List<TemAndHum> getTodayFromRedis() {
		Set<String> keys = RedisManager.keys("tem&hum*");
		
		Set<byte[]> keySet = new HashSet<>();
		for (String str : keys) {
			keySet.add(str.getBytes());
		}
		
		List<byte[]> list = RedisManager.mget(keySet);
		
		List<TemAndHum> result = new ArrayList<>();
		for (byte[] val : list) {
			String json = new String(val);
			TemAndHum item = JsonUtils.fromJson(json, TemAndHum.class);
			result.add(item);
		}
		
		/**
		 * 根据时间排序
		 */
		sortByTime(result);
		
		return result;
	}
	
	@Override
	public List<TemAndHum> getDayDataFromMysql(String date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if (date == null || !StringUtil.isNotEmpty(date)) {
			return null;
		}
		
		Date today = DateUtil.fomatDate(date, "yyyy-MM-dd");
    	
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, 1);
		Date time = calendar.getTime();
		String nextDay = sdf.format(time);
		
		Example example = new Example(TemAndHum.class);
		Criteria criteria = example.createCriteria();
		criteria.andGreaterThan("createTime", today);
		criteria.andLessThan("createTime", nextDay);
		List<TemAndHum> result = mapper.selectByExample(example);
		
		sortByTime(result);
		
		return result;
	}
	
	/**
	 * 按照时间排序
	 * @param result
	 */
	public void sortByTime(List<TemAndHum> result) {
		Collections.sort(result, new Comparator<TemAndHum>() {

			@Override
			public int compare(TemAndHum o1, TemAndHum o2) {
				if (o1.getCreateTime() == null || o2.getCreateTime() == null) {
					if (o1.getCreateTime() == null) {
						return -1;
					} else {
						return 1;
					}
				}
				if (o1.getCreateTime().getTime() < o2.getCreateTime().getTime()) {
					return -1;
				} 
				if (o1.getCreateTime().getTime() > o2.getCreateTime().getTime()) {
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

	@Override
	public TemAndHum selectLateData() {
		return mapper.selectLateData();
	}

	
}
