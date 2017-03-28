package com.jspark.android.kardoc;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.jspark.android.kardoc.util.EditUtil;
import com.jspark.android.kardoc.util.SignUtil;
import com.jspark.android.kardoc.util.TextUtil;

import java.util.Arrays;

public class SignActivity extends AppCompatActivity {

    private LoginButton signinFacebook;
    private CallbackManager callbackManager;

    private TextView alertId, alertPw, newCenter;
    private EditText editId, editPw;
    private Button btnSignup, btnSignin, btnForgetPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        // 위젯 선언
        setWidgets();

        // 페이스북 로그인 기능
        setCallbackManager();
        setSigninFacebook();

        // 회원가입 버튼 기능
        setBtnSignup();

        // 로그인 버튼 기능능
       setBtnSignin();
    }

    private void setCallbackManager() {
        callbackManager = CallbackManager.Factory.create();
    }

    private void setSigninFacebook() {
        signinFacebook = (LoginButton) findViewById(R.id.signinFacebook);
        signinFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        signinFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                    Log.i("result", object.toString());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent i  = new Intent(SignActivity.this, LobbyActivity.class);
                    startActivity(i);
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });
    }

    //---------Facebook Login------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    //-----------------------------

    private void setWidgets() {
        editId = (EditText)findViewById(R.id.editId);
        editPw = (EditText)findViewById(R.id.editPw);
        alertId = (TextView)findViewById(R.id.errorId);
        alertPw = (TextView)findViewById(R.id.errorPw);
        newCenter = (TextView)findViewById(R.id.signinCenter);
        btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignin = (Button)findViewById(R.id.btnSignin);
        btnForgetPw = (Button)findViewById(R.id.btnForgetPw);
    }

    private void setBtnSignup() {
        btnSignup.setOnClickListener((v) -> {

            EditText editFamily, editGiven, editPhone, editBirthYear, editBirthMonth, editBirthDay, editId, editPw, editPwcheck;
            TextView alertBirth, alertId, alertPw, alertPwCheck;
            RadioGroup genderGroup;
            CheckBox personalInformation;
            Button btnNegative, btnPositive;

            // 커스텀 다이얼로그 생성
            Dialog customdialog = new Dialog(SignActivity.this);
            customdialog.setContentView(R.layout.signupdialog);

            // 위젯 선언
            editFamily = (EditText)customdialog.findViewById(R.id.editFamilyName);
            editGiven = (EditText)customdialog.findViewById(R.id.editGivenName);
            editPhone = (EditText)customdialog.findViewById(R.id.editPhone);
            editBirthYear = (EditText)customdialog.findViewById(R.id.editYear);
            editBirthMonth = (EditText)customdialog.findViewById(R.id.editMonth);
            editBirthDay = (EditText)customdialog.findViewById(R.id.editDay);
            alertBirth = (TextView)customdialog.findViewById(R.id.errorBirth);
            editId = (EditText)customdialog.findViewById(R.id.editId);
            alertId = (TextView)customdialog.findViewById(R.id.errorId);
            editPw = (EditText)customdialog.findViewById(R.id.editPw);
            alertPw = (TextView)customdialog.findViewById(R.id.errorPw);
            editPwcheck = (EditText)customdialog.findViewById(R.id.editPwCheck);
            alertPwCheck = (TextView)customdialog.findViewById(R.id.errorPwCheck);
            genderGroup = (RadioGroup)customdialog.findViewById(R.id.genderGroup);
            personalInformation = (CheckBox)customdialog.findViewById(R.id.checkPrivate);
            btnNegative = (Button)customdialog.findViewById(R.id.buttonCancle);
            btnPositive = (Button)customdialog.findViewById(R.id.buttonSignup);

            // 전화번호 하이픈 자동 입
            editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

            // 버튼 리스너
            btnNegative.setOnClickListener((v1) -> customdialog.dismiss());
            btnPositive.setOnClickListener((v2)-> {
                RadioButton genderButton = (RadioButton)customdialog.findViewById(genderGroup.getCheckedRadioButtonId());

                boolean hasError = false;

                // 성, 이름 검사
                if(!("".equals(EditUtil.gTFE(editFamily)))) {
                    Log.w("Dialog Button Test", "성 : "+EditUtil.gTFE(editFamily));
                    if(!("".equals(EditUtil.gTFE(editGiven)))) {
                        Log.w("Dialog Button Test", "이름 : "+EditUtil.gTFE(editGiven));
                    } else {
                        hasError = true;
                        editGiven.requestFocus();
                        Toast.makeText(SignActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    hasError = true;
                    Toast.makeText(SignActivity.this, "성을 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                // 핸드폰 번호 검사
                if(EditUtil.gTFE(editPhone).length()==13) {
                    Log.w("Dialog Button Test", "폰 : " + EditUtil.gTFE(editPhone));
                } else {
                    hasError = true;
                    Toast.makeText(SignActivity.this, "폰 번호를 확인해주세요", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SignActivity.this, "개인 정보 수집 / 이용 내역에 동의해주세요", Toast.LENGTH_SHORT).show();
                }


                if(!hasError) {
                    // 서버로 회원정보 전송
                    Toast.makeText(SignActivity.this, "전송 성공", Toast.LENGTH_SHORT).show();
                    customdialog.dismiss();
                } else {

                }
            });

            // 커스텀 다이얼로그 시작
           customdialog.show();
        });
    }

    private void setBtnSignin() {
        btnSignin.setOnClickListener((v) -> {
            Intent i  = new Intent(SignActivity.this, LobbyActivity.class);
            startActivity(i);
            finish();
        });
    }
}
