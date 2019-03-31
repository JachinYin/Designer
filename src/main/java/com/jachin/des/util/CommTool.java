package com.jachin.des.util;

import com.jachin.des.entity.AEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jachin
 * @since 2019/3/12 17:00
 */
public class CommTool {

    public static final String imgUrl = "E:/Code/Graduation/Designer/Img";
//    public static final String imgUrl = "D:/Jachin/Graduation/Code/Designer/Img";
//    public static
    public static Logger getLogger(Class classz){
        return LoggerFactory.getLogger(classz);
    }

    public static int mergeResParam(ResParam resParam, AEntity template){
        try {
            Class<?> classResParam = template.getClass();
            Field[] declaredFields = classResParam.getDeclaredFields();
            for(Field item : declaredFields){
                String name = item.getName();
                name = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
                Method getMethod = classResParam.getMethod(name);
                resParam.put(item.getName(), getMethod.invoke(template));
            }
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.isEmpty();
    }

    public static String getNowTime(String format){
        return new SimpleDateFormat(format).format(new Date());
    }
    public static String getNowTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
