package edu.haut.greenhouse.mapper.temhum;

import com.github.abel533.mapper.Mapper;

import edu.haut.greenhouse.pojo.temhum.TemAndHum;
/**
 * 温湿度数据
 * @author chen haijian
 * @date 2018-04-14
 *
 */
public interface TemAndHumMapper extends Mapper<TemAndHum> {

	/**
	 * 获取最近的一条数据
	 * @return
	 */
	TemAndHum selectLateData();
}
