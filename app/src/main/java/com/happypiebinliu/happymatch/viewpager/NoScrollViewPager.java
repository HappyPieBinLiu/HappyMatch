package com.happypiebinliu.happymatch.viewpager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.happypiebinliu.happymatch.adapter.TakeTurnsAdapter;
import com.happypiebinliu.happymatch.common.Consts;

/**
 * 传入onScroll 控制当前 viewpager
 */
public class NoScrollViewPager extends ViewPager {

    //false可以滑动；true则不能滑动
    private boolean noScroll = false;

    //自动轮番的消息机制
    private Handler handler;


    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        //如果不能轮番，则直接将touch事件向上传递
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        //如果不能轮番，则直接将touch事件向上传递
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //如果不能轮番，则自动轮番的消息不再发送
        if (noScroll)
            return super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            Consts.Contant.isRun = false;//手指按下，则不能自动轮番
            Consts.Contant.isDown = true;//按下状态为true
            if (handler != null)
                handler.removeCallbacksAndMessages(null);//清空消息队列
        } else if (action == MotionEvent.ACTION_UP) {
            Consts.Contant.isRun = true;//手指抬起，自动轮番开始
            Consts.Contant.isDown = false;//按下状态为false
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);//清空消息队列
                handler.sendEmptyMessage(1);//重新发送轮番的指令
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setInfinateAdapter(Handler handler, TakeTurnsAdapter adapter) {
        this.handler = handler;
        setAdapter(adapter);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}
