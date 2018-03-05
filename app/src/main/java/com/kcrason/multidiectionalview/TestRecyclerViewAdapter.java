package com.kcrason.multidiectionalview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kcrason.multidiectionalviewlibrary.CustomHorizontalScrollView;
import com.kcrason.multidiectionalviewlibrary.adapter.MultiDirectionalRecyclerAdapter;

/**
 * @author KCrason
 * @date 2018/3/3
 */
public class TestRecyclerViewAdapter extends MultiDirectionalRecyclerAdapter<DataBean> {

    public TestRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateItemStableHeaderLayoutId() {
        return R.layout.stock_multidirection_header_stable_view;
    }

    @Override
    public int onCreateItemContentViewLayoutId() {
        return R.layout.stock_multidirection_content_view;
    }

    @Override
    public MultiDirectionalViewHolder onCreateMultiDirectionalViewHolder(View itemView, CustomHorizontalScrollView customHorizontalScrollView) {
        return new TestRecyclerViewHolder(itemView, customHorizontalScrollView);
    }

    @Override
    public void onBindContentViewData(MultiDirectionalViewHolder holder, int position) {
        if (holder instanceof TestRecyclerViewHolder) {
            ((TestRecyclerViewHolder) holder).text1.setText(String.valueOf(Math.random() * 10));
            ((TestRecyclerViewHolder) holder).text2.setText(String.valueOf(Math.random() * 20));
            ((TestRecyclerViewHolder) holder).text3.setText(String.valueOf(Math.random() * 30));
            ((TestRecyclerViewHolder) holder).text4.setText(String.valueOf(Math.random() * 40));
            ((TestRecyclerViewHolder) holder).text5.setText(String.valueOf(Math.random() * 50));
        }
    }

    @Override
    public void onBindStableHeaderViewData(MultiDirectionalViewHolder holder, int position) {
        if (holder instanceof TestRecyclerViewHolder) {
            ((TestRecyclerViewHolder) holder).text.setText("深证成指");
        }
    }

    class TestRecyclerViewHolder extends MultiDirectionalRecyclerAdapter.MultiDirectionalViewHolder {

        private TextView text, text1, text2, text3, text4, text5;

        public TestRecyclerViewHolder(View itemView, CustomHorizontalScrollView customHorizontalScrollView) {
            super(itemView, customHorizontalScrollView);
            text = itemView.findViewById(R.id.txt);
            text1 = itemView.findViewById(R.id.txt1);
            text2 = itemView.findViewById(R.id.txt2);
            text3 = itemView.findViewById(R.id.txt3);
            text4 = itemView.findViewById(R.id.txt4);
            text5 = itemView.findViewById(R.id.txt5);
        }
    }

}
