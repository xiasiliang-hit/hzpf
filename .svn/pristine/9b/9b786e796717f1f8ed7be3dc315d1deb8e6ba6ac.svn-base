package com.line.bqxd.platform.manager.cache;

import com.google.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by huangjianfei on 16/5/31.
 */
public class AuthCodeCache {

    private static Logger logger = LoggerFactory.getLogger(AuthCodeCache.class);

    private static final int DEFAULT_PRE_MINUTE_FREQUENCY_COUNT = 10;

    private int preMinuteCount = DEFAULT_PRE_MINUTE_FREQUENCY_COUNT;

    private Map<String, AtomicLong> map = new ConcurrentHashMap<String, AtomicLong>(100);

    //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
    LoadingCache<String, String> cache
            //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
            = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            //设置写缓存后60秒钟过期
           // .expireAfterWrite(1, TimeUnit.SECONDS)
            .expireAfterAccess(1,TimeUnit.SECONDS)
            //设置缓存容器的初始容量为10
            .initialCapacity(100)
            //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(1000)
            //设置要统计缓存的命中率
            .recordStats()
            //设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    Object object = notification.getKey();
                    Object value = map.remove(object);
                    if (logger.isDebugEnabled()) {
                        logger.debug("cache remove" + notification.getKey() + " was removed,value=" + value + " cause is " + notification.getCause());
                    }
                    System.out.println("======cache remove" + notification.getKey() + " was removed,value=" + value + " cause is " + notification.getCause()+"====="+System.currentTimeMillis());

                }
            })
            //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(
                    new CacheLoader<String, String>() {
                        @Override
                        public String load(String key) throws Exception {
                            map.put(key, new AtomicLong(1));
                            return key;
                        }
                    }
            );


    public AtomicLong getCacheCount(String key) {
        return map.get(key);
    }

    public void setCache(String key){
        cache.put(key,"1");
        map.put(key, new AtomicLong(1));
    }

    public boolean validateFrequency(String key) throws ExecutionException {
        AtomicLong atomicLong = getCacheCount(key);
        if (atomicLong == null) {
            setCache(key);
            return true;
        }
        //cache.get(key);
        if (atomicLong.longValue() < preMinuteCount) {
            atomicLong.incrementAndGet();
            return true;
        } else {
            return false;
        }
    }

    public void setPreMinuteCount(String preMinuteCount) {
        this.preMinuteCount = Integer.parseInt(preMinuteCount);
    }

    public static void main(String[] args) {
        AuthCodeCache codeCache = new AuthCodeCache();
        String code = "123";
        for (int i = 0; i < 15; i++) {
            try {
                System.out.print(System.currentTimeMillis()+"///"+i+"_"+code+"///");
                System.out.println(codeCache.validateFrequency(i+"_"+code));
                Thread.sleep(40000);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

}
