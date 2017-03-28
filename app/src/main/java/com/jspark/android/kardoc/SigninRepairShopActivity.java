package com.jspark.android.kardoc;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

        setBtnSignup();
        setBtnSignin();

    }
    private void setBtnSignup() {
        btnSignup.setOnClickListener(v -> {
            EditText editFamily, editGiven, editPhone, editBirthYear, editBirthMonth, editBirthDay, editId, editPw, editPwcheck;
            TextView alertBirth, alertId, alertPw, alertPwCheck;
            RadioGroup genderGroup;
            CheckBox personalInformation;


            // 위젯 선언
            editFamily = (EditText)findViewById(R.id.editFamilyName);
            editGiven = (EditText)findViewById(R.id.editGivenName);
            editPhone = (EditText)findViewById(R.id.editPhone);
            editBirthYear = (EditText)findViewById(R.id.editYear);
            editBirthMonth = (EditText)findViewById(R.id.editMonth);
            editBirthDay = (EditText)findViewById(R.id.editDay);
            alertBirth = (TextView)findViewById(R.id.errorBirth);
            editId = (EditText)findViewById(R.id.editId);
            alertId = (TextView)findViewById(R.id.errorId);
            editPw = (EditText)findViewById(R.id.editPw);
            alertPw = (TextView)findViewById(R.id.errorPw);
            editPwcheck = (EditText)findViewById(R.id.editPwCheck);
            alertPwCheck = (TextView)findViewById(R.id.errorPwCheck);
            genderGroup = (RadioGroup)findViewById(R.id.genderGroup);
            personalInformation = (CheckBox)findViewById(R.id.checkPrivate);


            // 전화번호 하이픈 자동 입
            editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

            // 버튼 리스너
            btnCancle.setOnClickListener((v1) -> finish());//TODO: check this
            btnSignup.setOnClickListener((v2)-> {
                RadioButton genderButton = (RadioButton)findViewById(genderGroup.getCheckedRadioButtonId());

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

                // 성별 체크 검사
                if(genderButton!=null) {
                    Log.w("Dialog Button Test", "성별 : "+genderButton.getText().toString());
                } else {
                    hasError = true;
                }

                // 생년월일 검사
                if (((EditUtil.gTFE(editBirthYear).length() == 4) && (EditUtil.gTFE(editBirthMonth).length() == 2) && (EditUtil.gTFE(editBirthDay).length() == 2))) {
                    alertBirth.setVisibility(View.GONE);
                    Log.w("Dialog Button Test", "년 : " + EditUtil.gTFE(editBirthYear) + " 월 : " + EditUtil.gTFE(editBirthMonth) + " 일 : " + EditUtil.gTFE(editBirthDay));
                } else {
                    hasError = true;
                    alertBirth.setVisibility(View.VISIBLE);
                    TextUtil.alertTextGone(alertBirth);
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

                if(personalInformation.isChecked()) {
                    Log.w("Dialog Button Test", "개인정보 : "+personalInformation.isChecked());
                } else {
                    hasError  = true;
                    Toast.makeText(SigninRepairShopActivity.this, "개인 정보 수집 / 이용 내역에 동의해주세요", Toast.LENGTH_SHORT).show();
                }


                if(!hasError) {
                    // 서버로 회원정보 전송
                    Toast.makeText(SigninRepairShopActivity.this, "전송 성공", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                }
            });
        });
    }

    private void setBtnSignin() {
        btnSignup.setOnClickListener((v) -> {
            Intent i  = new Intent(SigninRepairShopActivity.this, LobbyActivity.class);
            startActivity(i);
            finish();
        });
    }
}
