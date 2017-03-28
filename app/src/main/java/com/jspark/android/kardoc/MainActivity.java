package com.jspark.android.kardoc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final int LOADING_SPEED = 2000;

    Thread splash = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                // 2. 서브 스레드 splash 2초간 sleep
                splash.sleep(LOADING_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 3. 핸들러로 값 전달
            h.sendEmptyMessage(LOADING_SPEED);
        }
    });

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(LOADING_SPEED==msg.what) {
                // 4. 로그인 액티비티로 이동
                Intent i = new Intent(MainActivity.this, SignActivity.class);
                startActivity(i);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 로딩화면(스플래시) 시작
        splash.start();
    }

}
