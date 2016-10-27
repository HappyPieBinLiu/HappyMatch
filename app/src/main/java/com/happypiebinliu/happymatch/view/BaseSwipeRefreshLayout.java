package com.happypiebinliu.happymatch.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.common.ViewUtil;

/**
 * Created by Administrator on 2016/10/23 0023.
 */

public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {
    public BaseSwipeRefreshLayout(Context context) {
        super(context);
        this.init();
    }

    public BaseSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.setProgressViewOffset(false, ViewUtil.dip2px(this.getContext(), -50.0F), ViewUtil.dip2px(this.getContext(), 30.0F));
        this.setColorSchemeColors(new int[]{this.getContext().getResources().getColor(R.color.tab_green)});
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return !this.isRefreshing() && super.onStartNestedScroll(child, target, nestedScrollAxes);
    }
}
