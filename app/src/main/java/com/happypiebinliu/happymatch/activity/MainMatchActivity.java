package com.happypiebinliu.happymatch.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.happypiebinliu.happymatch.Fragment.MatchAddFragment;
import com.happypiebinliu.happymatch.Fragment.MatchCreateFragment;
import com.happypiebinliu.happymatch.Fragment.MatchFragment;
import com.happypiebinliu.happymatch.Fragment.MatchMoreFragment;
import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.adapter.FragAdapter;
import com.happypiebinliu.happymatch.common.ViewUtil;
import com.happypiebinliu.happymatch.view.TabLowItem;
import com.happypiebinliu.happymatch.view.TabLowLayout;

import java.util.ArrayList;

public class MainMatchActivity extends AppCompatActivity implements TabLowLayout.OnTabClickListener  {

    private final String TAG_CLASS = "MainMatchActivity";
    private TabLowLayout mTabLowLayout;
    private ArrayList<TabLowItem> tabs;
    private ActionBar actionBar;
    private ViewPager mViewPager;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_match_layout);
        // 系统标题栏的取得
        actionBar = getSupportActionBar();

        // ================Tab=============================
        // 底部Ｔａｂ栏的初始化
        mTabLowLayout = getViewInfo(R.id.tab_layout);
        mViewPager = getViewInfo(R.id.viewpager);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.main);
        }
        // Tab内容设定
        initData();
        // ================Tab=============================
    }

    /**
     * 　底部ＴＡＢ栏的初始化
     */
    private void initData() {

        // selector 实现动态的ｔａｂ的颜色变换＊＊＊＊
        // 并且加载了Fragment来实现Dynamic experience
        // 这里也是为了适应用户的用户体验 大众化的一种尝试

        tabs = new ArrayList<>();
        // Me主画面Tap
        tabs.add(new TabLowItem(R.drawable.selector_tab_me, R.string.main, MatchFragment.class));
        // 添加图片tap
        tabs.add(new TabLowItem(R.drawable.selector_tab_add, R.string.add, MatchAddFragment.class));
        // 组合生成tap
        tabs.add(new TabLowItem(R.drawable.selector_tab_create, R.string.make_one, MatchCreateFragment.class));
        // 更多设置tap
        tabs.add(new TabLowItem(R.drawable.selector_tab_setting_more, R.string.setting_more, MatchMoreFragment.class));

        mTabLowLayout.initData(tabs, this);
        mTabLowLayout.updateSelectedTab(0);

        // Fragment 的适配器初始化
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), tabs);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        // Viewpager　的监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLowLayout.updateSelectedTab(position);
                actionBar.setTitle(tabs.get(position).labelResId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTabClick(TabLowItem tabLowItem) {

        actionBar.setTitle(tabLowItem.labelResId);
        mViewPager.setCurrentItem(tabs.indexOf(tabLowItem));
    }

    /***
     * findViewById 的改进版本
     *
     * @param resId　resource ID
     * @param <E>    对应范型
     * @return　　　对应的组件
     */
    public <E extends View> E getViewInfo(int resId) {
        return ViewUtil.findViewById(this, resId);
    }

}