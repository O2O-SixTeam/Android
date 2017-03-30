package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jspark.android.kardoc.util.EditUtil;
import com.jspark.android.kardoc.util.SignUtil;
import com.jspark.android.kardoc.util.TextUtil;

public class SigninRepairShopActivity extends AppCompatActivity {
    Button btnCancle;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_repair_shop);

        btnSignup = (Button)findViewById(R.id.buttonSignupCompany);
        btnCancle = (Button)findViewById(R.id.buttonCancleCompany);

        setBtnSignupCompany();
        setBtnCancle();

    }
    private void setBtnSignupCompany() {
        btnSignup.setOnClickListener(v -> {
            EditText editFamily, editGiven, editPhone, editId, editPw, editPwcheck, editCompanyName, editCompanyAddress, editCompanyIntroduction;
            TextView alertId, alertPw, alertPwCheck;
            CheckBox personalInformation;

            // 위젯 선언
            editFamily = (EditText)findViewById(R.id.editFamilyName);
            editGiven = (EditText)findViewById(R.id.editGivenName);
            editPhone = (EditText)findViewById(R.id.editPhone);
            editId = (EditText)findViewById(R.id.editId);
            alertId = (TextView)findViewById(R.id.errorId);
            editPw = (EditText)findViewById(R.id.editPw);
            alertPw = (TextView)findViewById(R.id.errorPw);
            editPwcheck = (EditText)findViewById(R.id.editPwCheck);
            alertPwCheck = (TextView)findViewById(R.id.errorPwCheck);
            editCompanyName = (EditText)findViewById(R.id.editCompanyName);
            editCompanyAddress = (EditText)findViewById(R.id.editCompanyAddress);
            editCompanyIntroduction =(EditText)findViewById(R.id.editCompanyIntroduction);
            personalInformation = (CheckBox)findViewById(R.id.checkPrivate);

            // 전화번호 하이픈 자동 입
            editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

            // 버튼 리스너
            btnSignup.setOnClickListener((v2)-> {

                boolean hasError = false;

                // 성, 이름 검사
                if(!("".equals(EditUtil.gTFE(editFamily)))) {
                    Log.w("Dialog Button Test", "성 : "+EditUtil.gTFE(editFamily));
                    if(!("".equals(EditUtil.gTFE(editGiven)))) {
                        Log.w("Dialog Button Test", "이름 : "+EditUtil.gTFE(editGiven));
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
                if(EditUtil.gTFE(editPhone).length()==13) {
                    Log.w("Dialog Button Test", "폰 : " + EditUtil.gTFE(editPhone));
                } else {
                    hasError = true;
                    Toast.makeText(SigninRepairShopActivity.this, "폰 번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                }

                // 이메일 형식 아이디 검사
                if(SignUtil.validateEmail(EditUtil.gTFE(editId))) {
                    alertId.setVisibility(View.GONE);
                    Log.w("Dialog Email Check", EditUtil.gTFE(editId));
                } else {
                    hasError = true;
                    alertId.setVisibility(View.VISIBLE);
                    TextUtil.alertTextGone(alertId);
                }

                // 비밀번호 형식 검사
                if(SignUtil.validatePassword(EditUtil.gTFE(editPw))) {
                    alertPw.setVisibility(View.GONE);
                    Log.w("Dialog Password Check", EditUtil.gTFE(editPw));
                } else {
                    hasError = true;
                    alertPw.setVisibility(View.VISIBLE);
                    TextUtil.alertTextGone(alertPw);
                }

                // 비밀번호 확인 검사
                if(SignUtil.checkTwoPasswords(EditUtil.gTFE(editPw), EditUtil.gTFE(editPwcheck))) {
                    Log.w("Dialog Password Check", "Two Passwords are same");
                } else {
                    hasError = true;
                    alertPwCheck.setVisibility(View.VISIBLE);
                    TextUtil.alertTextGone(alertPwCheck);
                }

                //상호명 검사
                if(!("".equals(EditUtil.gTFE(editCompanyName)))) {
                    Log.w("Dialog Button Test", "회사명 : "+EditUtil.gTFE(editCompanyName));

                } else {
                    hasError = true;
                    Toast.makeText(SigninRepairShopActivity.this, "상호명을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                //상호 주소 검사
                if(!("".equals(EditUtil.gTFE(editCompanyAddress)))) {
                    Log.w("Dialog Button Test", "회사 주소 : "+EditUtil.gTFE(editCompanyAddress));

                } else {
                    hasError = true;
                    Toast.makeText(SigninRepairShopActivity.this, "회사 주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                //상호 소개 검사
                if(!("".equals(EditUtil.gTFE(editCompanyIntroduction)))) {
                    Log.w("Dialog Button Test", "회사 소개 : "+EditUtil.gTFE(editCompanyIntroduction));

                } else {
                    hasError = true;
                    Toast.makeText(SigninRepairShopActivity.this, "회사 소개를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                //개인정보 수집 동의
                if(personalInformation.isChecked()) {
                    Log.w("Dialog Button Test", "개인정보 : "+personalInformation.isChecked());
                } else {
                    hasError  = true;
                    Toast.makeText(SigninRepairShopActivity.this, "개인 정보 수집 / 이용 내역에 동의해주세요", Toast.LENGTH_SHORT).show();
                }


                if(!hasError) {
                    // 서버로 회원정보 전송
                    Intent i  = new Intent(SigninRepairShopActivity.this, LobbyActivity.class);
                    startActivity(i);
                    Toast.makeText(SigninRepairShopActivity.this, "전송 성공", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                }
            });
        });
    }
    private void setBtnCancle() {
        btnCancle.setOnClickListener((v1) -> onBackPressed());
    }

}
