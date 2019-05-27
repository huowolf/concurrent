package com.huowolf.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author huowolf
 * @date 2019/5/27
 * @description 演示submit(Callable task)的使用
 */
public class SubmitTest1 {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Future<String> future = pool.submit(new Callable<String>() {

            @Override
            public String call() {
                return "Hello";
            }
        });
        String result = future.get();
        System.out.println(result);
    }
}
