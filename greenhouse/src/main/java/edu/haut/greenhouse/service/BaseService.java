package edu.haut.greenhouse.service;

import java.util.List;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
/**
 * 基础service接口
 * @author chen haijian
 * @date 2018-04-14
 * @param <T>
 */
public interface BaseService<T> {

	/**
     * 根据id查询数据
     * @param id
     * @return 返回实体对象
     */
    T queryById(Integer id);

    /**
     * 查询所有数据
     * @return 返回实体Bean类型的list集合
     */
    List<T> queryAll();

    /**
     * 根据条件查询一条语句，如果有多条数据则会抛出异常
     * @param record 查询条件，一个Bean对象
     * @return 返回一个实体对象
     */
    T queryOne(T record);

    /**
     * 根据条件查询数据
     * @param record 查询条件
     * @return 返回一个实体Bean类型的list集合
     */
    List<T> queryListByWhere(T record);
    
    /**
     * 根据条件查询
     * @param example
     * @return
     */
    List<T> queryByExample(Example example);

    /**
     * 根据条件分页查询
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param record 查询条件
     * @return 返回实体Bean类型的PageInfo对象
     */
    PageInfo<T> queryPageListByWhere(Integer pageNum, Integer pageSize, T record);

    /**
     * 插入数据 ，返回插入条数
     * @param record 需要保存的实体对象
     * @return  返回插入的条数
     */
    Integer save(T record);

    /**
     * 插入数据，只插入不为null的字段，返回插入条数
     * @param record 需要保存的实体对象
     * @return 返回插入的条数
     */
    Integer saveSelective(T record);

    /**
     * 根据主键更新数据，返回更新条数
     * @param record 需要更新的实体对象
     * @return 返回更新的条数
     */
    Integer update(T record);
    
    /**
     * 根据条件更新数据，返回更新条数
     * @param record 更新的数据
     * @param example 条件
     * @return
     */
    Integer updateByWhere(T record, Example example);

    /**
     * 更新数据，字段不为null的
     * @param record 需要更新的实体对象
     * @return 返回更新的条数
     */
    Integer updateSelective(T record);
    
    /**
     * 更新数据，更新传入对象中值不为空的属性对应的表字段
     * @param record  实体对象
     * @param example 返回更新的条数
     * @return
     */
    Integer updateSelectiveByWhere(T record, Example example);

    /**
     * 根据id删除数据
     * @param id id
     * @return 返回删除的条数
     */
    Integer deleteById(Integer id);

    /**
     * 批量删除
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    Integer deleteByIds(Class<T> clazz, String property, List<Object> values);

    /**
     * 根据条件删除数据
     * @param record
     * @return
     */
    Integer deleteByWhere(T record);
}
