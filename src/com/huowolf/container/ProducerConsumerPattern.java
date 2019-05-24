package com.huowolf.container;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author huowolf
 * @date 2019/5/24
 * @description 使用阻塞队列实现生产者消费者模式
 */
public class ProducerConsumerPattern {


    static class Producer implements Runnable{
        private final BlockingDeque sharedQueue;

        public Producer(BlockingDeque sharedQueue) {
            this.sharedQueue = sharedQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {

                System.out.println("生产："+i);
                try {
                    sharedQueue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable{
        private final BlockingDeque sharedQueue;

        public Consumer(BlockingDeque sharedQueue) {
            this.sharedQueue = sharedQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    System.out.println("消费："+sharedQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public static void main(String[] args) {

        BlockingDeque sharedQueue = new LinkedBlockingDeque();

        Thread prodThread = new Thread(new Producer(sharedQueue));
        Thread consThread = new Thread(new Consumer(sharedQueue));

        prodThread.start();
        consThread.start();

    }
}

