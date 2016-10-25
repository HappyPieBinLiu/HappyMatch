package com.happypiebinliu.happymatch.common;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by B.Liu on 2016/10/20.
 */

public class ViewUtil {

    /**
     * 防止被实例化
     */
    private ViewUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * findViewById 的函数改进
     * 减少cast的次数
     */
    public static <E extends View> E findViewById(Activity activity, int resId) {
        return (E) activity.findViewById(resId);
    }

    public static <E extends View> E findViewById(View view, int resId) {
        return (E) view.findViewById(resId);
    }

}
