package edu.haut.greenhouse.common.cache;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
/**
 * @Description
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class CacheableList implements ICacheable {
    
    private Class clazz;
    
    private List list;
    
    public CacheableList() {
    	super();
    }
    
    public CacheableList(List list, Class clazz) {
        this.clazz = clazz;
        this.list = list;
    }

    @Override
    public void writeFields(DataOutput out) throws IOException {
        if (clazz == Long.class) {
            out.writeByte(1);
        }  else {
            throw new IOException("unsupported type " + clazz);
        }
        out.writeInt(list.size());
        for (Object obj : list) {
            if (clazz == Long.class) {
                out.writeLong((Long)obj);
            } 
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        byte type = in.readByte();
        if (type == 1) {
            clazz = Long.class;
        } else {
            throw new IOException("unsupported type " + type);
        }
        int size = in.readInt();
        list = new ArrayList();
        for (int i = 0; i < size; i++) {
            if (type == 1) {
                list.add(in.readLong());
            } else {
                try {
                    ICacheable obj = (ICacheable)clazz.newInstance();
                    obj.readFields(in);
                    list.add(obj);
                } catch (Exception e) {
                    throw new IOException(e);
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(list);
    }
    
    public <T> List<T> get() {
        return list;
    }
    
    public void set(List<?> list) {
        this.list = list;
    }

}
