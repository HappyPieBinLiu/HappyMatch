package com.happypiebinliu.happymatch.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.common.ViewUtil;
import com.happypiebinliu.happymatch.view.TabLowItem;
import com.happypiebinliu.happymatch.view.TabLowLayout;
import com.happypiebinliu.happymatch.view.TakeTurnsView;

import java.util.ArrayList;
import java.util.List;

public class MainTopActivity extends AppCompatActivity implements View.OnClickListener, TabLowLayout.OnTabClickListener {

    private TakeTurnsView takeTurnsViewTop;
    private TakeTurnsView takeTurnsViewLow;
    private TabLowLayout mTabLowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题
        this.getSupportActionBar().hide();
        setContentView(R.layout.main_top);

        // 上衣轮番图
        takeTurnsViewTop = getViewInfo(R.id.take_turns_view_top);
        // 下衣轮番图
        takeTurnsViewLow = getViewInfo(R.id.take_turns_view_low);
        // 底部Ｔａｂ栏的初始化
        mTabLowLayout=(TabLowLayout)findViewById(R.id.tab_layout);
        initData();
        takeTurnsViewTop.setTakeTurnsHeight(300);
        drawables.add(getResources().getDrawable(R.drawable.i1));
        drawables.add(getResources().getDrawable(R.drawable.i2));
        takeTurnsViewTop.setImageUrls(drawables);
        takeTurnsViewLow.setImageUrls(drawables);

        takeTurnsViewTop.setUpdateUI(new TakeTurnsView.UpdateUI() {
            @Override
            public void onUpdateUI(int position) {
                Log.i("test", "当前图片位置：" + position);
            }

            @Override
            public void onItemClick(int position, ImageView imageView) {
                Toast.makeText(MainTopActivity.this, "当前图片位置：" + position + "  id:" + imageView.getId(), Toast.LENGTH_LONG).show();
            }
        });
    }

    List<Drawable> drawables = new ArrayList<>();

    private void removeDrawables() {
        drawables.remove(0);
    }

    /**
     *　底部ＴＡＢ栏的初始化
     */
    private void initData(){

        ArrayList<TabLowItem>tabs=new ArrayList<TabLowItem>();
        // 添加上衣图片按钮
        tabs.add(new TabLowItem(R.drawable.hometopbtn,R.string.add_top));
        // 添加下衣服衣图片按钮
        tabs.add(new TabLowItem(R.drawable.homelowbtn,R.string.add_low));
        // 组合生成按钮
        tabs.add(new TabLowItem(R.drawable.topandlow,R.string.make_one));
        // Me自我设定按钮
        tabs.add(new TabLowItem(R.drawable.me,R.string.settings));

        mTabLowLayout.initData(tabs, this);

    }

    /**
     * 各个按钮的事件响应
     * @param view
     */
    @Override
    public void onClick(View view) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        takeTurnsViewTop.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /***
     * findViewById common
     */
    public <E extends View> E getViewInfo(int resId) {
        return ViewUtil.findViewById(this, resId);
    }

    @Override
    public void onTabClick(TabLowItem tabItem) {

    }
}