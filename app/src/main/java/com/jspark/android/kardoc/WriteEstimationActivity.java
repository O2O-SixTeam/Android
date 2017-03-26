package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Songmoo on 2017-03-26.
 */

public class WriteEstimationActivity extends AppCompatActivity {

    LinearLayout linear;
    Spinner spinnerOrg;
    Button btnAdd, btnFin, btnDel;

    TextView textResult; //스피너의 값

    List<Spinner> spinners = new ArrayList<>();
    List<String> data = new ArrayList<>();
    ArrayAdapter<String> adapter;

    final int viewPreId = 5350;
    int viewNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_estimation);



        for(int i=1;i<6;i++) {
            data.add("list "+i);
        }

        linear = (LinearLayout)findViewById(R.id.linearLayout);
        spinnerOrg = (Spinner)findViewById(R.id.spinnerOriginal);
        spinners.add(spinnerOrg);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnFin = (Button)findViewById(R.id.btnFinish);
        btnDel = (Button)findViewById(R.id.btnDelete);
        textResult = (TextView)findViewById(R.id.textResult);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);

        spinnerOrg.setAdapter(adapter);

        setButtonAdd();
        setButtonFinish();
        setButtonDelete();

    }

    private void setButtonAdd() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewNum < 2) {
                    viewNum++;
                    Spinner newSpin = new Spinner(WriteEstimationActivity.this);
                    newSpin.setId(viewPreId + viewNum);
                    newSpin.setAdapter(adapter);
                    linear.addView(newSpin, new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                    spinners.add(newSpin);
                }else{
                    Toast.makeText(WriteEstimationActivity.this, "더이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setButtonFinish() {
        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result ="";

                for(int i=0; i<spinners.size(); i++){
                    result += (spinners.get(i)).getSelectedItem().toString();
                    if(i !=spinners.size()-1){
                        result += "/";
                    }
                }
                textResult.setText(result);

//                //TODO modify Inetent
//                Intent i = new Intent(WriteEstimationActivity.this, WriteEstimationActivity.class);
//                i.putExtra("count", viewNum);
//                i.putExtra("resultOrg", spinnerOrg.getSelectedItem().toString());
//                if(viewNum >0){
//                    for(int j = 1; j< viewNum +1; j++) {
//                        Spinner getSpinner = (Spinner)findViewById(viewPreId +j);
//                        i.putExtra("result"+j, getSpinner.getSelectedItem().toString());
//                    }
//                }
//                startActivity(i);
            }
        });
    }

    private void setButtonDelete() {
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewNum !=0) {
                    Spinner removeSpinner = (Spinner) findViewById(viewPreId + viewNum);
                    linear.removeView(removeSpinner);
                    viewNum--;
                }
            }
        });
    }
}