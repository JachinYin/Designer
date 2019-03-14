package com.jachin.des.entity.def;

/**
 * @author Jachin
 * @since 2019/3/14 13:56
 */
public class DesignerDef {
    public static final class Status {
        public static final int NOT_NOW = -1;
        public static final int WAIT = 1;       // 待审核
        public static final int BACK = 2;       // 打回
        public static final int PASS = 3;       // 认证
        public static final int DEL = 4;        // 已注销
    }
}
