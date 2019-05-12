package com.huowolf.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

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
 * wait():调用该方法的线程进入Waiting状态，只有等待另外的线程通知或者被中断才会返回。
 * 需要注意，调用wait()方法后，会释放对象的锁。
 * 调用wait()方法后，线程状态由Running转变为Wating,并将当前线程放置到对象的等待队列。
 *
 *
 * notify():通知一个在对象上等待的线程，使其从wait()方法返回。
 * 而返回的前提是该对象获取到了对象的锁。
 * notify()方法是将等待队列中的一个等待线程从等待队列移到同步队列。
 * 被移动的线程状态由Wating转变为Blocked
 *
 */
public class ProducerConsumer {


    public static void main(String[] args) {

        Queue<Integer> buffer = new LinkedList<>();
        int maxSize = 10;

        Thread producer = new Producer("PRODUCER",buffer,maxSize);
        Thread consumer = new Consumer("CONSUMER",buffer,maxSize);

        producer.start();
        consumer.start();

    }
}

//生产者
class Producer extends Thread{

    private Queue<Integer> queue;
    private int maxSize;

    Producer(String name, Queue<Integer> queue, int maxSize) {
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            //对共享变量加锁
            synchronized (queue) {

                //如果队列已满
                while (queue.size() == maxSize){
                    try {
                        System.out.println("队列已满，正在等待消费者消费。");

                        //wait
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                //队列不满时，可以生产对象
                Random random = new Random();
                int i = random.nextInt();
                System.out.println("生产："+i);
                queue.add(i);

                //通知消费者消费
                queue.notifyAll();
            }
        }
    }
}


//消费者
class Consumer extends Thread {
    private Queue<Integer> queue;
    private int maxSize;

    public Consumer(String name, Queue<Integer> queue, int maxSize) {
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {

            synchronized (queue) {

                //如果队列已空
                while (queue.isEmpty()) {
                    System.out.println("队列已空，正在等待生产者生产");

                    //wait
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                //如果队列不空，可以消费
                System.out.println("消费："+queue.remove());

                //通知生产者生产
                queue.notifyAll();
            }
        }
    }
}
