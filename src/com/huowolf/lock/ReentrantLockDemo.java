package com.huowolf.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huowolf
 * @date 2019/5/14
 * @description 使用ReentrantLock模拟电影院的售票情况
 */
public class ReentrantLockDemo implements Runnable{

    private Lock lock = new ReentrantLock();

    private int ticket = 200;

    @Override
    public void run() {

        while (true){
            lock.lock();

            try{
                if(ticket > 0){
                    TimeUnit.MILLISECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName()+":"+ticket--);
                }else {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }


    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(reentrantLockDemo,"thread"+i);
            thread.start();
        }
    }
}
