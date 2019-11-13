package ouyj.hyena.com.infonews.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.toolbox.ImageLoader;

/**
 * 自定义ImageCache类
 */
public class VolleyCache extends LruCache<String, Bitmap>
        implements ImageLoader.ImageCache {

    public VolleyCache(int maxSize) {
        super(maxSize);
    }
    public VolleyCache(Context ctx) {
        this(getCacheSize(ctx));
    }
    public static int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        final int screenBytes = screenWidth * screenHeight * 4;
        return screenBytes * 5;
    }
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
    /**
     * 从缓存中获取图片
     * @param url
     * @return
     */
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }
    /**
     * 将图片放入缓存中
     * @param url
     * @param bitmap
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}