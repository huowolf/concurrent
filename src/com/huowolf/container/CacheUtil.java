package com.huowolf.container;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 缓存工具类
 *
 * @author lance
 * @since 2018-10-25
 */
public class CacheUtil {
    /**
     * 存储需缓存数据的map
     */
    private final static Map<String, Entity> MAP = new ConcurrentHashMap<>();
    /**
     * 定时器线程池，用于清除过期缓存
     */
    private final static ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    /**
     * 添加缓存
     *
     * @param key  map的key
     * @param data map的value
     */
    public synchronized static void put(String key, Object data) {
        CacheUtil.put(key, data, 0);
    }

    /**
     * 添加缓存
     *
     * @param key    map的key
     * @param data   map的value
     * @param expire 过期时间，单位：毫秒， 0表示无限长
     */
    public synchronized static void put(String key, Object data, long expire) {
        //清除原map
        CacheUtil.remove(key);
        //设置过期时间
        if (expire > 0) {
            Future future = EXECUTOR.schedule(() ->
            {
                //过期后清除该map
                synchronized (CacheUtil.class) {
                    MAP.remove(key);
                }
            }, expire, TimeUnit.MILLISECONDS);
            MAP.put(key, new Entity(data, future));
        } else {
            //不设置过期时间
            MAP.put(key, new Entity(data, null));
        }
    }

    /**
     * 校验缓存中是否存在key
     *
     * @param key map的key
     * @return 是否存在key
     */
    public synchronized static boolean keyExists(String key) {
        return MAP.get(key) != null;
    }

    /**
     * 读取缓存
     *
     * @param key map的key
     * @return map的value
     */
    public synchronized static Object get(String key) {
        Entity entity = MAP.get(key);
        return entity == null ? null : entity.getValue();
    }

    /**
     * 读取缓存
     *
     * @param key 键
     * @param cls 值类型
     * @return map中value存储的对象
     */
    public synchronized static <T> T get(String key, Class<T> cls) {
        return cls.cast(CacheUtil.get(key));
    }

    /**
     * 清除缓存
     *
     * @param key map的key
     * @return
     */
    public synchronized static void remove(String key) {
        //清除原缓存数据
        Entity entity = MAP.remove(key);

        //清除原map定时器
        if (null != entity) {
            Future future = entity.getFuture();
            if (future != null) {
                future.cancel(true);
            }
        }
    }

    /**
     * 缓存实体类
     */
    private static class Entity {
        /**
         * map的值
         */
        private Object value;
        /**
         * 定时器
         */
        private Future future;

        private Entity(Object value, Future future) {
            this.value = value;
            this.future = future;
        }

        /**
         * 获取map值
         *
         * @return map值
         */
        public Object getValue() {
            return value;
        }

        /**
         * 获取Future对象
         *
         * @return Future对象
         */
        private Future getFuture() {
            return future;
        }
    }


    public static void main(String[] args) {
        CacheUtil.put("key1","value1",60*1000);

        boolean exists = CacheUtil.keyExists("key1");
        if(exists){
            System.out.println(CacheUtil.get("key1",String.class));
        }

        CacheUtil.remove("key1");
    }
}