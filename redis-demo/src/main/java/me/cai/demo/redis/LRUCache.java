package me.cai.demo.redis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * me.cai.demo.redis
 *
 * @author caiguangzheng
 * date: 2019-07-14
 * description: 如何利用java的库函数实现LRU算法
 * 思路就是集成 {@link LinkedHashMap}。{@link LinkedHashMap}默认
 * 是按插入顺序排序，通过重载构造函数设置accessOrder 为 true，实现按访问时间排序。
 * 再通过重写 {@link #removeEldestEntry(Map.Entry)} 方法，设置是否移除最末尾
 * 的key，以及移除条件。
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int CACHE_SIZE;


    public LRUCache(int cacheSize) {
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > CACHE_SIZE;
    }
}
