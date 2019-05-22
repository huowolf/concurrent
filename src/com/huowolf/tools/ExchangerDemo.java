package com.huowolf.tools;


import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huowolf
 * @date 2019/5/20
 * @description
 *
 * Exchanger:用于线程间交换数据。
 * 它提供了一个同步点，在这个同步点，两个线程可以交换彼此的数据。
 */
public class ExchangerDemo {

    private static final Exchanger<String> exchanger = new Exchanger<>();

    private static final ExecutorService threadpool = Executors.newFixedThreadPool(2);

    public static void main(String[] args){

        threadpool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String A = "银行流水A";

                    exchanger.exchange(A);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });


        threadpool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = "银行流水B";

                    String A = exchanger.exchange(B);

                    System.out.println("A和B数据是否一致："+A.equals(B));
                    System.out.println("A录入的数据是："+A);
                    System.out.println("B录入的数据是："+B);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        threadpool.shutdown();

    }
}