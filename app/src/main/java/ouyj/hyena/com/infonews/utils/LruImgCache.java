package ouyj.hyena.com.infonews.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * LruCache：Andorid的内存缓存
 * Lru策略：当缓存超出容量时，优先淘汰链表中最近最少使用的数据（通过LinkedHashMap实现）
 */
public class LruImgCache implements ImageLoader.ImageCache {

    private static LruCache<String, Bitmap> memoryCache;
    private static LruImgCache imgCache;

    private LruImgCache(){
        //获得最大的可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        //设置缓存的大小
        memoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap){
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    public static LruImgCache instance(){
        if(imgCache == null){
            imgCache = new LruImgCache();
        }
        return imgCache;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return memoryCache.get(url);
    }
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if(getBitmap(url) == null){
            memoryCache.put(url, bitmap);
        }
    }
}
