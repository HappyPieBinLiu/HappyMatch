package com.happypiebinliu.happymatch.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.common.LogUtil;

import java.util.ArrayList;

import static com.happypiebinliu.happymatch.common.Consts.TabLowItemIsEmpty;

/**
 * Created by Bin.Liu on 2016/10/25.
 */

public class TabLowLayout extends LinearLayout implements View.OnClickListener {

    private final String TAG_CLASS = "TabLowLayout";
    private ArrayList<TabLowItem> tabs;
    private OnTabClickListener listener;
    private View selectView;
    private int tabCount;

    public TabLowLayout(Context context) {
        super(context);
        initView();
    }

    public TabLowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TabLowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
    }

    /**
     * 设定当前的Ｔａｂ
     *
     * @param i
     */
    public void updateSelectedTab(int i) {

        TextView textView;
        if (i < tabCount && i >= 0) {
            View view = getChildAt(i);
            if (selectView != view) {

                view.setSelected(true);

                // 文本的取得
                textView = (TextView) view.findViewById(R.id.tab_label);
                // 改变文本选中颜色
                textView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.tab_blue));

                // 原来被选中view的初始化返回
                if (selectView != null) {
                    selectView.setSelected(false);
                    textView = (TextView) selectView.findViewById(R.id.tab_label);
                    textView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.tab_gray));
                }
                // 更新被选中view
                selectView = view;
            }
        }
    }

    /**
     * 初始化
     * @param tabs
     * @param listener
     */
    public void initData(ArrayList<TabLowItem> tabs, OnTabClickListener listener) {

        this.tabs = tabs;
        this.listener = listener;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        if (tabs != null && tabs.size() > 0) {
            tabCount = tabs.size();
            TabLowView mTabView = null;
            for (int i = 0; i < tabs.size(); i++) {
                mTabView = new TabLowView(getContext());
                mTabView.setTag(tabs.get(i));
                mTabView.initData(tabs.get(i));
                mTabView.setOnClickListener(this);
                addView(mTabView, params);
            }

        } else {
            LogUtil.error(TAG_CLASS, TabLowItemIsEmpty);
            throw new IllegalArgumentException(TabLowItemIsEmpty);
        }

    }

    @Override
    public void onClick(View v) {

        listener.onTabClick((TabLowItem) v.getTag());

    }

    public interface OnTabClickListener {

        void onTabClick(TabLowItem tabLowItem);
    }
}
