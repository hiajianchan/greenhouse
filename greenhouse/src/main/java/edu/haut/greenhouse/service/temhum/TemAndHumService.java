package edu.haut.greenhouse.service.temhum;

import java.util.List;

import edu.haut.greenhouse.bean.temhum.TemHumItem;
import edu.haut.greenhouse.pojo.temhum.TemAndHum;
import edu.haut.greenhouse.service.BaseService;
/**
 * 
 * @Description 温湿度数据的service接口
 * @author chen haijian
 * @date 2018年4月14日
 * @version 1.0
 */
public interface TemAndHumService extends BaseService<TemAndHum> {

	/**
	 * 获取今天的温湿度数据
	 * 从redis中获取
	 * @return
	 */
	List<TemAndHum> getTodayFromRedis();
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	List<TemAndHum> getDayDataFromMysql(String date);
	
	/**
	 * 获取最近的一条数据
	 * @return
	 */
	TemAndHum selectLateData();
	
	/**
	 * 插入数据
	 * @param temAndHum
	 */
	void insertData(TemAndHum temAndHum);
}
