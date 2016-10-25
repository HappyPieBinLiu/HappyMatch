package com.happypiebinliu.happymatch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by ope001 on 2016/10/25.
 */

public class TabLowLayout extends LinearLayout implements View.OnClickListener {

    private ArrayList<TabLowItem> tabs;
    private OnTabClickListener listener;

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

    private void initView(){
        setOrientation(HORIZONTAL);
    }
    public void initData(ArrayList<TabLowItem>tabs, OnTabClickListener listener){
        this.tabs = tabs;
        this.listener = listener;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight=1;
        if (tabs != null && tabs.size() > 0){
            TabLowView mTabView=null;
            for(int i = 0; i < tabs.size(); i++){
                mTabView = new TabLowView(getContext());
                mTabView.setTag(tabs.get(i));
                mTabView.initData(tabs.get(i));
                mTabView.setOnClickListener(this);
                addView(mTabView,params);
            }

        }else{

            throw new IllegalArgumentException("tabs can not be empty");
        }
    }
    @Override
    public void onClick(View view) {

    }
    public interface OnTabClickListener{

        void onTabClick(TabLowItem tabItem);
    }
}
