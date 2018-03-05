package com.kcrason.multidiectionalviewlibrary.callback;

import android.view.View;

import com.kcrason.multidiectionalviewlibrary.CustomHorizontalScrollView;

/**
 * @author KCrason
 * @date 2018/3/1
 * 外部接口
 */
public interface MultiDirectional<T> {

    /**
     * 外部创建固定的头部View,如果无固定头部，返回0
     *
     * @return
     */
    int onCreateItemStableHeaderLayoutId();


    /**
     * 外部创建可水平滑动View的内容View，一般为LinearLayout包裹
     *
     * @return
     */
    int onCreateItemContentViewLayoutId();


    /**
     * 创建ViewHolder
     *
     * @return
     */
   T onCreateMultiDirectionalViewHolder(View itemView, CustomHorizontalScrollView customHorizontalScrollView);

    /**
     * 绑定内容数据
     *
     */
    void onBindContentViewData(T holder, int position);

    /**
     * 绑定头部数据。
     *
     */
    void onBindStableHeaderViewData(T holder, int position);

}
