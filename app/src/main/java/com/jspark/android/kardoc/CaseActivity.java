package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.jspark.android.kardoc.domain.Estimation;
import com.jspark.android.kardoc.server.ApiServices;
import com.jspark.android.kardoc.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

public class CaseActivity extends AppCompatActivity {

    ApiServices apiServices = null;
    RecyclerView lists;
    List<Estimation> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);

        setRetrofit();
        setCase();
        setRecyclerView();
    }

    private void setRetrofit() {
        RetrofitUtil retrofit = RetrofitUtil.getInstance();
        apiServices = retrofit.getApiServices();
    }

    private void setCase() {
        Intent i = getIntent();
        String caseBy = i.getStringExtra("case");
        switch (caseBy) {
            case "fender" :

                break;
            case "bumper" :

                break;
            case "audi" :

                break;
            case "benz" :

                break;
            case "bmw" :

                break;
            case "all" :

                break;
        }
    }

    private void setRecyclerView() {

    }
}