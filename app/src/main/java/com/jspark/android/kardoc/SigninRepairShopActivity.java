package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jspark.android.kardoc.domain.Result;
import com.jspark.android.kardoc.domain.Shop;
import com.jspark.android.kardoc.server.ShopPost;
import com.jspark.android.kardoc.util.EditUtil;
import com.jspark.android.kardoc.util.SignUtil;
import com.jspark.android.kardoc.util.TextUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SigninRepairShopActivity extends AppCompatActivity {
    Button btnCancle;
    Button btnSignup;
    ShopPost shopPost = null;

    //
    EditText editFamily, editGiven, editPhone, editId, editPw, editPwcheck, editCompanyName, editCompanyAddress, editCompanyIntroduction;

    Spinner yearSpinner, monthSpinner, daySpinner;
    Spinner citySpinner, guSpinner;
    TextView alertBirth, alertId, alertPw, alertPwCheck;
    CheckBox personalInformation;
    RadioGroup genderGroup;

    String[] cityNames;
    String[] guNames;
    //

    private boolean leapYear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_repair_shop);

        btnSignup = (Button) findViewById(R.id.buttonSignupCompany);
        btnCancle = (Button) findViewById(R.id.buttonCancleCompany);

        cityNames = this.getResources().getStringArray(R.array.city);
        guNames = this.getResources().getStringArray(R.array.seoul_gu);

        // 레트로핏 연결
        setRetrofitInShop();

        setWidget();
        setBtnSignupCompany();
        setBtnCancle();

    }

    private void setRetrofitInShop() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-13-124-46-207.ap-northeast-2.compute.amazonaws.com:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        shopPost = retrofit.create(ShopPost.class);
    }

    private void setWidget() {
        editFamily = (EditText) findViewById(R.id.editFamilyName);
        editGiven = (EditText) findViewById(R.id.editGivenName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        yearSpinner = (Spinner) findViewById(R.id.editYear);
        monthSpinner = (Spinner) findViewById(R.id.editMonth);
        daySpinner = (Spinner) findViewById(R.id.editDay);
        citySpinner = (Spinner) findViewById(R.id.citySpinner);
        guSpinner = (Spinner) findViewById(R.id.guSpinner);
        guSpinner.setVisibility(Spinner.INVISIBLE);
        alertBirth = (TextView) findViewById(R.id.errorBirth);
        editId = (EditText) findViewById(R.id.editId);
        alertId = (TextView) findViewById(R.id.errorId);
        editPw = (EditText) findViewById(R.id.editPw);
        alertPw = (TextView) findViewById(R.id.errorPw);
        editPwcheck = (EditText) findViewById(R.id.editPwCheck);
        alertPwCheck = (TextView) findViewById(R.id.errorPwCheck);
        editCompanyName = (EditText) findViewById(R.id.editCompanyName);
        editCompanyAddress = (EditText) findViewById(R.id.editCompanyAddress);
        editCompanyIntroduction = (EditText) findViewById(R.id.editCompanyIntroduction);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        personalInformation = (CheckBox) findViewById(R.id.checkPrivate);
        // 위젯 선언


        // 전화번호에 하이픈 없이 달라고 함
        // 전화번호 하이픈 자동 입력
        //editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private void setBtnSignupCompany() {
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cityNames);
        ArrayAdapter<String> guAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, guNames);
        citySpinner.setAdapter(cityAdapter);
        guSpinner.setAdapter(guAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectCity = cityNames[position];
                if (selectCity.equals("서울특별시")) {

                    Toast.makeText(SigninRepairShopActivity.this, "선택 되었습니다.", Toast.LENGTH_SHORT).show();
                    guSpinner.setVisibility(Spinner.VISIBLE);

                } else {
                    Toast.makeText(SigninRepairShopActivity.this, "현재는 서울 특별시만 지원됩니다.", Toast.LENGTH_SHORT).show();
                    guSpinner.setVisibility(Spinner.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //생년월일 스피너
        String[] yearData = this.getResources().getStringArray(R.array.yearArray);
        String[] monthData = this.getResources().getStringArray(R.array.monthArray);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, yearData);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, monthData);

        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);
        // 윤년, 월을 기준으로 28, 29, 30, 31일 세팅
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Integer.parseInt((String) yearSpinner.getSelectedItem()) % 4 == 0) {
                    leapYear = true;
                } else {
                    leapYear = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int getMonth = Integer.parseInt((String) monthSpinner.getSelectedItem());
                ArrayAdapter<String> dayAdapter;
                if (getMonth == 2) {
                    if (!leapYear) {
                        String[] dayData = getResources().getStringArray(R.array.day28Array);
                        dayAdapter = new ArrayAdapter<>(SigninRepairShopActivity.this, android.R.layout.simple_spinner_dropdown_item, dayData);
                        daySpinner.setAdapter(dayAdapter);
                    } else {
                        String[] dayData = getResources().getStringArray(R.array.day29Array);
                        dayAdapter = new ArrayAdapter<>(SigninRepairShopActivity.this, android.R.layout.simple_spinner_dropdown_item, dayData);
                        daySpinner.setAdapter(dayAdapter);
                    }
                } else if (getMonth == 4 || getMonth == 6 || getMonth == 9 || getMonth == 11) {
                    String[] dayData = getResources().getStringArray(R.array.day30Array);
                    dayAdapter = new ArrayAdapter<>(SigninRepairShopActivity.this, android.R.layout.simple_spinner_dropdown_item, dayData);
                    daySpinner.setAdapter(dayAdapter);
                } else {
                    String[] dayData = getResources().getStringArray(R.array.day31Array);
                    dayAdapter = new ArrayAdapter<>(SigninRepairShopActivity.this, android.R.layout.simple_spinner_dropdown_item, dayData);
                    daySpinner.setAdapter(dayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSignup.setOnClickListener(v -> {
            RadioButton genderButton = (RadioButton) findViewById(genderGroup.getCheckedRadioButtonId());
            String ownerData = "";
            String shopName = "";
            boolean hasError = false;

            // 성, 이름 검사
            if(!("".equals(EditUtil.gTFE(editFamily)))) {
                if(!("".equals(EditUtil.gTFE(editGiven)))) {
                    ownerData = EditUtil.gTFE(editFamily)+EditUtil.gTFE(editGiven);
                    Log.w("Dialog Button Test", "Name : "+ownerData);
                } else {
                    hasError = true;
                    editGiven.requestFocus();
                    Toast.makeText(SigninRepairShopActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            } else {
                hasError = true;
                Toast.makeText(SigninRepairShopActivity.this, "성을 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            // 핸드폰 번호 검사
            if (EditUtil.gTFE(editPhone).length() == 11) {
                Log.w("Dialog Button Test", "폰 : " + EditUtil.gTFE(editPhone));
            } else {
                hasError = true;
                Toast.makeText(SigninRepairShopActivity.this, "폰 번호를 확인해주세요", Toast.LENGTH_SHORT).show();
            }

            // 성별 체크 검사
            if (genderButton != null) {
                Log.w("Dialog Button Test", "성별 : " + genderButton.getText().toString());
            } else {
                hasError = true;
            }


            // 이메일 형식 아이디 검사
            if (SignUtil.validateEmail(EditUtil.gTFE(editId))) {
                alertId.setVisibility(View.GONE);
                Log.w("Dialog Email Check", EditUtil.gTFE(editId));
            } else {
                hasError = true;
                alertId.setVisibility(View.VISIBLE);
                TextUtil.alertTextGone(alertId);
            }

            // 비밀번호 형식 검사
            if (SignUtil.validatePassword(EditUtil.gTFE(editPw))) {
                alertPw.setVisibility(View.GONE);
                Log.w("Dialog Password Check", EditUtil.gTFE(editPw));
            } else {
                hasError = true;
                alertPw.setVisibility(View.VISIBLE);
                TextUtil.alertTextGone(alertPw);
            }

            // 비밀번호 확인 검사
            if (SignUtil.checkTwoPasswords(EditUtil.gTFE(editPw), EditUtil.gTFE(editPwcheck))) {
                Log.w("Dialog Password Check", "Two Passwords are same");
            } else {
                hasError = true;
                alertPwCheck.setVisibility(View.VISIBLE);
                TextUtil.alertTextGone(alertPwCheck);
            }

            if (personalInformation.isChecked()) {
                Log.w("Dialog Button Test", "개인정보 : " + personalInformation.isChecked());
            } else {
                hasError = true;
                Toast.makeText(SigninRepairShopActivity.this, "개인 정보 수집 / 이용 내역에 동의해주세요", Toast.LENGTH_SHORT).show();
            }

            //상호명 검사
            if (!("".equals(EditUtil.gTFE(editCompanyName)))) {
                shopName = EditUtil.gTFE(editCompanyName);
                Log.w("Dialog Button Test", "회사명 : " + EditUtil.gTFE(editCompanyName));

            } else {
                hasError = true;
                Toast.makeText(SigninRepairShopActivity.this, "상호명을 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            //상호 주소 검사
            if (!("".equals(EditUtil.gTFE(editCompanyAddress)))) {
                Log.w("Dialog Button Test", "회사 주소 : " + EditUtil.gTFE(editCompanyAddress));

            } else {
                hasError = true;
                Toast.makeText(SigninRepairShopActivity.this, "회사 주소를 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            //상호 소개 검사
            if (!("".equals(EditUtil.gTFE(editCompanyIntroduction)))) {
                Log.w("Dialog Button Test", "회사 소개 : " + EditUtil.gTFE(editCompanyIntroduction));

            } else {
                hasError = true;
                Toast.makeText(SigninRepairShopActivity.this, "회사 소개를 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            if (!hasError) {
                // 서버로 회원정보 전송
                Shop shop = new Shop();
                shop.setOwner(ownerData);
                shop.setShopname(shopName);

                Call<Result> remoteData = shopPost.createShop(shop);

                remoteData.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.w("shopResult", response.toString());
                        if(response.code()==201&&response.body()!=null) {
                            Intent i = new Intent(SigninRepairShopActivity.this, LobbyActivity.class);
                            startActivity(i);
                            Toast.makeText(SigninRepairShopActivity.this, "전송 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });

            } else {

            }
        });
    }

    private void setBtnCancle() {
        btnCancle.setOnClickListener((v1) -> onBackPressed());
    }

}
