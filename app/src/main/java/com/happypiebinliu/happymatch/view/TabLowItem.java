package com.happypiebinliu.happymatch.view;

import com.happypiebinliu.happymatch.Fragment.BaseFragment;

/**
 * Tab 栏的对应　对象
 * Created by Bin.Liu on 2016/10/25.
 */

public class TabLowItem {

    // 图片
    public int imageResId;

    // 文本
    public int labelResId;

    // Tab 对应的Fragment
    public Class<? extends BaseFragment> tabFragmentClz;

    /**
     * Tab栏的初始化　实例化函数
     *
     * @param imageResId     　使用图片
     * @param labelResId     　显示文本
     * @param tabFragmentClz 　对应的Fragment
     */
    public TabLowItem(int imageResId, int labelResId, Class<? extends BaseFragment> tabFragmentClz) {

        this.imageResId = imageResId;
        this.labelResId = labelResId;
        this.tabFragmentClz = tabFragmentClz;
    }
}
