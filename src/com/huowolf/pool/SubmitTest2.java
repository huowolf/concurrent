package com.huowolf.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author huowolf
 * @date 2019/5/27
 * @description
 * 演示submit(Runnable task, T result)的使用。
 */
public class SubmitTest2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Data data = new Data();
        Future<Data> future = pool.submit(new MyRunnable(data), data);
        String result = future.get().getName();
        System.out.println(result);
    }
}

class Data {
    String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class MyRunnable implements Runnable {
    private Data data;

    public MyRunnable(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        data.setName("huowolf");
    }
}
