package com.jachin.des.util;

import com.jachin.des.entity.AEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jachin
 * @since 2019/3/12 17:00
 */
public class CommTool {

    public static Response validateArg(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
//            StringBuilder errInfo = new StringBuilder();
//            for (FieldError fieldError : bindingResult.getFieldErrors()) {
//                errInfo.append(fieldError.getDefaultMessage()).append(";");
//            }
//            return new Response(false, errInfo.toString());
            return new Response(false, bindingResult.getFieldError().getDefaultMessage());
        }
        return null;
    }


    // 存放图片资源的路径
    public static final String imgUrl = "E:/Code/Graduation/Designer";
//    public static final String imgUrl = "D:/Jachin/Graduation/Code/Designer/Img";
//    public static
    public static Logger getLogger(Class myClass){
        return LoggerFactory.getLogger(myClass);
    }

    // 合并ResParam
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

    // 判断String 非空非null
    public static boolean isNotBlank(String str) {
        return str != null && !str.isEmpty();
    }

    // 获取当前时间
    public static String getNowTime(String format){
        return new SimpleDateFormat(format).format(new Date());
    }
    public static String getNowTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }


    public static boolean isPicture(String suffix){
        return suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png") || suffix.equals(".gif");
    }
    // 文件去重
    public static String getFileMD5String(MultipartFile file) {
        try {
            MessageDigest mMessageDigest = null;
            mMessageDigest = MessageDigest.getInstance("MD5");
            InputStream fis = file.getInputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) > 0) {
                mMessageDigest.update(buffer, 0, length);
            }
            fis.close();
            return new BigInteger(1, mMessageDigest.digest()).toString(16);
        } catch (Exception e) {
            return "";
        }
    }
}
