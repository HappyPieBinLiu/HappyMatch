package com.happypiebinliu.happymatch.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.common.ITabClickListener;

/**
 * Created by B.Liu on 2016/10/27.
 */

public class MatchAddFragment extends BaseFragment implements ITabClickListener{
    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_add_layout, container, false);
        return view;
    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }
}
