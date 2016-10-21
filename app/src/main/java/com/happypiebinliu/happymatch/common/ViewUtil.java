package com.happypiebinliu.happymatch.common;

import android.app.Activity;
import android.view.View;

/**
 * Created by B.Liu on 2016/10/20.
 */

public class ViewUtil {

    public static <E extends View> E findViewById(Activity activity, int resId) {
        return (E) activity.findViewById(resId);
    }

    public static <E extends View> E findViewById(View view, int resId) {
        return (E) view.findViewById(resId);
    }

}
