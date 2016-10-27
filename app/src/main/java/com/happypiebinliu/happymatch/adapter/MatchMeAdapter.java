package com.happypiebinliu.happymatch.adapter;

import com.happypiebinliu.happymatch.R;

/**
 * Created by ope001 on 2016/10/27.
 */

public class MatchMeAdapter extends BaseRecyclerViewAdapter {

    public MatchMeAdapter() {
        super(R.layout.tab_me_turns);
    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
        var1.getTextView(R.id.contact).setText("Match");
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
