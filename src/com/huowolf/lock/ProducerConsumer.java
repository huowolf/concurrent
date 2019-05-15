package com.huowolf.lock;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huowolf
 * @date 2019/5/12
 * @description 生产者消费者问题
 *
 * 1. 生产者仅仅在仓储未满时生产，仓满则停止生产
 * 2. 消费者仅仅在仓储有产品时才能消费，仓空则等待。
 * 3. 当消费者发现仓储没产品可消费时会通知生产者生产。
 * 4. 生产者在生产出可消费产品时，应该通知等待的消费者去消费。
 *
 *
 * 使用ReentrantLock以及Condition实现生产者消费者模式
 */
public class ProducerConsumer {

    private Lock lock = new ReentrantLock();

    private Condition addCondition = lock.newCondition();

    private Condition removeCondition = lock.newCondition();

    private Queue<Integer> queue = new LinkedList<>();

    private int maxSize;

    public ProducerConsumer(int maxSize) {
        this.maxSize = maxSize;
    }

    //生产者
    class Producer implements Runnable {

        @Override
        public void run() {
            while (true) {

                lock.lock();

                try {
                    //如果队列已满
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("队列已满，正在等待消费者消费。");

                            addCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    //队列不满时，可以生产对象
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("生产：" + i);
                    queue.add(i);

                    //通知消费者消费
                    removeCondition.signal();
                }finally {
                    lock.unlock();
                }


            }
        }
    }


    //消费者
    class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {

                lock.lock();

                try{
                    //如果队列已空
                    while (queue.isEmpty()) {
                        System.out.println("队列已空，正在等待生产者生产");

                        //wait
                        try {
                            removeCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    //如果队列不空，可以消费
                    System.out.println("消费：" + queue.remove());

                    //通知生产者生产
                    addCondition.signal();

                }finally {
                    lock.unlock();
                }

            }
        }
    }



    public static void main(String[] args) {

        ProducerConsumer producerConsumer = new ProducerConsumer(10);


        Producer producer = producerConsumer.new Producer();
        Consumer consumer = producerConsumer.new Consumer();

        Thread producerThread = new Thread(producer, "producer");
        Thread consumerThread = new Thread(consumer, "consumer");
        producerThread.start();
        consumerThread.start();

    }
}