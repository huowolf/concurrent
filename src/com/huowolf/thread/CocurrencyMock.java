package com.huowolf.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huowolf
 * @date 2019/7/17
 * @description
 * 并发模拟
 */
public class CocurrencyMock {

    //请求总数
    public static int clientTotal = 1000;

    //同时并发执行的线程数
    public static int threadTotal = 50;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);

        //控制请求总数
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    e.printStackTrace();
                }

                //计数器减一
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        executorService.shutdown();

        //it should be 1000
        System.out.println(count);
    }

    private static void add() {

        count.getAndIncrement();

    }
}
