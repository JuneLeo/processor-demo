package com.example.songpengfei.myapplication.abtest.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cleanmaster.mguard.R;
import com.example.songpengfei.myapplication.abtest.ABTest;

public class ABTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abtest);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(ABTestActivity.this));

        ABTestAdapter ABTestAdapter = new ABTestAdapter(ABTestActivity.this);
        recyclerView.setAdapter(ABTestAdapter);
        ABTestAdapter.addAll(ABTest.getDefault().getABTestModels(ABTestActivity.class.getName()));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
