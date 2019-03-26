package com.jachin.des.entity;

/**
 * @author Jachin
 * @since 2019/3/14 15:33
 */
public class DataDef {
    // 模板审核状态
    public static final class TemplateStatus {
        public static final int DEFAULT = 0;       // 未提交审核
        public static final int WAIT = 1;       // 待审核
        public static final int BACK = 2;       // 打回
        public static final int PASS = 3;       // 通过
    }

    // 设计师状态
    public static final class DesignerStatus {
        public static final int NOT_NOW = 0;    // 待提交审核
        public static final int WAIT = 1;       // 待审核
        public static final int BACK = 2;       // 打回
        public static final int PASS = 3;       // 认证
        public static final int DEL = 4;        // 已注销
    }

    // 设计师状态
    public static final class CashFlag {
        public static final int INCOME = 1;       // 收入
        public static final int WITHDRAW = 2;       // 提现
        public static final int DELTA_PRICE = 3;       // 提现
    }

    public static int getTemplateStatus(String name) {
        int flag = -1;
        switch (name) {
            case "pass":
                flag = TemplateStatus.PASS;
                break;
            case "back":
                flag = TemplateStatus.BACK;
                break;
            case "wait":
                flag = TemplateStatus.WAIT;
                break;
            case "default":
                flag = TemplateStatus.DEFAULT;
                break;
            default:
                flag = 0;
        }
        return flag;
    }

    public static int getCashFlag(String name) {
        int flag = -1;
        switch (name) {
            case "withdraw":
                flag = CashFlag.WITHDRAW;
                break;
            case "income":
                flag = CashFlag.INCOME;
                break;
            default:
                flag = 0;
        }
        return flag;
    }

}
