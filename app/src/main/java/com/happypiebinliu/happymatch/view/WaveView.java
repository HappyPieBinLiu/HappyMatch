package com.happypiebinliu.happymatch.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.R.attr.action;

public class WaveView extends LinearLayout {

	private View mTargetTouchView;
	private Paint mHalfTransPaint;
	private Paint mTransPaint;
	private float[] mDownPosition;
	private float rawRadius;
	private float drawedRadius;
	private float drawingRadiusDegrees = 10;
	private static final long INVALID_DURATION = 30;
	private static postUpEventDelayed delayedRunnable;

	public void init() {
		setOrientation(VERTICAL);
		mHalfTransPaint = new Paint();
		mHalfTransPaint.setColor(Color.parseColor("#55ffffff"));
		mHalfTransPaint.setAntiAlias(true);
		mTransPaint = new Paint();
		mTransPaint.setColor(Color.parseColor("#00ffffff"));
		mTransPaint.setAntiAlias(true);
		mDownPosition = new float[2];
		delayedRunnable = new postUpEventDelayed();
	}

	/***
	 * 我们在这里进行拦截单击事件
	 * @param ev
	 * @return
     */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			mTargetTouchView = null;
			drawedRadius = 0;
			// 得到屏幕相对坐标
			float x = ev.getRawX();
			float y = ev.getRawY();
			mTargetTouchView = findTargetView(x, y, this);
			if(mTargetTouchView!=null){
				Button msg = (Button) mTargetTouchView;
				RectF targetTouchRectF = getViewRectF(mTargetTouchView);
				mDownPosition = getCircleCenterPosition(x, y);

				float circleCenterX = mDownPosition[0];
				float circleCenterY = mDownPosition[1];
				float left = circleCenterX - targetTouchRectF.left;
				float right = targetTouchRectF.right - circleCenterX;
				float top = circleCenterY - targetTouchRectF.top;
				float bottom = targetTouchRectF.bottom - circleCenterY;

				rawRadius = Math.max(bottom, Math.max(Math.max(left, right), top));
				postInvalidateDelayed(INVALID_DURATION);
			}
		}else if (ev.getAction() == MotionEvent.ACTION_UP) {

			delayedRunnable.event = ev;
			// 延迟　增加水波效果
			postDelayed(delayedRunnable, 400);
			return true;

		} else if (action == MotionEvent.ACTION_CANCEL) {

			postInvalidateDelayed(INVALID_DURATION);
		}
		return super.dispatchTouchEvent(ev);
	}

	/***
	 * 延迟处理
	 */
	class postUpEventDelayed implements Runnable{
		private MotionEvent event;
		@Override
		public void run() {
			if(mTargetTouchView!=null && mTargetTouchView.isClickable()
					&& event!=null){
				mTargetTouchView.dispatchTouchEvent(event);
			}
		}
	}

	/***
	 * 绘制波纹
	 * @param canvas
     */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);

		if (mTargetTouchView != null) {
			RectF clipRectF = clipRectF(mTargetTouchView);
			canvas.save();

			canvas.clipRect(clipRectF);
			if(drawedRadius < rawRadius){
				drawedRadius += rawRadius / drawingRadiusDegrees;
				canvas.drawCircle(mDownPosition[0], mDownPosition[1], drawedRadius, mHalfTransPaint);
				postInvalidateDelayed(INVALID_DURATION);
			}else{
				canvas.drawCircle(mDownPosition[0], mDownPosition[1], rawRadius, mTransPaint);
				post(delayedRunnable);
			}
			canvas.restore();
		}
	}

	/***
	 *  获取中心坐标
	 * @param x
	 * @param y
     * @return
     */
	public float[] getCircleCenterPosition(float x,float y){
		int[] location = new int[2];
		float[] mDownPosition = new float[2];
		getLocationOnScreen(location );
		mDownPosition[0] = x;
		mDownPosition[1] = y -location[1];
		return mDownPosition;
	}
 
	/**
	 *
	 * @param mTargetTouchView
	 * @return
	 */
	public RectF clipRectF(View mTargetTouchView){
		RectF clipRectF = getViewRectF(mTargetTouchView);
		int[] location = new int[2];
		getLocationOnScreen(location);
		clipRectF.top -= location[1];
		clipRectF.bottom -=  location[1];
		return clipRectF;
	}
	
	/**
	 * 遍历 view 找到我们点击的view
	 * 落在屏幕范围内的view
	 * @param x
	 * @param y
	 * @param anchorView
	 * @return
	 */
	public View findTargetView(float x, float y, View anchorView) {
		ArrayList<View> touchablesView = anchorView.getTouchables();
		View targetView = null;
		for (View child : touchablesView) {
			RectF rectF = getViewRectF(child);
			if (rectF.contains(x, y) && child.isClickable()) {
				targetView = child;
				break;
			}
		}
		return targetView;
	}

	/***
	 * 得到被点击View的信息
	 * @param view
	 * @return
     */
	public RectF getViewRectF(View view) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int childLeft = location[0];
		int childTop = location[1];
		int childRight = childLeft + view.getMeasuredWidth();
		int childBottom = childTop + view.getMeasuredHeight();
		return new RectF(childLeft, childTop, childRight, childBottom);
	}

	public WaveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public WaveView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WaveView(Context context) {
		this(context, null, 0);
	}

}
