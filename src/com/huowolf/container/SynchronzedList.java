package com.huowolf.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * @author huowolf
 * @date 2019/7/17
 * @description
 */
public class SynchronzedList {

    //请求总数
    public static int clientTotal = 1000;

    //同时并发执行的线程数
    public static int threadTotal = 50;

    public static List<Integer> list = Collections.synchronizedList(new ArrayList());

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);

        //控制请求总数
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {

            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                }catch (Exception e){
                    e.printStackTrace();
                }

                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        executorService.shutdown();

        System.out.println("size: "+list.size());

        //迭代遍历
        synchronized (list) {
            Iterator i = list.iterator(); // Must be in synchronized block
            while (i.hasNext()){
                //System.out.println(i.next());
            }

        }
    }

    private static void update(int i) {
        list.add(i);
    }
}
