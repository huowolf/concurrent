package com.huowolf.single;

/**
 * @author huowolf
 * @date 2019/5/12
 * @description 线程安全的懒汉模式单例
 *
 * 懒汉：延迟初始化对象
 * 通过加锁synchronized保证单例，但加锁会影响效率。
 */
public class Singleton2 {

    private static Singleton2 singleton = new Singleton2();

    private Singleton2 (){}

    public static synchronized Singleton2 getSingleton(){
        if (singleton == null){
            singleton = new Singleton2();
        }

        return singleton;
    }
}
