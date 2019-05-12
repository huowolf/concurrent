package com.huowolf.single;

/**
 * @author huowolf
 * @date 2019/5/12
 * @description 线程安全的饿汉模式单例
 *
 * 饿汉：并不延迟初始化，立即初始化好一个对象。
 * 类加载时就初始化，基于classloader机制避免了多线程的同步问题
 */
public class Singleton1 {

    private final static Singleton1 singleton = new Singleton1();

    private Singleton1(){}

    public static Singleton1 getSingleton(){
        return singleton;
    }
}
