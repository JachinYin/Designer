package com.jachin.des.util;

import com.jachin.des.web.WebDesigner;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author Jachin
 * @since 2019/3/6 16:48
 */
public class Param extends HashMap {
    private HashMap<String, Object> map;

    public Param(){
        map = new HashMap<>();
    }

    public Param setBoolean(String key, Boolean val){
        return this.setObject(key, val);
    }
    public Param setDouble(String key, Double val){
        return this.setObject(key, val);
    }
    public Param setInt(String key, Integer val){
        return this.setObject(key, val);
    }
    public Param setString(String key, String val){
        return this.setObject(key, val);
    }
    public Param setParam(String key, Param val){
        return this.setObject(key, val);
    }

    public Param setObject(String key, Object value) {
        if (key != null && value != null) {
            this.map.put(key, value);
            return this;
        } else {
            return null;
        }
    }

    public Boolean getBoolean(String key){
        Object value = this.getObject(key);
        return value instanceof Null? null : (Boolean)value;
    }
    public Boolean getBoolean(String key, Boolean defaultVal){
        Boolean value = this.getBoolean(key);
        return value == null ? defaultVal: value;
    }

    public Double getDouble(String key){
        Object value = this.getObject(key);
        return value instanceof Null? null : (Double)value;
    }
    public Double getDouble(String key, Double defaultVal){
        Double value = this.getDouble(key);
        return value == null ? defaultVal: value;
    }

    public Integer getInt(String key){
        Object value = this.getObject(key);
        return value instanceof Null ? null : (Integer)value;
    }
    public Integer getInt(String key, Integer defaultVal){
        Integer value = this.getInt(key);
        return value == null ? defaultVal : value;
    }

    public String getString(String key){
        Object value = this.getObject(key);
        return value instanceof Null ? null : (String)value;
    }
    public String getString(String key, String defaultVal){
        String value = this.getString(key);
        return value == null ? defaultVal : (String)value;
    }

    public Param getParam(String key){
        Object value = this.getObject(key);
        return value instanceof Null ? null : (Param)value;
    }
    public Param getParam(String key, Param defaultVal){
        Param value = this.getParam(key);
        return value == null ? defaultVal: value;
    }

    public Object getObject(String key) {
        if (key == null) {
            return null;
        }
        return this.map.get(key);
    }


    /* 将 param 转换为 json 字符串 === start */
    public String toJson(){
        StringBuilder sb = new StringBuilder(512);
        dealBuf(sb);
        return sb.toString();
    }

    private void dealBuf(StringBuilder sb) {
        sb.append("{");
        int count = 0;

        for (Entry<String, Object> entry : this.map.entrySet()) {
            ++count;
            String key = entry.getKey();
            Object obj = entry.getValue();
            if (obj == null) {
                sb.setLength(0);
                sb.append("{}");
                return;
            }

            sb.append("\"");
            sb.append(key);
            sb.append("\":");
            toJson(obj, sb);
            if (count < this.map.size()) {
                sb.append(",");
            }
        }

        sb.append("}");
    }

    private void toJson(Object obj, StringBuilder buf) {
        if (obj != null) {
            byte type = parseType(obj);
            switch(type) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    buf.append(obj.toString());
                    return;
                case 6: // String
                    buf.append("\"");
                    this.encodeJson((String)obj, buf);
                    buf.append("\"");
                    return;
                case 7: // Calender
                    buf.append(((Calendar) obj).getTimeInMillis());
                    return;
                case 8:
                case 11:
                case 12:
                default:
                    buf.append("\"");
                    this.encodeJson(obj.toString(), buf);
                    buf.append("\"");
                    return;
                case 9:
                    ((Param)obj).dealBuf(buf);
                    return;
            }
        }
    }

    private byte parseType(Object obj) {
        if (obj instanceof Integer) {
            return 1;
        } else if (obj instanceof Long) {
            return 2;
        } else if (obj instanceof Float) {
            return 3;
        } else if (obj instanceof Double) {
            return 4;
        } else if (obj instanceof Boolean) {
            return 5;
        } else if (obj instanceof String) {
            return 6;
        } else if (obj instanceof Calendar) {
            return 7;
        } else if (obj instanceof Param) {
            return 9;
        } else if (obj instanceof Byte) {
            return 11;
        } else if (obj instanceof Short) {
            return 12;
        } else if (obj instanceof ByteBuffer) {
            return 8;
        }
        return  0;
    }

    private void encodeJson(String html, StringBuilder buf) {
        try {
            if (html == null) {
                return;
            }

            int len = html.length();
            boolean inBracket = false;

            for(int i = 0; i < len; ++i) {
                char c;
                label86: {
                    c = html.charAt(i);
                    switch(c) {
                        case '\b':
                            buf.append("\\b");
                            break label86;
                        case '\t':
                            buf.append("\\t");
                            break label86;
                        case '\n':
                            buf.append("\\n");
                            break label86;
                        case '\f':
                            buf.append("\\f");
                            break label86;
                        case '\r':
                            buf.append("\\r");
                            break label86;
                        case '!':
                            if (inBracket) {
                                buf.append("\\u0021");
                            } else {
                                buf.append("!");
                            }
                            break label86;
                        case '"':
                            buf.append("\\\"");
                            break label86;
                        case '/':
                            if (inBracket) {
                                buf.append("\\/");
                            } else {
                                buf.append("/");
                            }
                            break label86;
                        case '\\':
                            buf.append("\\\\");
                            break label86;
                    }

                    if ((c < 0 || c > 31) && (c < 127 || c > 159) && (c < 8192 || c > 8447)) {
                        buf.append(c);
                    } else {
                        String ss = Integer.toHexString(c);
                        buf.append("\\u");

                        for(int k = 0; k < 4 - ss.length(); ++k) {
                            buf.append('0');
                        }

                        buf.append(ss.toUpperCase());
                    }
                }

                if (c == '<') {
                    inBracket = true;
                } else {
                    inBracket = false;
                }
            }
        } catch (Exception var8) {
            buf.setLength(0);
        }

    }

    /**/

    public static Param parseParam(String json) {
        Param param = new Param();
        try {
            json = json.replace("{", "").replace("}", "");
            for (String s1 : json.split(",")) {
                String[] split = s1.split(":");
                String key = split[0];
                String val = split[1];
                param.setObject(key, val);
            }
        }catch (Exception e){
            return WebDesigner.getRetParam(false, "解析失败");
        }
        return param;
    }
}
