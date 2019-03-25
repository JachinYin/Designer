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
        public static final int NOT_NOW = -1;
        public static final int WAIT = 1;       // 待审核
        public static final int BACK = 2;       // 打回
        public static final int PASS = 3;       // 认证
        public static final int DEL = 4;        // 已注销
    }


    // 设计师状态
    public static final class CashFlag {
        public static final int WAIT = 1;       // 收入
        public static final int BACK = 2;       // 提现
    }


}
