package ouyj.hyena.com.infonews;

import android.app.Application;

import ouyj.hyena.com.infonews.utils.ScreenUtil;

/**
 *
 */
public class MyApplication extends Application {

    public static int width = 0;
    public static int height = 0;
    public static float density = 0;

    public void onCreate() {
        super.onCreate();

        //得到屏幕的宽高和密度
        width = ScreenUtil.getWidth(this);
        height = ScreenUtil.getHeight(this);
        density = ScreenUtil.getDensity(this);
    }
}