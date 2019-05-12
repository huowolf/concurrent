package com.huowolf.single;

/**
 * @author huowolf
 * @date 2019/5/12
 * @description 基于类初始化方案来实现线程安全的延迟加载
 *
 * 在执行类的初始化期间，JVM会获取一个锁，这个锁可以同步多个线程对同一个类的初始化。
 */
public class InstanceFactory {

    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        return InstanceHolder.instance;     //这里将导致InstanceHolder类被初始化
    }
}
