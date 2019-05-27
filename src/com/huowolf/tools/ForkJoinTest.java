package com.huowolf.tools;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author huowolf
 * @date 2019/5/27
 * @description
 *
 * 使用fork-join框架来计算1+2+3+4+5+6+7+8+8+10的值
 */
public class ForkJoinTest {

    static class CountTask extends RecursiveTask<Integer> {

        private static final int THRESHOLD = 2; //阈值

        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {

            int sum = 0;

            //如果任务足够小，就计算任务
            boolean canComputer = (end - start) <= THRESHOLD;
            if(canComputer) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            }else {
                //如果任务大于阈值，就分裂成两个子任务计算
                int middle = (start + end)/2;
                CountTask leftTask = new CountTask(start,middle);
                CountTask rightTask = new CountTask(middle+1,end);

                //执行子任务
                leftTask.fork();
                rightTask.fork();

                //等待子任务执行完，得到结果
                int leftResult = leftTask.join();
                int rightResult = rightTask.join();

                //合并子任务
                sum = leftResult + rightResult;
            }

            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        CountTask task = new CountTask(1,10);

        ForkJoinTask<Integer> result = forkJoinPool.submit(task);

        System.out.println(result.get());
    }
}
