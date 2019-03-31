package com.jachin.des.util;

/**
 * @author Jachin
 * @since 2019/3/31 10:18
 */
public class CurrentUser {
    /**
     * 线程绑定的存储空间
     */
    private static final InheritableThreadLocal<Integer> THREAD_LOCAL = new InheritableThreadLocal<>();

    /**
     * 保存用户id
     * @param userId 用户id
     */
    public static void setCurrentAid(int userId){
        THREAD_LOCAL.set(userId);
    }

    /**
     * 获取用户id
     * @return 用户id
     */
    public static Integer getCurrentAid() {
        return THREAD_LOCAL.get();
    }

    public static void removeCurrentAid(){
        THREAD_LOCAL.remove();
    }
}
