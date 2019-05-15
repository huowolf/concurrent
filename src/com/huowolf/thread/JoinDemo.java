package com.huowolf.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author huowolf
 * @date 2019/5/12
 * @description
 * 如果一个线程A执行了thread.join()语句，其含义是：
 * 当前线程A等待thread线程(某个调用了join方法线程)终止之后才会从thread.join()返回。
 *
 *
 * 本例演示10个线程依次执行。
 */
public class JoinDemo {

    //多米诺
    static class Domino implements Runnable {
        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {

            try {

                //等待上一个线程执行完，才继续执行本线程。
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " terminate.");
        }

        public static void main(String[] args) throws InterruptedException {
            Thread previous = Thread.currentThread();

            for (int i = 0; i < 10; i++) {

                //每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回。
                Thread thread = new Thread(new Domino(previous),String.valueOf(i));
                thread.start();
                previous = thread;
            }

            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}
