package com.kcrason.multidiectionalviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kcrason.multidiectionalviewlibrary.adapter.MultiDirectionalBaseAdapter;
import com.kcrason.multidiectionalviewlibrary.adapter.MultiDirectionalRecyclerAdapter;
import com.kcrason.multidiectionalviewlibrary.callback.OnVisibleImageFlagScrollListener;

import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.CENTER_VERTICAL;

/**
 * @author KCrason
 * @date 2018/2/28
 */
public class MultiDirectionalView extends LinearLayout implements OnVisibleImageFlagScrollListener {

    private RecyclerView mRecyclerView;

    private CustomHorizontalScrollView mStableCustomHorizontalScrollView;

    private ImageView mImageFlagView;

    private boolean mIsShowScrollFlag;

    private View mStableHeaderIndicatorView;

    private ListView mListView;

    private int mListType;

    private final static int LIST_TYPE_RECYCLER_VIEW = 2;

    public MultiDirectionalView(Context context) {
        super(context);
        init(context, null);
    }

    public MultiDirectionalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MultiDirectionalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StockMultidirectionalView);
        if (typedArray != null) {
            mIsShowScrollFlag = typedArray.getBoolean(R.styleable.StockMultidirectionalView_isShowScrollFlag, false);
            int stableHeaderIndicatorLayout = typedArray.getResourceId(R.styleable.StockMultidirectionalView_stableHeaderIndicatorLayout, 0);
            mListType = typedArray.getInt(R.styleable.StockMultidirectionalView_listType, LIST_TYPE_RECYCLER_VIEW);

            int  resourceId = typedArray.getResourceId(R.styleable.StockMultidirectionalView_scrollFlagDrawable,0);

            if (stableHeaderIndicatorLayout != 0) {
                mStableHeaderIndicatorView = LayoutInflater.from(context).inflate(stableHeaderIndicatorLayout, this, false);
                mStableCustomHorizontalScrollView = findStableHeaderIndicatorCustomHorizontalScrollView(mStableHeaderIndicatorView);
                addView(mStableHeaderIndicatorView);
            }

            RelativeLayout relativeLayout = new RelativeLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(layoutParams);
            addView(relativeLayout);


            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

            if (mListType == LIST_TYPE_RECYCLER_VIEW) {
                mRecyclerView = new RecyclerView(context);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                relativeLayout.addView(mRecyclerView, layoutParams1);
            } else {
                mListView = new ListView(context);
                relativeLayout.addView(mListView, layoutParams1);
            }

            if (mIsShowScrollFlag) {
                mImageFlagView = new ImageView(context);
                if (resourceId != 0) {
                    mImageFlagView.setImageResource(resourceId);
                }
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams2.addRule(CENTER_VERTICAL);
                layoutParams2.addRule(ALIGN_PARENT_RIGHT);
                mImageFlagView.setLayoutParams(layoutParams2);
                relativeLayout.addView(mImageFlagView);
            }

            typedArray.recycle();
        }
    }


    /**
     * 获取固定的头部View
     *
     * @return
     */
    public View getStableHeaderIndicatorView() {
        return mStableHeaderIndicatorView;
    }

    /**
     * 找到头部固定导航栏中的CustomHorizontalScrollView
     *
     * @param stableHeaderIndicatorView 头部固定导航栏
     * @return
     */
    private CustomHorizontalScrollView findStableHeaderIndicatorCustomHorizontalScrollView(View stableHeaderIndicatorView) {
        if (stableHeaderIndicatorView instanceof ViewGroup) {
            int childCount = ((ViewGroup) stableHeaderIndicatorView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = ((ViewGroup) stableHeaderIndicatorView).getChildAt(i);
                if (childView instanceof CustomHorizontalScrollView) {
                    return (CustomHorizontalScrollView) childView;
                }
            }
        }
        return null;
    }


    /**
     * 给RecyclerView控件设置adapter
     *
     * @param adapter
     */
    public void setRecyclerViewAdapter(MultiDirectionalRecyclerAdapter adapter) {
        if (mListType != LIST_TYPE_RECYCLER_VIEW) {
            throw new RuntimeException("ListType must be RecyclerView");
        }
        if (adapter == null) {
            throw new RuntimeException("StockMultidirectionalAdapter may be is null!");
        }
        if (mIsShowScrollFlag) {
            adapter.setOnVisibleImageFlagScrollListener(this);
        }
        if (mStableHeaderIndicatorView != null) {
            if (mStableCustomHorizontalScrollView == null) {
                throw new RuntimeException("StableCustomHorizontalScrollView may be is null! " +
                        "Please set CustomHorizontalScrollView in StableHeaderIndicatorLayout");
            }
            adapter.addStableHeaderIndicatorCustomHorizontalScrollView(mStableCustomHorizontalScrollView);
        }
        mRecyclerView.setAdapter(adapter);
    }


    /**
     * 给RecyclerView控件设置adapter
     *
     * @param adapter
     */
    public void setListViewAdapter(MultiDirectionalBaseAdapter adapter) {
        if (mListType == LIST_TYPE_RECYCLER_VIEW) {
            throw new RuntimeException("ListType must be ListView");
        }
        if (adapter == null) {
            throw new RuntimeException("StockMultidirectionalAdapter may be is null!");
        }
        if (mIsShowScrollFlag) {
            adapter.setOnVisibleImageFlagScrollListener(this);
        }
        if (mStableHeaderIndicatorView != null) {
            if (mStableCustomHorizontalScrollView == null) {
                throw new RuntimeException("StableCustomHorizontalScrollView may be is null! " +
                        "Please set CustomHorizontalScrollView in StableHeaderIndicatorLayout");
            }
            adapter.addStableHeaderIndicatorCustomHorizontalScrollView(mStableCustomHorizontalScrollView);
        }
        mListView.setAdapter(adapter);
    }

    @Override
    public void onVisibleImageFlag(int visible) {
        if (mImageFlagView != null) {
            mImageFlagView.setVisibility(visible);
        }
    }
}
