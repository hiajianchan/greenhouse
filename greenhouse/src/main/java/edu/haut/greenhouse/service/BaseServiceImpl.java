package edu.haut.greenhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 基础的service实现类
 * @author chj
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	@Autowired
	private Mapper<T> mapper;
	
	/**
     * 根据ID查询对象
     * @param id 传入ID值
     * @return  返回查询到的对象
     */
    public T queryById(Integer id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有的数据
     * @return 所有数据的集合
     */
    public List<T> queryAll() {
        return this.mapper.select(null);
    }

    /**
     * 根据查询条件插叙一条数据，如果有多条则抛出异常
     * @param record  查询条件
     * @return  返回查询到的对象
     */
    public T queryOne(T record) {
        return this.mapper.selectOne(record);
    }

    /**
     * 根据查询条件查询对象集合
     * @param record 查询条件
     * @return 返回list集合
     */
    public List<T> queryListByWhere(T record) {
        return this.mapper.select(record);
    }
    
    
    public List<T> queryByExample(Example example) {
    	return this.mapper.selectByExample(example);
    }

    /**
     * 根据条件进行分页查询
     *
     * @param pageNum  起始页数
     * @param pageSize 页面大小，即有多少条数据
     * @param record   查询条件
     * @return         返回PageInfo对象
     */
    public PageInfo<T> queryPageListByWhere(Integer pageNum, Integer pageSize, T record) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = this.queryListByWhere(record);
        return new PageInfo<T>(list);
    }

    /**
     * 插入数据，返回插入条数
     * @param record  插入对象值
     * @return   返回插入条数
     */
    public Integer save(T record) {
        return this.mapper.insert(record);
    }

    /**
     * 插入数据，只插入不为null的字段，返回插入条数
     * @param record
     * @return
     */
    public Integer saveSelective(T record) {
        return this.mapper.insertSelective(record);
    }

    /**
     * 根据主键 更新数据
     * @param record
     * @return
     */
    public Integer update(T record) {
        return this.mapper.updateByPrimaryKey(record);
    }
    
   /**
    * 根据条件更新数据
    */
    public Integer updateByWhere(T record, Example example) {
    	return this.mapper.updateByExample(record, example);
    }

    /**
     * 更新数据，更新值不为null的字段
     * @param record
     * @return
     */
    public Integer updateSelective(T record) {
        return this.mapper.updateByPrimaryKeySelective(record);
    }
    
    /**
     * 根据条件更新字段，传入字段值不为空则更新
     */
    public Integer updateSelectiveByWhere(T record, Example example) {
    	return this.mapper.updateByExampleSelective(record, example);
    }

    /**
     * 根据主键删除数据
     * @param id
     * @return
     */
    public Integer deleteById(Integer id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     *批量删除数据
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> values) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, values);
        return this.mapper.deleteByExample(example);
    }

    /**
     * 根据条件删除数据
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record) {
        return this.mapper.delete(record);
    }
}
