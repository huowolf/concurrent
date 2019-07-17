package com.huowolf.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author huowolf
 * @date 2019/7/17
 * @description
 */
public class AtomicDemo1 {

    private static int[] value = new int[]{1, 2, 3};
    private static AtomicIntegerArray integerArray = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        //对数组中索引为1的位置的元素加5
        int result = integerArray.getAndAdd(1, 5);
        System.out.println(integerArray.get(1));
        System.out.println(result);
    }
}
