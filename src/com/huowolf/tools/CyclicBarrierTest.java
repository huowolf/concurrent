package com.huowolf.tools;

import java.util.concurrent.CyclicBarrier;

/**
 * @author huowolf
 * @date 2019/5/22
 * @description
 * CyclicBarrier:允许一组线程互相等待，直到到达某个公共屏障点，才会进行后续任务。
 * CyclicBarrier可以用于多线程计算数据，最后合并计算结果的应用场景。
 */
public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier;

    static class CyclicBarrierThread extends Thread{
        public void run() {
            System.out.println(Thread.currentThread().getName() + "到了");
            //等待
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("人到齐了，开会吧....");
            }
        });

        for(int i = 0 ; i < 5 ; i++){
            new CyclicBarrierThread().start();
        }
    }
}
