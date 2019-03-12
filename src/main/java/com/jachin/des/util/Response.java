package com.jachin.des.util;

import java.util.HashMap;

/**
 * @author Jachin
 * @since 2019/3/12 14:49
 */
public class Response {
    private boolean success;
    private String msg;
    private HashMap<String, Object> other;

    public Response() {
    }

    public Response(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
        this.other = new HashMap<>();
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

    public HashMap<String, Object> getOther() {
        return other;
    }

    public void setOther(HashMap<String, Object> other) {
        this.other = other;
    }
}
