package com.happypiebinliu.happymatch.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Bin.Liu on 2016/10/27.
 * 利用了V7官方推荐的RecyclerView控件，考虑快速和灵活性
 * 因为是个新手，不知道原来的ListView的感觉，这个用起来也不错的感觉，而且官方推荐
 * RecyclerView的适配器
 * 也是为Tab来服务的
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter {

    private int mResourceId;
    private BaseRecyclerViewAdapter.OnItemClickListener mItemClickListener;
    private BaseRecyclerViewAdapter.OnItemLongClickListener mItemLongClickListener;

    public BaseRecyclerViewAdapter(int resourceId) {
        this.mResourceId = resourceId;
    }

    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(this.mResourceId, parent, false);
        return new BaseRecyclerViewAdapter.ViewHolder(itemView);
    }

    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder holder, final int position) {
        if (this.mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    BaseRecyclerViewAdapter.this.mItemClickListener.onItemClick(v, position);
                }
            });
        }

        if (this.mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    BaseRecyclerViewAdapter.this.mItemLongClickListener.onItemLongClick(v, position);
                    return false;
                }
            });
        }

        this.onBindViewData((BaseRecyclerViewAdapter.ViewHolder) holder, position);
    }

    public Object getItemData(int position) {
        return null;
    }

    public BaseRecyclerViewAdapter.OnItemClickListener getItemClickListener() {
        return this.mItemClickListener;
    }

    public void setItemClickListener(BaseRecyclerViewAdapter.OnItemClickListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    public BaseRecyclerViewAdapter.OnItemLongClickListener getItemLongClickListener() {
        return this.mItemLongClickListener;
    }

    public void setItemLongClickListener(BaseRecyclerViewAdapter.OnItemLongClickListener longClickListener) {
        this.mItemLongClickListener = longClickListener;
    }

    public abstract void onBindViewData(BaseRecyclerViewAdapter.ViewHolder var1, int var2);

    public interface OnItemLongClickListener {
        void onItemLongClick(View var1, int var2);
    }

    public interface OnItemClickListener {
        void onItemClick(View var1, int var2);
    }

    // ---------------------------ViewHolder--------------------------------------------------------

    /**
     *
     */
    public static final class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        private SparseArray<View> viewArray;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public <V extends View> V getView(int id) {
            return (V) this.findView(id);
        }

        public TextView getTextView(int id) {
            return (TextView) this.getView(id);
        }

        public Button getButton(int id) {
            return (Button) this.getView(id);
        }

        public ImageView getImageView(int id) {
            return (ImageView) this.getView(id);
        }

        public RelativeLayout getRelativeLayout(int id) {
            return (RelativeLayout) this.getView(id);
        }

        private View findView(int id) {
            if (this.viewArray == null) {
                this.viewArray = new SparseArray();
            }

            View view = (View) this.viewArray.get(id);
            if (view == null) {
                view = this.itemView.findViewById(id);
                this.viewArray.put(id, view);
            }

            return view;
        }
    }
    // ---------------------------ViewHolder--------------------------------------------------------
}

