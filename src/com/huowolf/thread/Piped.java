package com.huowolf.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author huowolf
 * @date 2019/5/12
 * @description 管道输入/输出流
 *
 * 本例演示PintThread接受Main线程的输入，然后打印。
 */
public class Piped {

    static class Print implements Runnable {

        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;

            try {
                while ((receive = in.read()) != -1) {
                    System.out.println(Thread.currentThread().getName()+"："+(char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {

        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);

        Thread printThread = new Thread(new Print(in),"PrintThread");
        printThread.start();

        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        } finally {
            out.close();
        }
    }
}
