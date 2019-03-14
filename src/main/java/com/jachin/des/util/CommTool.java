package com.jachin.des.util;

import com.jachin.des.entity.AEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jachin
 * @since 2019/3/12 17:00
 */
public class CommTool {
//    public static
    public static Logger getLogger(){
        return LoggerFactory.getLogger(Class.class);
    }

    public static String dealDateFormat(String oldDate) {

        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return df2.format(date1);
    }

    public static void mergeResParam(ResParam resParam, AEntity template) throws Exception {
        Class<?> classResParam = template.getClass();
        Field[] declaredFields = classResParam.getDeclaredFields();
        for(Field item : declaredFields){
            String name = item.getName();
            name = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
            Method getMethod = classResParam.getMethod(name);
            resParam.put(item.getName(), getMethod.invoke(template));
        }
    }
}
