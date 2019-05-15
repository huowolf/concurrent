package com.huowolf.thread;

/**
 * @author huowolf
 * @date 2019/5/15
 * @description 演示使用Volatile变量。
 */
public class VolatileExample {

    private static volatile boolean flag = false;

    private static int a = 0;


    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {

            //false
            System.out.println(a == 1);

            //如果不使用volatile关键字，本线程将无法看到main线程对flag变量的改变。
            //程序将一直阻塞在这里。
            //如果使用volatile变量，本线程将会看到main线程对flag变量的改变。
            while(!flag){

            }

            //如果使用volatile变量，这里会打印true
            System.out.println(a == 1);
        }).start();

        Thread.sleep(100);

        flag = true;
        a = 1;
    }
}
