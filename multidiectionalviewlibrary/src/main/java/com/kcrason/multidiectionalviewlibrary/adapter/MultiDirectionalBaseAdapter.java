package com.kcrason.multidiectionalviewlibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


import com.kcrason.multidiectionalviewlibrary.CustomHorizontalScrollView;
import com.kcrason.multidiectionalviewlibrary.MultiDirectionalDelegate;
import com.kcrason.multidiectionalviewlibrary.callback.MultiDirectional;
import com.kcrason.multidiectionalviewlibrary.callback.OnCustomHorizontalScrollViewListener;
import com.kcrason.multidiectionalviewlibrary.callback.OnVisibleImageFlagScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCrason
 * @date 2018/3/1
 */
public abstract class MultiDirectionalBaseAdapter<T> extends BaseAdapter implements
        MultiDirectional<MultiDirectionalBaseAdapter.MultiDirectionalViewHolder> {


    /**
     * 当前头部导航栏中的ScrollView，如果需要头部跟随移动，需要将其添加至list集合中。
     */
    private CustomHorizontalScrollView mStableHeaderIndicatorCustomHorizontalScrollView;

    /**
     * list数据源
     */
    private List<T> mListData;



    private MultiDirectionalDelegate mMultiDirectionalDelegate;


    public MultiDirectionalBaseAdapter(Context context) {
        mMultiDirectionalDelegate = MultiDirectionalDelegate.create(context);
    }

    public List<T> getListData() {
        return mListData;
    }


    /**
     * 设置数据源
     *
     * @param listData
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
     *
     * @param stableHeaderIndicatorCustomHorizontalScrollView
     */
    public void addStableHeaderIndicatorCustomHorizontalScrollView(CustomHorizontalScrollView stableHeaderIndicatorCustomHorizontalScrollView) {
        this.mStableHeaderIndicatorCustomHorizontalScrollView = stableHeaderIndicatorCustomHorizontalScrollView;
    }


    private View layoutIdToView(ViewGroup parent, int layoutId) {
        if (layoutId != 0) {
            return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mListData == null ? 0 : mListData.size();
    }

    @Override
    public T getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MultiDirectionalViewHolder multiDirectionalViewHolder;
        if (convertView == null) {
            convertView = new LinearLayout(parent.getContext());
            ((LinearLayout) convertView).setOrientation(LinearLayout.HORIZONTAL);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(layoutParams);

            View itemStableHeaderView = layoutIdToView(parent, onCreateItemStableHeaderLayoutId());
            if (itemStableHeaderView != null) {
                ((LinearLayout) convertView).addView(itemStableHeaderView);
            }

            CustomHorizontalScrollView customHorizontalScrollView = new CustomHorizontalScrollView(parent.getContext());
            customHorizontalScrollView.setHorizontalScrollBarEnabled(false);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            customHorizontalScrollView.setLayoutParams(layoutParams1);

            View itemContentView = layoutIdToView(parent, onCreateItemContentViewLayoutId());
            if (itemContentView != null) {
                customHorizontalScrollView.addView(itemContentView);
            }
            ((LinearLayout) convertView).addView(customHorizontalScrollView);
            multiDirectionalViewHolder = onCreateMultiDirectionalViewHolder(convertView, customHorizontalScrollView);
            convertView.setTag(multiDirectionalViewHolder);
        } else {
            multiDirectionalViewHolder = (MultiDirectionalViewHolder) convertView.getTag();
        }
        onBindContentViewData(multiDirectionalViewHolder, position);
        onBindStableHeaderViewData(multiDirectionalViewHolder, position);
        mMultiDirectionalDelegate.addCustomHorizontalScrollView(multiDirectionalViewHolder.customHorizontalScrollView);
        return convertView;
    }


    public void setOnVisibleImageFlagScrollListener(OnVisibleImageFlagScrollListener onVisibleImageFlagScrollListener) {
        mMultiDirectionalDelegate.setOnImageFlagScrollListener(onVisibleImageFlagScrollListener);
    }

    public static class MultiDirectionalViewHolder {

        private CustomHorizontalScrollView customHorizontalScrollView;

        private View itemView;

        public MultiDirectionalViewHolder(View itemView, CustomHorizontalScrollView customHorizontalScrollView) {
            this.itemView = itemView;
            this.customHorizontalScrollView = customHorizontalScrollView;
        }
    }
}
