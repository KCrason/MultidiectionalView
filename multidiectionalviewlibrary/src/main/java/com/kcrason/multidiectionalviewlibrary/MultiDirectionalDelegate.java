package com.kcrason.multidiectionalviewlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.kcrason.multidiectionalviewlibrary.callback.OnCustomHorizontalScrollViewListener;
import com.kcrason.multidiectionalviewlibrary.callback.OnVisibleImageFlagScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCrason
 * @date 2018/3/1
 */
public class MultiDirectionalDelegate implements OnCustomHorizontalScrollViewListener {
    /**
     * 用于存储相应的ScrollView，便于横向联动
     */
    private List<CustomHorizontalScrollView> mCustomHorizontalScrollViews = new ArrayList<>();
    /**
     * 最后滑动的位置
     */
    private int mLastScrollX;

    /**
     * 图片标识回调接口
     */
    private OnVisibleImageFlagScrollListener mOnVisibleImageFlagScrollListener;

    private CustomHorizontalScrollView mTouchCustomHorizontalScrollView;

    private Context mContext;

    @Override
    public void onCallBackCurrentTouchView(CustomHorizontalScrollView customHorizontalScrollView) {
        this.mTouchCustomHorizontalScrollView = customHorizontalScrollView;
    }

    public List<CustomHorizontalScrollView> getCustomHorizontalScrollViews() {
        return mCustomHorizontalScrollViews;
    }

    public MultiDirectionalDelegate(Context context) {
        this.mContext = context;
    }

    public static MultiDirectionalDelegate create(Context context) {
        return new MultiDirectionalDelegate(context);
    }

    public void addCustomHorizontalScrollView(CustomHorizontalScrollView customHorizontalScrollView) {
        customHorizontalScrollView.setOnCustomHorizontalScrollViewListener(this);
        mCustomHorizontalScrollViews.add(customHorizontalScrollView);
        //将当前滑动到的最后的位置传递给customHorizontalScrollView
        customHorizontalScrollView.setLastScrollX(mLastScrollX);
    }


    public View layoutIdToView(ViewGroup parent, int layoutId) {
        if (layoutId != 0) {
            return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        }
        return null;
    }

    /**
     * 设置滑动标识回调接口
     *
     * @param onVisibleImageFlagScrollListener
     */
    public void setOnImageFlagScrollListener(OnVisibleImageFlagScrollListener onVisibleImageFlagScrollListener) {
        mOnVisibleImageFlagScrollListener = onVisibleImageFlagScrollListener;
    }

    @Override
    public void onCallBackScrollChanged(int l, int t) {
        if (mCustomHorizontalScrollViews != null) {
            this.mLastScrollX = l;
            callBackScrollFlagStatus(l);
            for (CustomHorizontalScrollView scrollView : mCustomHorizontalScrollViews) {
                //防止重复滑动，并通知其他scrollview进行联动
                if (mTouchCustomHorizontalScrollView != scrollView) {
                    scrollView.smoothScrollTo(l, t);
                }
            }
        }
    }

    /**
     * 回调滑动标识状态
     *
     * @param scrollX ScrollView滑动的x距离
     */
    private void callBackScrollFlagStatus(int scrollX) {
        if (mOnVisibleImageFlagScrollListener != null) {
            if (mCustomHorizontalScrollViews.size() > 0) {
                CustomHorizontalScrollView customHorizontalScrollView = mCustomHorizontalScrollViews.get(0);
                if (customHorizontalScrollView.getChildCount() > 0) {
                    View firstView = customHorizontalScrollView.getChildAt(0);
                    if (firstView != null) {
                        if (firstView.getWidth() - mContext.getResources().getDisplayMetrics().widthPixels > scrollX) {
                            mOnVisibleImageFlagScrollListener.onVisibleImageFlag(View.VISIBLE);
                        } else {
                            mOnVisibleImageFlagScrollListener.onVisibleImageFlag(View.GONE);
                        }
                    }
                }
            }
        }
    }
}
