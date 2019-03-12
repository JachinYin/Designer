package com.jachin.des.util;

import com.jachin.des.web.WebDesigner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    // list
    public <T> Param setList(String key, List<T> value) {
        return this.setObject(key, value);
    }

    public <T> List<T> getList(String key) {
        Object value = this.getObject(key);
        return value instanceof Null ? null : (List)value;
    }

    public <T> List<T> getListNullIsEmpty(String key) {
        List<T> value = this.getList(key);
        return value == null ? new ArrayList<>() : value;
    }

    public <T> List<T> getList(String key, List<T> defaultValue) {
        List<T> value = this.getList(key);
        return value != null && value.size() > 0 ? value : defaultValue;
    }


    /* 将 param 转换为 json 字符串 === start */
    public String toJson(){
        StringBuilder sb = new StringBuilder(512);
        dealBuf(sb);
        return sb.toString();
    }

    public void dealBuf(StringBuilder sb) {
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
            JsonParse.toJson(obj, sb);
            if (count < this.map.size()) {
                sb.append(",");
            }
        }

        sb.append("}");
    }

    /*
    * 将JSON 解释为Param 对象
    * */
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
            return WebDesigner.getRetParam(false, "解析失败").setString("jsonStr", json);
        }
        return param;
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}
