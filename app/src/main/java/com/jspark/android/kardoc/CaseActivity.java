package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);

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
        }
    }
}