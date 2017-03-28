package com.jspark.android.kardoc;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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

    private void checkId() {
        // make Logic to check Id&Pw
    }

    private void setBtnSignup() {
        btnSignup.setOnClickListener((v) -> {

            EditText editFamily, editGiven, editPhone, editBirthYear, editBirthMonth, editBirthDay, editId, editPw, editPwcheck;
            TextView alertId, alertPw, alertPwCheck;
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

            // 버튼 리스너
            btnNegative.setOnClickListener((v1) -> {});
            btnPositive.setOnClickListener((v2)-> {
                RadioButton genderButton = (RadioButton)customdialog.findViewById(genderGroup.getCheckedRadioButtonId());

                Log.w("Dialog Button Test", "성 : "+gTFE(editFamily)+" 이름 : "+gTFE(editGiven));
                Log.w("Dialog Button Test", "폰 : "+gTFE(editPhone));
                Log.w("Dialog Button Test", "성별 : "+genderButton.getText().toString());
                Log.w("Dialog Button Test", "년 : "+gTFE(editBirthYear)+" 월 : "+gTFE(editBirthMonth)+" 일 : "+gTFE(editBirthDay));
                Log.w("Dialog Button Test", "id : "+gTFE(editId)+" pw : "+gTFE(editPw));
                Log.w("Dialog Button Test", "개인정보 : "+personalInformation.isChecked());
            });

            // 커스텀 다이얼로그 시작작
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

    // getTextFromEditText
    private String gTFE(EditText edit) {

        return edit.getText().toString();
    }
}
