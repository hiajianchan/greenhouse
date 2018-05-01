package edu.haut.greenhouse.common.datamodel;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
/**
 * @Description
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
public interface IWritable {
    
    public void writeFields(DataOutput out) throws IOException;
    
    public void readFields(DataInput in) throws IOException;

}
