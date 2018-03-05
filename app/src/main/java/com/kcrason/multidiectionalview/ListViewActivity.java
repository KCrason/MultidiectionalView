package com.kcrason.multidiectionalview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kcrason.multidiectionalviewlibrary.MultiDirectionalView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCrason
 * @date 2018/3/5
 */
public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        MultiDirectionalView multiDirectionalView = findViewById(R.id.multi_directional_view);
        final TestListViewAdapter testListViewAdapter = new TestListViewAdapter(this);
        multiDirectionalView.setListViewAdapter(testListViewAdapter);
        testListViewAdapter.setSourceData(createDataBeans());

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testListViewAdapter.updateSourceData(createDataBeans());
            }
        });
    }

    private List<DataBean> createDataBeans() {
        List<DataBean> dataBeans = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            DataBean dataBean = new DataBean();
            dataBean.setName("StockName" + i);
            dataBeans.add(dataBean);
        }
        return dataBeans;
    }
}
