package ouyj.hyena.com.infonews.utils;

import android.app.Activity;
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

