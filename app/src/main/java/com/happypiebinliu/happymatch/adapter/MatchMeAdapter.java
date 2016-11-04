package com.happypiebinliu.happymatch.adapter;

import com.happypiebinliu.happymatch.R;

/**
 * Created by Bin.Liu on 2016/10/27.
 * Tab-Match 的
 */

public class MatchMeAdapter extends BaseRecyclerViewAdapter {

    public MatchMeAdapter() {
        super(R.layout.tab_match_turns);
    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
       // var1.getTextView(R.id.contact).setText("Match");
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
