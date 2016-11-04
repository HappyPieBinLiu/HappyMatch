package com.happypiebinliu.happymatch.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.adapter.MatchMeAdapter;
import com.happypiebinliu.happymatch.common.ITabClickListener;
import com.happypiebinliu.happymatch.view.BaseSwipeRefreshLayout;
import com.happypiebinliu.happymatch.view.TakeTurnsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 16/4/3.
 */
public class MatchFragment extends BaseFragment implements ITabClickListener {

    private BaseSwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mListView;
    private LinearLayoutManager layoutManager;
    private MatchMeAdapter adapter;
    private TakeTurnsView takeTurnsViewTop;
    private TakeTurnsView takeTurnsViewLow;
    private List<Drawable> drawables = new ArrayList<>();

    @Override
    public void fetchData() {
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_me_layout, container, false);
        mSwipeRefreshLayout = (BaseSwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        //mListView = (RecyclerView) view.findViewById(R.id.list);
        this.layoutManager = new LinearLayoutManager(this.getContext());
        this.layoutManager.setOrientation(1);
        //this.mListView.setLayoutManager(this.layoutManager);
        //adapter = new MatchMeAdapter();
        //mListView.setAdapter(adapter);

        // ================轮番图=============================
        // 上衣轮番图
        takeTurnsViewTop = (TakeTurnsView) view.findViewById(R.id.take_turns_view_top);
        takeTurnsViewTop.setTakeTurnsHeight(300);
        drawables.add(getResources().getDrawable(R.drawable.top_1));
        drawables.add(getResources().getDrawable(R.drawable.top_2));
        takeTurnsViewTop.setImageUrls(drawables);
        drawables.clear();

        // 下衣轮番图
        takeTurnsViewLow = (TakeTurnsView) view.findViewById(R.id.take_turns_view_low);
        takeTurnsViewLow.setTakeTurnsHeight(300);
        drawables.add(getResources().getDrawable(R.drawable.low_1));
        drawables.add(getResources().getDrawable(R.drawable.low_2));
        takeTurnsViewLow.setImageUrls(drawables);
        drawables.clear();

        takeTurnsViewTop.setUpdateUI(new TakeTurnsView.UpdateUI() {
            @Override
            public void onUpdateUI(int position) {

                Log.i("test", "当前图片位置：" + position);
            }

            @Override
            public void onItemClick(int position, ImageView imageView) {
            }
        });
        // ================轮番图=============================
        return view;
    }

    private void getData() {
        //模拟子线程耗时操作
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onPause() {
        super.onPause();
        takeTurnsViewTop.onPause();
        takeTurnsViewLow.onPause();
    }
}
