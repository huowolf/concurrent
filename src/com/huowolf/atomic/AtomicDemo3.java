package com.huowolf.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author huowolf
 * @date 2019/7/17
 * @description
 *
 * 要想使用原子更新字段需要两步操作：
 *
 * 1. 原子更新字段类都是抽象类，只能通过静态方法newUpdater来创建一个更新器，并且需要设置想要更新的类和属性；
 * 2. 更新类的属性必须使用public volatile进行修饰；
 */
public class AtomicDemo3 {

    private static AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(User.class,"age");
    public static void main(String[] args) {
        User user = new User("a", 1);
        int oldValue = updater.getAndAdd(user, 5);
        System.out.println(oldValue);
        System.out.println(updater.get(user));
    }

    static class User {
        private String userName;
        public volatile int age;

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userName='" + userName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
