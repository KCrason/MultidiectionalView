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
public class RecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        MultiDirectionalView multiDirectionalView = findViewById(R.id.multi_directional_view);
        final TestRecyclerViewAdapter testRecyclerViewAdapter = new TestRecyclerViewAdapter(this);
        multiDirectionalView.setRecyclerViewAdapter(testRecyclerViewAdapter);
        testRecyclerViewAdapter.setSourceData(createDataBeans());
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRecyclerViewAdapter.updateSourceData(createDataBeans());
            }
        });
    }

    private List<DataBean> createDataBeans() {
        List<DataBean> dataBeans = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            DataBean dataBean = new DataBean();
            dataBeans.add(dataBean);
        }
        return dataBeans;
    }
}
