package com.huowolf.container;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author huowolf
 * @date 2019/5/23
 * @description 验证HashMap在多线程下环境下存在并发问题:cpu 100%
 */
public class HashMapTest implements Runnable{


    final HashMap<String, Integer> map = new HashMap<>();


    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new Thread(new HashMapTest()).start();
        }

    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            String key = UUID.randomUUID().toString();
            map.put(key, i++);
            System.out.println("线程"+Thread.currentThread().getName()+"放入"+ map.get(key));
        }

    }


}
