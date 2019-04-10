package com.jachin.des.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author Jachin
 * @since 2019/3/13 14:11
 */
@Component
public class ResParam extends HashMap<String, Object> {

    public ResParam(){}

    public ResParam(String key, Object val){
        super();
        put(key, val);
    }
}
