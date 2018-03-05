package com.kcrason.multidiectionalviewlibrary.callback;

import com.kcrason.multidiectionalviewlibrary.CustomHorizontalScrollView;

/**
 * @author KCrason
 * @date 2018/3/3
 */
public interface OnCustomHorizontalScrollViewListener {
    /**
     * 回调当前滑动的是哪个view
     */
    void onCallBackCurrentTouchView(CustomHorizontalScrollView customHorizontalScrollView);

    /**
     * 回调当前view滑动的具体相关数据。
     */

    void onCallBackScrollChanged(int l, int t);
}
