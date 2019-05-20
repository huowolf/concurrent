package com.huowolf.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author huowolf
 * @date 2019/5/15
 * @description 通过缓存示例说明读写锁的使用方式。
 */
public class Cache {

    static Map<String,Object> map = new HashMap<>();

    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    static Lock r = rwl.readLock();

    static Lock w = rwl.writeLock();

    public static final Object get(String key){
        r.lock();
        try {
            return map.get(key);
        }finally {
            r.unlock();
        }
    }

    public static final Object put(String key, Object value){
        w.lock();
        try {
            return map.put(key, value);
        }finally {
            w.unlock();
        }
    }

    public static final void clear(){
        w.lock();
        try {
            map.clear();
        }finally {
            w.unlock();
        }
    }
}
