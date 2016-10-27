package com.happypiebinliu.happymatch.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.happypiebinliu.happymatch.Fragment.BaseFragment;
import com.happypiebinliu.happymatch.Fragment.MatchAddFragment;
import com.happypiebinliu.happymatch.Fragment.MatchCreateFragment;
import com.happypiebinliu.happymatch.Fragment.MatchFragment;
import com.happypiebinliu.happymatch.Fragment.MatchMoreFragment;
import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.common.ViewUtil;
import com.happypiebinliu.happymatch.view.TabItem;
import com.happypiebinliu.happymatch.view.TabLowLayout;

import java.util.ArrayList;

public class MainTopActivity extends AppCompatActivity implements TabLowLayout.OnTabClickListener {

    private TabLowLayout mTabLowLayout;
    private ArrayList<TabItem> tabs;
    private ActionBar actionBar;
    private ViewPager mViewPager;
    BaseFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_top);

        // ================Tab=============================
        // 底部Ｔａｂ栏的初始化
        mTabLowLayout = getViewInfo(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.main);
        }
        // Tab内容设定
        initData();
        // ================Tab=============================
    }

    /**
     *　底部ＴＡＢ栏的初始化
     */
    private void initData(){

        // selector 实现动态的ｔａｂ的颜色变换＊＊＊＊
        tabs = new ArrayList<>();
        // Me主画面Tap
        tabs.add(new TabItem(R.drawable.selector_tab_me, R.string.main, MatchFragment.class));
        // 添加图片tap
        tabs.add(new TabItem(R.drawable.selector_tab_add, R.string.add, MatchAddFragment.class));
        // 组合生成tap
        tabs.add(new TabItem(R.drawable.selector_tab_create, R.string.make_one, MatchCreateFragment.class));
        // 更多设置tap
        tabs.add(new TabItem(R.drawable.selector_tab_setting_more, R.string.setting_more, MatchMoreFragment.class));

        mTabLowLayout.initData(tabs, this);
        mTabLowLayout.setCurrentTab(0);

      FragAdapter adapter = new FragAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLowLayout.setCurrentTab(position);
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

    /***
     * findViewById common
     */
    public <E extends View> E getViewInfo(int resId) {
        return ViewUtil.findViewById(this, resId);
    }

    @Override
    public void onTabClick(TabItem tabItem) {

        actionBar.setTitle(tabItem.labelResId);
        mViewPager.setCurrentItem(tabs.indexOf(tabItem));

    }
    public class FragAdapter extends FragmentPagerAdapter {


        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            try {
                return tabs.get(arg0).tagFragmentClz.newInstance();

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
}