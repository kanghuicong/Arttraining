package com.example.kk.arttraining.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 作者：wschenyongyin on 2016/12/28 09:20
 * 说明:图片缓存优化处理类
 * 算法是最近最少使用算法。
 * 它把最近使用的对象用“强引用”存储在LinkedHashMap中，并且把最近最少使用的对象在缓存值达到预设定值之前就从内存中移除
 */
public class LruCacheUtils extends LruCache<String, Bitmap> {
    //获取手机内存大小
    private static int MAXMEMONRY = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static LruCacheUtils cacheUtils;

    private LruCacheUtils(int maxSize) {
        super(maxSize);
    }

    /**
     * 单例
     */
    public static LruCacheUtils getInstance() {
        if (cacheUtils == null) {
            //创建对象时分配缓存，我们取内存的5分之一
            cacheUtils = new LruCacheUtils(MAXMEMONRY / 5);
        }
        return cacheUtils;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    /**
     * 清理缓存
     */
    public void clearCache() {
        if (cacheUtils.size() > 0) {
            cacheUtils.evictAll();
        }
    }


    /**
     * 添加缓存图片
     */
    public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (cacheUtils.get(key) != null) {
            return;
        }
        if (!isEmpty(key) && bitmap != null) {
            cacheUtils.put(key, bitmap);
        }
    }


    /**
     * 获取缓存图片
     */
    public synchronized Bitmap getBitmapFromMemCache(String key) {
        if (isEmpty(key)) {
            return null;
        }
        Bitmap bm = cacheUtils.get(key);
        if (bm != null && !bm.isRecycled()) {
            return bm;
        }
        return null;
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public synchronized void removeImageCache(String key) {
        if (isEmpty(key)) {
            return;
        }
        Bitmap bm = cacheUtils.remove(key);
        if (bm != null && !bm.isRecycled()) {
            bm.recycle();
        }
    }


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public boolean isEmpty(String... str) {
        if (str == null) {
            return true;
        }
        for (String s : str) {
            if (s == null || s.isEmpty() || s.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }


}