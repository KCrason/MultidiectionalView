package com.kcrason.multidiectionalviewlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.kcrason.multidiectionalviewlibrary.callback.OnCustomHorizontalScrollViewListener;

/**
 * 主要用于横向滑动的ScrollView
 *
 * @author KCrason
 * @date 2017/3/1
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {

    /**
     * 标识当前触摸的是哪个View
     */
    private CustomHorizontalScrollView mCurrentTouchView;

    private int mLastScrollX;

    private OnCustomHorizontalScrollViewListener mOnCustomHorizontalScrollViewListener;

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomHorizontalScrollView(Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mLastScrollX, 0);
    }

    public void setOnCustomHorizontalScrollViewListener(OnCustomHorizontalScrollViewListener onCustomHorizontalScrollViewListener) {
        this.mOnCustomHorizontalScrollViewListener = onCustomHorizontalScrollViewListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        setOnTouchViewCallBack();
        return super.onTouchEvent(e);
    }


    private void init(Context context) {
        if (context != null && context instanceof OnCustomHorizontalScrollViewListener) {
            this.mOnCustomHorizontalScrollViewListener = (OnCustomHorizontalScrollViewListener) context;
        }
    }


    public void setLastScrollX(int lastScrollX) {
        mLastScrollX = lastScrollX;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mCurrentTouchView == this) {
            if (mOnCustomHorizontalScrollViewListener != null) {
                mOnCustomHorizontalScrollViewListener.onCallBackScrollChanged(l, t);
            }
        } else {
            super.onScrollChanged(l, t, oldl, oldt);
        }
    }


    public void setOnTouchViewCallBack() {
        this.mCurrentTouchView = this;
        if (mOnCustomHorizontalScrollViewListener != null) {
            mOnCustomHorizontalScrollViewListener.onCallBackCurrentTouchView(this);
        }
    }
}
