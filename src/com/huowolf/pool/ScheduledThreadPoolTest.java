package com.huowolf.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author huowolf
 * @date 2019/5/27
 * @description 演示newScheduledThreadPool的使用
 */
public class ScheduledThreadPoolTest {

    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.schedule(() -> {
                System.out.println(Thread.currentThread().getName() + "\t开始发车啦....");
            }, 10, TimeUnit.SECONDS);
        }
    }
}
