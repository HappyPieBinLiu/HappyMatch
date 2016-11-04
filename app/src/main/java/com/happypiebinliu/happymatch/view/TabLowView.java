package com.happypiebinliu.happymatch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.happypiebinliu.happymatch.R;

/**
 * document your custom view class.
 *
 * @author Bin.Liu
 */
public class TabLowView extends LinearLayout implements View.OnClickListener {

    private ImageView mTabImage;
    private TextView mTabLabel;
    private Context context;

    public TabLowView(Context context) {
        super(context);
        initView(context);
    }

    public TabLowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabLowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * Tab 的每一个按钮
     *
     * @param context
     */
    private void initView(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.tab_low, this, true);
        mTabImage = (ImageView) findViewById(R.id.tab_image);
        mTabLabel = (TextView) findViewById(R.id.tab_label);
    }

    /***
     * 每一个按钮内容的赋值
     *
     * @param tabLowItem
     */
    public void initData(TabLowItem tabLowItem) {

        mTabImage.setImageResource(tabLowItem.imageResId);
        mTabLabel.setText(tabLowItem.labelResId);
    }

    @Override
    public void onClick(View view) {
    }
}

