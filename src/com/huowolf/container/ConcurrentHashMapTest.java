package com.huowolf.container;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huowolf
 * @date 2019/5/23
 * @description
 * 在1.8版本以前，ConcurrentHashMap采用分段锁的概念，使锁更加细化，
 * 但是1.8已经改变了这种思路，而是利用CAS+Synchronized来保证并发更新的安全，当然底层采用数组+链表+红黑树的存储结构。
 */
public class ConcurrentHashMapTest {

    private static ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);


        for (int i = 0; i < 1000; i++) {
            if((i%2) == 1){
                executorService.execute(() -> {
                    for (int j = 0; j < 10; j++) {
                        String key = UUID.randomUUID().toString();
                        map.put(key, j);
                        System.out.println("线程"+Thread.currentThread().getName()+"放入"+ map.get(key));
                    }
                });
            }else{
                executorService.execute(() ->{
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("此时容量为："+map.size());
                });
            }
        }

        executorService.shutdown();
    }


}
