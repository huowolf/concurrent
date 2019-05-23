package com.huowolf.container;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huowolf
 * @date 2019/5/23
 * @description
 * 在jdk1.8的版本下，ConcurrentHashMap同样存在并发问题：cpu 100%
 * 该问题在jdk1.9中得到修复。
 */
public class ConcurrentHashMapTest implements Runnable{

    private ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new Thread(new HashMapTest()).start();
        }

    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            String key = UUID.randomUUID().toString();
            map.put(key, i);
            System.out.println("线程"+Thread.currentThread().getName()+"放入"+ map.get(key));
        }

    }
}
