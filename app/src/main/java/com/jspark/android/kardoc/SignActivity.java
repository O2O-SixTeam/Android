package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class SignActivity extends AppCompatActivity {

    LoginButton signinFacebook;
    private CallbackManager callbackManager;

    TextView alertId, alertPw, newCenter;
    EditText editId, editPw;
    Button btnSignup, btnSignin, btnForgetPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        setWidgets();

        setCallbackManager();
        setSigninFacebook();

        checkId();

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

    private void setBtnSignin() {
        btnSignin.setOnClickListener((v) -> {
            Intent i  = new Intent(SignActivity.this, LobbyActivity.class);
            startActivity(i);
            finish();
        });
    }
}
