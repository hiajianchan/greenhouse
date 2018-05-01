package edu.haut.greenhouse.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
/**
 * @Description
 * @author chen haijian
 * @date 2018年5月1日
 * @version 1.0
 */
public class CSVUtils {
    
    private static final String DOUBLE_FORMAT = "%.4f";
    
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    private static final String SEPERATOR = "!@#$";
    
    public static String toCSV(Object... values) {
        StringBuilder sb = new StringBuilder();
        for (Object v : values) {
            append(sb, v);
        }
        return sb.toString();
    }
    
    public static void append(StringBuilder sb, Object value) {
        if (sb.length() > 0) sb.append(',');
        String v = null;
        if (value != null) {
            if (value instanceof Double || value instanceof Float) {
                v = String.format(DOUBLE_FORMAT, value);
            } else if (value instanceof Date) {
                v = DATE_FORMAT.format((Date)value);
            } else {
                if (!StringUtils.isEmpty(value.toString())) v = value.toString();
            }
        }
        if (v != null) {
            sb.append(StringUtils.replace(v, ",", SEPERATOR));
        }
    }
    
    public static String[] fromCSV(String text) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isNotEmpty(text)) {
            text += ',';
            int start = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ',') {
                    String str = text.substring(start, i);
                    str = StringUtils.replace(str, SEPERATOR, ",");
                    result.add(str);
                    start = i + 1;
                }
            }
        }
        String[] s = new String[result.size()];
        return result.toArray(s);
    }
    
    public static void main(String args[]) {
        String[] r = fromCSV("search,20150501000154459,,,114.240.127.167,pc,,,,,a");
        for (String s : r) {
            System.out.println(":" + s);
        }
    }

}
