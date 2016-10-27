package com.happypiebinliu.happymatch.view;

import com.happypiebinliu.happymatch.Fragment.BaseFragment;

/**
 * Created by ope001 on 2016/10/25.
 */

public class TabItem {
    /**
     * icon
     */
    public int imageResId;
    /**
     * 文本
     */
    public int labelResId;
    public Class<? extends BaseFragment>tagFragmentClz;

    public TabItem(int imageResId, int labelResId) {
        this.imageResId = imageResId;
        this.labelResId = labelResId;
    }

    public TabItem(int imageResId, int labelResId, Class<? extends BaseFragment> tagFragmentClz) {
        this.imageResId = imageResId;
        this.labelResId = labelResId;
        this.tagFragmentClz = tagFragmentClz;
    }
}
