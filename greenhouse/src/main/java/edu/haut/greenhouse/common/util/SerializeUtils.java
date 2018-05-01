package edu.haut.greenhouse.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import edu.haut.greenhouse.common.datamodel.IWritable;
/**
 * @Description  序列化工具
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
public class SerializeUtils {
    
    public static byte[] serialize(IWritable v) throws IOException {
        if (v == null) return null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(out);
        v.writeFields(dataOutput);
        dataOutput.close();
        return out.toByteArray();
    }
    
    public static <T extends IWritable> T deseialize(byte[] bytes, Class<T> clazz) throws IOException {
        if (bytes == null) return null;
        try {
            T v = clazz.newInstance();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            DataInputStream dataInput = new DataInputStream(in);
            v.readFields(dataInput);
            dataInput.close();
            return v;
        } catch (InstantiationException e) {
            throw new IOException("invalid default constructor for class " + clazz);
        } catch (IllegalAccessException e) {
            throw new IOException("Illegal access default constructor for class " + clazz);
        }
    }
    
    public static void writeInt(DataOutput out, Integer value) throws IOException {
        if (value == null) {
            out.writeInt(-1);
        } else {
            out.writeInt(value);
        }
    }
    
    public static Integer readInt(DataInput in) throws IOException {
        Integer v = in.readInt();
        if (v == -1) return null;
        return v;
    }
    
    public static void writeLong(DataOutput out, Long value) throws IOException {
        if (value == null) {
            out.writeLong(-1);
        } else {
            out.writeLong(value);
        }
    }
    
    public static Long readLong(DataInput in) throws IOException {
        Long v = in.readLong();
        if (v == -1) return null;
        return v;
    }
    
    public static void writeDouble(DataOutput out, Double value) throws IOException {
        if (value == null) {
            out.writeDouble(-1);
        } else {
            out.writeDouble(value);
        }
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public static Double readDouble(DataInput in) throws IOException {
        Double v = in.readDouble();
        if (v.equals(-1)) return null;
        return v;
    }
    
    public static void writeFloat(DataOutput out, Float value) throws IOException {
        if (value == null) {
            out.writeFloat(-1);
        } else {
            out.writeFloat(value);
        }
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public static Float readFloat(DataInput in) throws IOException {
        Float v = in.readFloat();
        if (v.equals(-1)) return null;
        return v;
    }
    
    public static void writeDate(DataOutput out, Date date) throws IOException {
        if (date == null) {
            out.writeLong(-1);
        } else {
            out.writeLong(date.getTime());
        }
    }
    
    public static Date readDate(DataInput in) throws IOException {
        Long time = in.readLong();
        if (time == -1) return null;
        return new Date(time);
    }
    
    public static void writeString(DataOutput out, String value) throws IOException {
        if (value == null) {
            out.writeUTF("@null");
        } else {
            out.writeUTF(value);
        }
    }
    
    public static String readString(DataInput in) throws IOException {
        String str = in.readUTF();
        if ("@null".equals(str)) return null;
        return str;
    }
}
