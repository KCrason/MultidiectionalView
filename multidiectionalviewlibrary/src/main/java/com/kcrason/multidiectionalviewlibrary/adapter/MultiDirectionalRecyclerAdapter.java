package com.kcrason.multidiectionalviewlibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.kcrason.multidiectionalviewlibrary.CustomHorizontalScrollView;
import com.kcrason.multidiectionalviewlibrary.MultiDirectionalDelegate;
import com.kcrason.multidiectionalviewlibrary.callback.MultiDirectional;
import com.kcrason.multidiectionalviewlibrary.callback.OnVisibleImageFlagScrollListener;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/2/28
 */
public abstract class MultiDirectionalRecyclerAdapter<T> extends RecyclerView.Adapter<MultiDirectionalRecyclerAdapter.MultiDirectionalViewHolder>
        implements MultiDirectional<MultiDirectionalRecyclerAdapter.MultiDirectionalViewHolder> {

    /**
     * 当前头部导航栏中的ScrollView，如果需要头部跟随移动，需要将其添加至list集合中。
     */
    private CustomHorizontalScrollView mStableHeaderIndicatorCustomHorizontalScrollView;

    /**
     * list数据源
     */
    private List<T> mListData;


    private MultiDirectionalDelegate mMultiDirectionalDelegate;


    public MultiDirectionalRecyclerAdapter(Context context) {
        mMultiDirectionalDelegate = MultiDirectionalDelegate.create(context);
    }

    public List<T> getListData() {
        return mListData;
    }


    /**
     * 设置数据源
     */
    public void setSourceData(List<T> listData) {
        updateSourceData(listData);
    }

    /**
     * 更新数据源
     *
     * @param listData
     */
    public void updateSourceData(List<T> listData) {
        if (mMultiDirectionalDelegate.getCustomHorizontalScrollViews() != null) {
            mMultiDirectionalDelegate.getCustomHorizontalScrollViews().clear();
            if (mStableHeaderIndicatorCustomHorizontalScrollView != null) {
                mMultiDirectionalDelegate.addCustomHorizontalScrollView(mStableHeaderIndicatorCustomHorizontalScrollView);
            }
        }
        this.mListData = listData;
        notifyDataSetChanged();
    }

    /**
     * 设置头部的导航ScrollView,主要目的是进行联动
     */
    public void addStableHeaderIndicatorCustomHorizontalScrollView(CustomHorizontalScrollView stableHeaderIndicatorCustomHorizontalScrollView) {
        this.mStableHeaderIndicatorCustomHorizontalScrollView = stableHeaderIndicatorCustomHorizontalScrollView;
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    @Override
    public MultiDirectionalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return generateStockMultiDirectionalViewHolder(parent);
    }

    /**
     * 创建ViewHolder
     */
    private MultiDirectionalViewHolder generateStockMultiDirectionalViewHolder(ViewGroup parent) {

        LinearLayout parentView = new LinearLayout(parent.getContext());
        parentView.setOrientation(LinearLayout.HORIZONTAL);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parentView.setLayoutParams(layoutParams);

        View itemStableHeaderView = layoutIdToView(parent, onCreateItemStableHeaderLayoutId());
        if (itemStableHeaderView != null) {
            parentView.addView(itemStableHeaderView);
        }

        CustomHorizontalScrollView customHorizontalScrollView = new CustomHorizontalScrollView(parent.getContext());
        customHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        customHorizontalScrollView.setLayoutParams(layoutParams1);

        View itemContentView = layoutIdToView(parent, onCreateItemContentViewLayoutId());
        if (itemContentView != null) {
            customHorizontalScrollView.addView(itemContentView);
        }

        parentView.addView(customHorizontalScrollView);

        return onCreateMultiDirectionalViewHolder(parentView, customHorizontalScrollView);
    }

    public void setOnVisibleImageFlagScrollListener(OnVisibleImageFlagScrollListener onVisibleImageFlagScrollListener) {
        mMultiDirectionalDelegate.setOnImageFlagScrollListener(onVisibleImageFlagScrollListener);
    }


    private View layoutIdToView(ViewGroup parent, int layoutId) {
        if (layoutId != 0) {
            return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MultiDirectionalViewHolder holder, int position) {
        onBindContentViewData(holder, position);
        onBindStableHeaderViewData(holder, position);
        mMultiDirectionalDelegate.addCustomHorizontalScrollView(holder.mCustomHorizontalScrollView);
    }


    public static class MultiDirectionalViewHolder extends RecyclerView.ViewHolder {

        private CustomHorizontalScrollView mCustomHorizontalScrollView;

        public MultiDirectionalViewHolder(View itemView, CustomHorizontalScrollView customHorizontalScrollView) {
            super(itemView);
            this.mCustomHorizontalScrollView = customHorizontalScrollView;
        }
    }
}
