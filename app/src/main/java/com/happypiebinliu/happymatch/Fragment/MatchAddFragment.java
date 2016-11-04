package com.happypiebinliu.happymatch.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.common.ITabClickListener;

/**
 * Created by B.Liu on 2016/10/27.
 */

public class MatchAddFragment extends BaseFragment implements ITabClickListener, View.OnClickListener {
    View view ;
    private Button selectBtn;
    private Button uploadBtn;
    private Button changeBtn;
    private Button addLineBtn;

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_add_layout, container, false);

        selectBtn = (Button) view.findViewById(R.id.btnSelectTop);
        uploadBtn = (Button) view.findViewById(R.id.btnUploadTop);
        changeBtn = (Button) view.findViewById(R.id.btnChangeTop);
        addLineBtn = (Button) view.findViewById(R.id.addLineTopBtn);
        selectBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
        changeBtn.setOnClickListener(this);
        addLineBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnSelectTop:
                break;
            case R.id.btnUploadTop:
                break;
            case R.id.btnChangeTop:
                break;
            case R.id.addLineTopBtn:
                break;
            default:
                break;
        }
    }
}
