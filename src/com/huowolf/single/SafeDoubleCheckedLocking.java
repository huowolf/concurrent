package com.huowolf.single;

/**
 * @author huowolf
 * @date 2019/5/12
 * @description 基于双重检查锁定来实现延迟初始化（使用volatile的方案实现线程安全）
 */
class Instance {}

public class SafeDoubleCheckedLocking {

    private volatile static Instance instance;

    public static Instance getInstance() {
        if(instance == null){
            synchronized (SafeDoubleCheckedLocking.class) {
                if(instance == null){
                    instance = new Instance();
                }
            }
        }

        return instance;
    }

}
