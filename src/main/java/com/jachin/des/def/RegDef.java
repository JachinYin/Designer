package com.jachin.des.def;

/**
 * @author Jachin
 * @since 2019/4/6 10:22
 */
public class RegDef {
    public static final String idCardReg = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    public static final String phoneReg = "^1[345789]\\d{9}$";
    public static final String qqReg = "^[1-9]\\d{5,11}$";

    public static final String idCardInfo = "身份证格式错误。";
    public static final String phoneInfo = "手机格式错误。";
    public static final String qqInfo = "QQ格式错误";



    public static String getMsg(String name) {
        String msg;
        switch (name) {
            case idCardReg:
                msg = "";
                break;
            case phoneReg:
                msg = "手机号码格式错误。";
                break;
            case qqReg:
                msg = "QQ格式错误。";
                break;
            default:
                msg = "";
        }
        return msg;
    }
}
