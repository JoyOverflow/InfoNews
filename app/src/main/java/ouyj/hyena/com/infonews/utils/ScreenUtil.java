package ouyj.hyena.com.infonews.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ScreenUtil {
    //屏幕信息类
    static DisplayMetrics dm = new DisplayMetrics();
    private static void setDisplayMetrics(Context context) {
        dm = context.getResources().getDisplayMetrics();
    }
    /**
     * 屏幕的密度
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        setDisplayMetrics(context);
        return (int)(dm.density);
    }
    /**
     * 屏幕的宽和高（像素）
     * @param app
     * @return
     */
    public static int getWidth(Application app) {
        return dm.widthPixels;
    }
    public static int getHeight(Application app) {
        return dm.heightPixels;
    }




    /**
     * 将dp转换为像素px
     * @param activity
     * @param dp
     * @return
     */
    public static int dp2px(Activity activity, float dp)
    {
        setDisplayMetrics(activity);
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                activity.getResources().getDisplayMetrics()
        );
    }



}

