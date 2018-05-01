package edu.haut.greenhouse.common.cache;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author yanghao
 */
@SuppressWarnings("unchecked")
public class PrimitiveCacheable implements ICacheable {
    
    public static final PrimitiveCacheable EMPTY = new PrimitiveCacheable();
    
    private Object obj = null;
    
    public PrimitiveCacheable(Object obj) {
        this.obj = obj;
    }
    
    public PrimitiveCacheable() {
    	super();
	}

    @Override
    public void writeFields(DataOutput out) throws IOException {
        if (obj == null) {
            out.writeByte(0);
        } else if (obj instanceof Integer) {
            out.writeByte(1);
            out.writeInt((Integer)obj);
        } else if (obj instanceof Long) {
            out.writeByte(2);
            out.writeLong((Long)obj);
        } else if (obj instanceof Float) {
            out.writeByte(3);
            out.writeFloat((Float)obj);
        } else if (obj instanceof Double) {
            out.writeByte(4);
            out.writeDouble((Double)obj);
        } else if (obj instanceof String) {
            out.writeByte(5);
            out.writeUTF((String)obj);
        } else {
            throw new RuntimeException("not primitive value");
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        byte type = in.readByte();
        if (type == 0) {
            obj = null;
        } else if (type == 1) {
            obj = in.readInt();
        } else if (type == 2) {
            obj = in.readLong();
        } else if (type == 3) {
            obj = in.readFloat();
        } else if (type == 4) {
            obj = in.readDouble();
        } else if (type == 5) {
            obj = in.readUTF();
        } else {
            throw new RuntimeException("not primitive value");
        }
    }

    @Override
    public boolean isEmpty() {
        return obj == null;
    }
    
    public <T> T get() {
        return (T)obj;
    }

}
