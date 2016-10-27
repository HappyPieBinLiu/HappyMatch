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
 */
public class TabLowView extends LinearLayout implements View.OnClickListener{

    private ImageView mTabImage;
    private TextView mTabLabel;

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

    private void initView(Context context){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.tab_low,this,true);
        mTabImage=(ImageView)findViewById(R.id.tab_image);
        mTabLabel=(TextView)findViewById(R.id.tab_label);

    }

    public void initData(TabItem tabItem){

        mTabImage.setImageResource(tabItem.imageResId);
        mTabLabel.setText(tabItem.labelResId);
    }


    @Override
    public void onClick(View view) {

    }
    public void onDataChanged(int badgeCount) {
        //  TODO notify new message, change the badgeView
    }
}
