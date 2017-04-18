package com.jspark.android.kardoc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.jspark.android.kardoc.domain.Result;
import com.jspark.android.kardoc.domain.User;
import com.jspark.android.kardoc.server.ApiServices;
import com.jspark.android.kardoc.util.EditUtil;
import com.jspark.android.kardoc.util.RetrofitUtil;
import com.jspark.android.kardoc.util.SignUtil;
import com.jspark.android.kardoc.util.TextUtil;

import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignActivity extends AppCompatActivity {

    public static final String MY_TOKEN = "MY_TOKEN";

    private LoginButton signinFacebook;
    private CallbackManager callbackManager;

    private TextView alertId, alertPw, signinRepairShop;
    private EditText editId, editPw;
    private Button btnSignup, btnSignin, btnForgetPw;

    private boolean leapYear = false;

    Context mContext = null;

    ApiServices apiServices = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mContext = SignActivity.this;

        // 레트로핏 연결
        setRetrofit();

        // 위젯 선언
        setWidgets();

        // 페이스북 로그인 기능
        setCallbackManager();
        setSigninFacebook();

        // 회원가입 버튼 기능
        setBtnSignup();

        // 로그인 버튼 기능능
        setBtnSignin();

        //공업사 로그인 기능
        setSigninRepairShop();
    }

    private void setRetrofit() {
        RetrofitUtil retrofit = RetrofitUtil.getInstance();
        apiServices = retrofit.getApiServices();
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
                parameters.putString("fields", "id,name,email");
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
        signinRepairShop = (TextView)findViewById(R.id.signinRepairShop); //공업사 회원가입 Activity로 이동.
        btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignin = (Button)findViewById(R.id.btnSignin);
        btnForgetPw = (Button)findViewById(R.id.btnForgetPw);
    }

    private void setBtnSignup() {
        btnSignup.setOnClickListener((v) -> {

            EditText editFamily, editGiven, editPhone, editId, editPw, editPwcheck;
            Spinner yearSpinner, monthSpinner, daySpinner;
            TextView alertBirth, alertId, alertPw, alertPwCheck;
            RadioGroup genderGroup;
            CheckBox personalInformation;
            Button btnNegative, btnPositive;

            // 커스텀 다이얼로그 생성
            Dialog customdialog = new Dialog(SignActivity.this);
            customdialog.setContentView(R.layout.signupdialog);

            // 위젯 선언
            editFamily = (EditText)customdialog.findViewById(R.id.editFamilyName);
            editGiven = (EditText)customdialog.findViewById(R.id.editShopName);
            editPhone = (EditText)customdialog.findViewById(R.id.editPhone);
            yearSpinner = (Spinner)customdialog.findViewById(R.id.editYear);
            monthSpinner = (Spinner)customdialog.findViewById(R.id.editMonth);
            daySpinner = (Spinner)customdialog.findViewById(R.id.editDay);
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
            //editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

            // 생년월 스피너 정보
            String[] yearData = mContext.getResources().getStringArray(R.array.yearArray);
            String[] monthData = mContext.getResources().getStringArray(R.array.monthArray);
            ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, yearData);
            ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, monthData);

            yearSpinner.setAdapter(yearAdapter);
            monthSpinner.setAdapter(monthAdapter);

            // 윤년, 월을 기준으로 28, 29, 30, 31일 세팅
            yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(Integer.parseInt((String)yearSpinner.getSelectedItem())%4==0) {
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
                    int getMonth = Integer.parseInt((String)monthSpinner.getSelectedItem());
                    if(getMonth==2) {
                        if(!leapYear) {
                            String[] dayData = mContext.getResources().getStringArray(R.array.day28Array);
                            ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, dayData);
                            daySpinner.setAdapter(dayAdapter);
                        } else {
                            String[] dayData = mContext.getResources().getStringArray(R.array.day29Array);
                            ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, dayData);
                            daySpinner.setAdapter(dayAdapter);
                        }
                    } else if(getMonth==4||getMonth==6||getMonth==9||getMonth==11) {
                        String[] dayData = mContext.getResources().getStringArray(R.array.day30Array);
                        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, dayData);
                        daySpinner.setAdapter(dayAdapter);
                    } else {
                        String[] dayData = mContext.getResources().getStringArray(R.array.day31Array);
                        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, dayData);
                        daySpinner.setAdapter(dayAdapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // 버튼 리스너
            btnNegative.setOnClickListener((v1) -> customdialog.dismiss());
            btnPositive.setOnClickListener((v2)-> {
                RadioButton genderButton = (RadioButton)customdialog.findViewById(genderGroup.getCheckedRadioButtonId());

                String nameData = "";
                String phoneData = "";
                String genderData = "";
                String birthData = "";
                String emailData = "";
                String passwordData = "";

                boolean hasError = false;

                // 성, 이름 검사
                if(!("".equals(EditUtil.gTFE(editFamily)))) {
                    if(!("".equals(EditUtil.gTFE(editGiven)))) {
                        nameData = EditUtil.gTFE(editFamily)+EditUtil.gTFE(editGiven);
                        Log.w("Dialog Button Test", "Name : "+nameData);
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
                if(EditUtil.gTFE(editPhone).length()==11) {
                    Log.w("Dialog Button Test", "폰 : " + EditUtil.gTFE(editPhone));
                    phoneData = EditUtil.gTFE(editPhone);
                } else {
                    hasError = true;
                    Toast.makeText(SignActivity.this, "폰 번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                }

                // 성별 체크 검사
                if(genderButton!=null) {
                    if(genderButton.getText().toString().equals("남성")) {
                        genderData = "M";
                    } else {
                        genderData = "F";
                    }
                    Log.w("Dialog Button Test", "성별 : "+genderData);
                } else {
                    hasError = true;
                }

                // 생년월일은 스피너로 특정 값만 넣을 수 있게 했으므로 검사하지 않는다
                birthData = yearSpinner.getSelectedItem().toString()
                        + "-"
                        + monthSpinner.getSelectedItem().toString()
                        + "-"
                        + daySpinner.getSelectedItem().toString();

                // 이메일 형식 아이디 검사
                if(SignUtil.validateEmail(EditUtil.gTFE(editId))) {
                    alertId.setVisibility(View.GONE);
                    Log.w("Dialog Email Check", EditUtil.gTFE(editId));
                    emailData = EditUtil.gTFE(editId);
                } else {
                    hasError = true;
                    alertId.setVisibility(View.VISIBLE);
                    TextUtil.alertTextGone(alertId);
                }

                // 비밀번호 형식 검사
                if(SignUtil.validatePassword(EditUtil.gTFE(editPw))) {
                    alertPw.setVisibility(View.GONE);
                    Log.w("Dialog Password Check", EditUtil.gTFE(editPw));
                    passwordData = EditUtil.gTFE(editPw);
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
                    User user = new User();
                    user.setName(nameData);
                    user.setPhone(phoneData);
                    user.setGender(genderData);
                    user.setEmail(emailData);
                    user.setBirth(birthData);
                    user.setUsername(emailData);
                    user.setPassword(passwordData);

                    Call<ResponseBody> remoteData = apiServices.createUser(user);

                    remoteData.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.w("create user response", response.toString());
                            try {
                                if(response.code()==201&&response.body()!=null) {
                                    customdialog.dismiss();
                                }
                            } catch(Exception e) {
                                Log.e("SignUp Error", "SignUp Error Occured!!!!");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });


                } else {

                }
            });

            // 커스텀 다이얼로그 시작
           customdialog.show();
        });
    }

    private void setBtnSignin() {
        btnSignin.setOnClickListener((v) -> {
            boolean hasError = false;
            String myId="", myPw="";

            if(SignUtil.validateEmail(EditUtil.gTFE(editId))) {
                alertId.setVisibility(View.GONE);
                myId = EditUtil.gTFE(editId);
            } else {
                hasError = true;
                alertId.setVisibility(View.VISIBLE);
                TextUtil.alertTextGone(alertId);
            }

            if(SignUtil.validatePassword(EditUtil.gTFE(editPw))) {
                alertPw.setVisibility(View.GONE);
                myPw = EditUtil.gTFE(editPw);
            } else {
                hasError = true;
                alertPw.setVisibility(View.VISIBLE);
                TextUtil.alertTextGone(alertPw);
            }

            if(!hasError) {
                SharedPreferences sharedPreferences = getSharedPreferences(MY_TOKEN, Context.MODE_PRIVATE);

                // 이전의 토큰 값 보기
                String originalToken = sharedPreferences.getString("token" + myId, "null");
                Log.w("Original Token", originalToken);

                String userName = myId;

                // 토큰 받아오기
                Call<Result> loginData = apiServices.loginUser(myId, myPw);
                Log.w("id / pw", myId+" / "+myPw);
                loginData.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.w("login response", response.toString());

                        if(response.code()==200) {
                            Log.w("token get", response.body().getToken());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token" + userName, response.body().getToken());
                            editor.putString(MY_TOKEN, response.body().getToken());
                            editor.commit();
                            Log.w("token shared", sharedPreferences.getString("token" + userName, "null"));
                            Log.w("token mine", sharedPreferences.getString(MY_TOKEN, "null"));


                            Intent i  = new Intent(SignActivity.this, LobbyActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(SignActivity.this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void setSigninRepairShop(){
        signinRepairShop.setOnClickListener(v -> {
           Intent i = new Intent(SignActivity.this, SignupRepairShopActivity.class);
            startActivity(i);
        });
    }
}
