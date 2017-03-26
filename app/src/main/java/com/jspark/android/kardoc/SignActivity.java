package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignActivity extends AppCompatActivity {

    TextView alertId, alertPw;
    EditText editId, editPw;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        setWidgets();

        checkId();

        setBtnSignin();
    }

    private void setWidgets() {
        editId = (EditText)findViewById(R.id.editId);
        editPw = (EditText)findViewById(R.id.editPw);
        alertId = (TextView)findViewById(R.id.errorId);
        alertPw = (TextView)findViewById(R.id.errorPw);
        btnSignin = (Button)findViewById(R.id.btnSignin);
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
