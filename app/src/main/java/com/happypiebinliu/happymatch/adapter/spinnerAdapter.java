package com.happypiebinliu.happymatch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.happypiebinliu.happymatch.R;

/**
 * 修改系统自带Spinner 字体的颜色和大小
 * Created by liubin on 16/11/21.
 */

public class spinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private String[] mArrayString;

    /**
     * 构造函数
     * @param context
     * @param stringArray
     */
    public spinnerAdapter(Context context, String[] stringArray) {
        super(context, android.R.layout.simple_spinner_item, stringArray);
        mContext = context;
        mArrayString = stringArray;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //修改Spinner选择后结果的字体颜色
        if (convertView != null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);

        }

        // 此处text1是Spinner默认的用来显示文字的TextView
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(mArrayString[position]);
        textView.setTextSize(20f);
        textView.setTextColor(Color.WHITE);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //修改Spinner展开后的字体颜色
        if (convertView != null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        }
        // 此处text1是Spinner默认的用来显示文字的TextView
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(mArrayString[position]);
        textView.setTextSize(20f);
        textView.setTextColor(Color.BLACK);
        return convertView;
    }

}
