package ouyj.hyena.com.infonews.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class VolleySingle {
    private static VolleySingle instance;
    private RequestQueue queue;
    private ImageLoader imgLoader;
    private static Context context;

    /**
     * 返回单例
     * @param c
     * @return
     */
    public static synchronized VolleySingle getInstance(Context c) {
        if (instance == null) {
            instance = new VolleySingle(c);
        }
        return instance;
    }
    /**
     * 构造方法
     * @param c
     */
    private VolleySingle(Context c) {
        context = c;
        queue = getRequestQueue();

        //使用ImageLoader实现图片加载
        imgLoader = new ImageLoader(
                queue,
                new VolleyCache(context),
                1 * 3600 * 1000,
                15 * 24 * 3600 * 1000,
                true
        );
    }
    /**
     * 返回请求队列的对象（设置30M缓存）
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(
                    context.getApplicationContext(),
                    null,
                    30 * 1024 * 1024
            );
        }
        return queue;
    }





    public ImageLoader getImageLoader() {
        return imgLoader;
    }




}