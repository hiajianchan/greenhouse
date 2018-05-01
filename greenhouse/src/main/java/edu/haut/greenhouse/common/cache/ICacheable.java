package edu.haut.greenhouse.common.cache;

import edu.haut.greenhouse.common.datamodel.IWritable;

/**
 * @Description
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
public interface ICacheable extends IWritable {
    
    public boolean isEmpty();

}
