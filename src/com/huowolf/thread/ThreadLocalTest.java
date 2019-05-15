package com.huowolf.thread;

/**
 * @author huowolf
 * @date 2019/5/14
 * @description
 *
 * ThreadLocal,线程变量，是一个以ThreadLocal对象为键，任意对象为值的存储结构。
 * ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
 * 最常见的ThreadLocal使用场景为 用来解决 数据库连接、Session管理等。
 *
 * 下面的例子演示了ThreadLocal在不同线程上创建了不同的变量副本。
 */
public class ThreadLocalTest {

    ThreadLocal<Long> longLocal = new ThreadLocal<>();
    ThreadLocal<String> stringLocal = new ThreadLocal<>();

    public void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }

    public long getLong() {
        return longLocal.get();
    }

    public String getString() {
        return stringLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalTest test = new ThreadLocalTest();

        test.set();
        System.out.println(test.getLong()+ ":"+ test.getString());

        Thread thread1 = new Thread(){
            public void run() {
                test.set();
                System.out.println(test.getLong()+ ":"+ test.getString());
            };
        };
        thread1.start();
        thread1.join();

        System.out.println(test.getLong()+ ":"+ test.getString());
    }
}
