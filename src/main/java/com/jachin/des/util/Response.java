package com.jachin.des.util;

import java.util.HashMap;

/**
 * @author Jachin
 * @since 2019/3/12 14:49
 */
public class Response {
    private boolean success;
    private int code;
    private String msg;
    private HashMap<String, Object> data;

    public Response() {
    }

    public Response(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
        this.data = new HashMap<>();
    }
    public Response(boolean success) {
        this.success = success;
        if(success)
            this.msg = "操作成功~";
        else
            this.msg = "操作失败";
        this.data = new HashMap<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
