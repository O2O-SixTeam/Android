package com.jspark.android.kardoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jspark.android.kardoc.server.ApiServices;
import com.jspark.android.kardoc.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jspark.android.kardoc.SignActivity.MY_TOKEN;

/**
 * Created by Songmoo on 2017-03-26.
 */

public class RequestEstimationActivity extends AppCompatActivity {

    public static final int BRAND_CODE = 200;

    LinearLayout linear;
    Spinner spinnerOrg;
    TextView textResult; //스피너의 값

    Button btnAdd, btnFin, btnDel;
    Button btnBrand, btnCallEstimation, btnWarranty;

    EditText carNameField, carNumberField, carVinField, phoneNumberField;

    CheckBox insuranceCheckBox, rentcarCheckBox, pickupCheckBox, agreementCheckBox;

    List<Spinner> spinners = new ArrayList<>();
    String[] partsData;
    ArrayAdapter<String> adapter;

    final int viewPreId = 5350;
    int viewNum = 0;

    ApiServices apiServices = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_estimation);

        setWidgets();

        setRetrofit();

        setSpinnerOrg();

        setButtonAdd();
        setButtonFinish();
        setButtonDelete();

        btnBrand.setOnClickListener(listener);
        btnWarranty.setOnClickListener(listener);
        setBtnCallEstimation();

    }

    private void setRetrofit() {
        RetrofitUtil retrofit = RetrofitUtil.getInstance();
        apiServices = retrofit.getApiServices();
    }

    private void setSpinnerOrg() {
        partsData = getResources().getStringArray(R.array.parts);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, partsData);
        spinnerOrg.setAdapter(adapter);
    }

    View.OnClickListener listener = v -> {
        Intent intent;
        switch (v.getId()){
            case R.id.btnBrend:
                intent = new Intent(RequestEstimationActivity.this, BrandActivity.class );
                startActivityForResult(intent, BRAND_CODE);
                break;
            case R.id.btnWarranty:
                intent = new Intent(RequestEstimationActivity.this,WarrentyActivity.class);
                startActivity(intent);
                break;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==BRAND_CODE&&data!=null) {
            btnBrand.setText(data.getStringExtra("result"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setWidgets() {
        linear = (LinearLayout)findViewById(R.id.linearLayout);
        spinnerOrg = (Spinner)findViewById(R.id.spinnerOriginal);
        spinners.add(spinnerOrg);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnFin = (Button)findViewById(R.id.btnFinish);
        btnDel = (Button)findViewById(R.id.btnDelete);
        carNameField = (EditText)findViewById(R.id.editCarName);
        carNumberField = (EditText)findViewById(R.id.editCarNum);
        carVinField = (EditText)findViewById(R.id.editVinNum);
        phoneNumberField = (EditText)findViewById(R.id.editPhoneNumber);
        btnBrand =  (Button)findViewById(R.id.btnBrend);
        btnCallEstimation = (Button)findViewById(R.id.btnCallEstimation);
        btnWarranty = (Button)findViewById(R.id.btnWarranty);
        textResult = (TextView)findViewById(R.id.textResult);
        insuranceCheckBox = (CheckBox)findViewById(R.id.cbFixInsurance);
        rentcarCheckBox = (CheckBox)findViewById(R.id.cbRentCar);
        pickupCheckBox = (CheckBox)findViewById(R.id.cbPickupService);
        agreementCheckBox = (CheckBox)findViewById(R.id.cbAgreement);
    }

    private void setBtnCallEstimation() {
        btnCallEstimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean hasError = false;

                String brandData = "";
                String modelData = "";
                String carnumberData = "";
                String broken1Data = "";
                String broken2Data = "";
                String broken3Data = "";
                String detailData = "";
                String numberData = "";
                Boolean completedData = false;
                Boolean insurancerepairData = false;
                Boolean rentcarData = false;
                Boolean pickupData = false;
                String caridData = "";

                // 매칭 완료 false
                completedData = Boolean.FALSE;

                if(viewNum==0) {
                    broken1Data = ((spinners.get(0)).getSelectedItem().toString());
                } else if(viewNum==1) {
                    broken1Data = (spinners.get(0)).getSelectedItem().toString();
                    broken2Data = ((spinners.get(1)).getSelectedItem().toString());
                } else if(viewNum==2) {
                    broken1Data = (spinners.get(0)).getSelectedItem().toString();
                    broken2Data = ((spinners.get(1)).getSelectedItem().toString());
                    broken3Data = ((spinners.get(2)).getSelectedItem().toString());
                }

                // 브랜드 선택 유무 확인
                if(!btnBrand.getText().toString().contains("*")) {
                    brandData = (btnBrand.getText().toString());
                } else {
                    hasError = true;
                    Toast.makeText(RequestEstimationActivity.this, "차량 제조사를 선택해주세요", Toast.LENGTH_SHORT).show();
                }

                // 차량명 / 연식 입력 확인
                if(!("".equals(carNameField.getText().toString()))) {
                    modelData = (carNameField.getText().toString());
                } else {
                    hasError = true;
                    Toast.makeText(RequestEstimationActivity.this, "차량명과 연식을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                // 차량번호 입력 확인 (선택 사항이므로 에러표기 안함)
                if(!("".equals(carNumberField.getText().toString()))) {
                    carnumberData = (carNumberField.getText().toString());
                }

                // 추가 서비스 확인
                if(insuranceCheckBox.isChecked()) {
                    insurancerepairData = Boolean.TRUE;
                } else {
                    insurancerepairData = Boolean.FALSE;
                }
                if(rentcarCheckBox.isChecked()) {
                    rentcarData = Boolean.TRUE;
                } else {
                    rentcarData = Boolean.FALSE;
                }
                if(pickupCheckBox.isChecked()) {
                    pickupData = Boolean.TRUE;
                } else {
                    pickupData = Boolean.FALSE;
                }
//                insurancerepairData = insuranceCheckBox.isChecked();
//                rentcarData = rentcarCheckBox.isChecked();
//                pickupData = pickupCheckBox.isChecked();

                // 차대번호 확인
                if(!("".equals(carVinField.getText().toString()))) {
                    caridData = (carVinField.getText().toString());
                }

                // 연락처 확인
                if(!("".equals(phoneNumberField.getText().toString()))) {
                    numberData = (phoneNumberField.getText().toString());
                } else {
                    hasError = true;
                    Toast.makeText(RequestEstimationActivity.this, "연락처를 확인해주세요", Toast.LENGTH_SHORT).show();
                }

                // 개인정보 취급 방침 체크 확인
                if(!agreementCheckBox.isChecked()) {
                    hasError = true;
                    Toast.makeText(RequestEstimationActivity.this, "개인정보 취급 방침에 동의해주세요", Toast.LENGTH_SHORT).show();
                }

                // 오류 없을 시
                if(!hasError) {
                    SharedPreferences sharedPreferences = getSharedPreferences(MY_TOKEN, MODE_PRIVATE);
                    String token = sharedPreferences.getString(MY_TOKEN, null);
                    Log.w("gettoken", token);

                    Call<ResponseBody> requestEstimation = apiServices.request("Token "+token, brandData, modelData,carnumberData,broken1Data,broken2Data,broken3Data,detailData,numberData,completedData,insurancerepairData,rentcarData,pickupData,caridData);
                    requestEstimation.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.w("response", response.toString());
                            if(response.code()==201) {
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void setButtonAdd() {
        btnAdd.setOnClickListener(v -> {
            if(viewNum < 2) {
                viewNum++;
                Spinner newSpin = new Spinner(RequestEstimationActivity.this);
                newSpin.setId(viewPreId + viewNum);
                newSpin.setAdapter(adapter);
                linear.addView(newSpin, new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                spinners.add(newSpin);
            }else{
                Toast.makeText(RequestEstimationActivity.this, "더이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setButtonFinish() {
        btnFin.setOnClickListener(v -> {
            String result ="";

            for(int i=0; i<spinners.size(); i++){
                result += (spinners.get(i)).getSelectedItem().toString();
                if(i !=spinners.size()-1){
                    result += "/";
                }
            }
            textResult.setText(result);
        });
    }

    private void setButtonDelete() {
        btnDel.setOnClickListener(v -> {
            if(viewNum !=0) {
                Spinner removeSpinner = (Spinner) findViewById(viewPreId + viewNum);
                spinners.remove(spinners.indexOf(removeSpinner));
                linear.removeView(removeSpinner);
                viewNum--;
            }
        });
    }
}