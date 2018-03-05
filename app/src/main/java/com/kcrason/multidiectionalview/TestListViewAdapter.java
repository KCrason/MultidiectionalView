package com.kcrason.multidiectionalview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kcrason.multidiectionalviewlibrary.CustomHorizontalScrollView;
import com.kcrason.multidiectionalviewlibrary.adapter.MultiDirectionalBaseAdapter;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/3/3
 */
public class TestListViewAdapter extends MultiDirectionalBaseAdapter<DataBean> {


    public TestListViewAdapter(Context context) {
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
        return new TestListViewHolder(itemView, customHorizontalScrollView);
    }


    @Override
    public void onBindContentViewData(MultiDirectionalViewHolder holder, int position) {
        if (holder instanceof TestListViewHolder) {
            ((TestListViewHolder) holder).text1.setText(String.valueOf(Math.random() * 10));
            ((TestListViewHolder) holder).text2.setText(String.valueOf(Math.random() * 20));
            ((TestListViewHolder) holder).text3.setText(String.valueOf(Math.random() * 30));
            ((TestListViewHolder) holder).text4.setText(String.valueOf(Math.random() * 40));
            ((TestListViewHolder) holder).text5.setText(String.valueOf(Math.random() * 50));
        }
    }

    @Override
    public void onBindStableHeaderViewData(MultiDirectionalViewHolder holder, int position) {
        if (holder instanceof TestListViewHolder) {
            List<DataBean> dataBeans = getListData();
            if (dataBeans != null && position < dataBeans.size()) {
                ((TestListViewHolder) holder).text.setText(dataBeans.get(position).getName());
            }
        }
    }

    class TestListViewHolder extends MultiDirectionalBaseAdapter.MultiDirectionalViewHolder {

        private TextView text, text1, text2, text3, text4, text5;

        public TestListViewHolder(View itemView, CustomHorizontalScrollView customHorizontalScrollView) {
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
