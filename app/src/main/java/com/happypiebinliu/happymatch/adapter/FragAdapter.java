package com.happypiebinliu.happymatch.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.happypiebinliu.happymatch.Fragment.BaseFragment;
import com.happypiebinliu.happymatch.view.TabLowItem;

import java.util.ArrayList;

/**
 * Created by Bin.Liu on 2016/10/28.
 * 处理底部Tab栏的Fragment 碎片适配器
 */

public class FragAdapter extends FragmentPagerAdapter {

    private ArrayList<TabLowItem> tabs;
    private BaseFragment fragment;

    /**
     * 构造函数　重新塑造
     *
     * @param fm   　　FragmentManager的取得方便后面fragment的增删改
     * @param tabs 　本项目的特别Ｔａｂ　ｌｉｓｔ（TabLowItem）
     */
    public FragAdapter(FragmentManager fm, ArrayList<TabLowItem> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return tabs.get(position).tabFragmentClz.newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
