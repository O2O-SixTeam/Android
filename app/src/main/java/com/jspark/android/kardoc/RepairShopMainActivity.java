package com.jspark.android.kardoc;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.jspark.android.kardoc.domain.Shop;
import com.jspark.android.kardoc.server.ApiServices;
import com.jspark.android.kardoc.util.RetrofitUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jspark.android.kardoc.SignActivity.myToken;

public class RepairShopMainActivity extends AppCompatActivity {

    private final int REQ_PERMISSION = 100; // 권한요청코드
    private final int REQ_CAMERA = 101; // 카메라 요청코드
    private final int REQ_GALLERY = 102; // 갤러리 요청코드

    ImageButton mCamera, mGallery;
    ImageView mImageView;
    TextView mMovieTest, mUpload;
    Button mOk, mCancel, mSave;
    EditText editText;
    Dialog customDialog;
    String getUri = "";

    ApiServices apiServices = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_shop_main);

        setWidget();

        setRetrofit();

        buttonDisable();

        setListener();

        checkPermission();
    }

    private void setRetrofit() {
        RetrofitUtil retrofit = RetrofitUtil.getInstance();
        apiServices = retrofit.getApiServices();
    }

    // 버튼 비활성화하기
    private void buttonDisable() {
        mCamera.setEnabled(false);
    }

    // 버튼 활성화하기
    private void buttonEnable() {
        mCamera.setEnabled(true);
    }

    private void init() {
        // 권한처리가 통과 되었을때만 버튼을 활성화 시켜준다
        buttonEnable();
    }

    // 권한관리
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionControl.checkPermission(this, REQ_PERMISSION)) {
                init();
            }
        } else {
            init();
        }
    }

    // 위젯 세팅
    private void setWidget() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mCamera = (ImageButton) findViewById(R.id.btnCamera);
        mGallery = (ImageButton) findViewById(R.id.btnGallery);
        mMovieTest = (TextView) findViewById(R.id.testMovie);
        mUpload = (TextView) findViewById(R.id.upload);
        mSave = (Button) findViewById(R.id.save);

    }

    // 리스너 세팅
    private void setListener() {
        mCamera.setOnClickListener(clickListener);
        mGallery.setOnClickListener(clickListener);
        mMovieTest.setOnClickListener(clickListener);
        mUpload.setOnClickListener(clickListener);
        mSave.setOnClickListener(clickListener);
    }

    // 사진촬영후 임시로 저장할 파일 공간
    Uri fileUri = null;
    // 리스너 정의
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Boolean hasError = false;
            Intent intent = null;
            Shop shop = new Shop();
            switch (v.getId()) {
                case R.id.btnCamera: //카메라 버튼 동작
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // 롤리팝 이상 버전에서는 아래 코드를 반영해야 한다.
                    // --- 카메라 촬영 후 미디어 컨텐트 uri 를 생성해서 외부저장소에 저장한다 ---
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // 저장할 미디어 속성을 정의하는 클래스
                        ContentValues values = new ContentValues(1);
                        // 속성중에 파일의 종류를 정의
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        // 전역변수로 정의한 fileUri에 외부저장소 컨텐츠가 있는 Uri 를 임시로 생성해서 넣어준다.
                        fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        // 위에서 생성한 fileUri를 사진저장공간으로 사용하겠다고 설정
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        // Uri에 읽기와 쓰기 권한을 시스템에 요청
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    // --- 여기 까지 컨텐트 uri 강제세팅 ---
                    startActivityForResult(intent, REQ_CAMERA);
                    break;

                case R.id.btnGallery: // 갤러리에서 이미지 불러오기
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // 이미지 여러개 가져오는 플래그
                    // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType("image/*"); // 외부저장소에 있는 이미지만 가져오기 위한 필터링
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_GALLERY);
                    break;

                case R.id.upload:
                    uploadYoutubeUrl();
                    break;

                case R.id.testMovie:
                    if (!getUri.equals("")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getUri)));
                    } else {
                        Toast.makeText(RepairShopMainActivity.this, "먼저 url을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.save:
                    if(!hasError) {
                        //SHOP 이미 저장되어 있음
                        SharedPreferences sharedPreferences = getSharedPreferences(myToken, MODE_PRIVATE);
                        String token = sharedPreferences.getString(myToken, null);

                        Call<Shop> uploadurl = apiServices.uploadmovie("Token "+token, 1, SignupRepairShopActivity.SHOP.getShopname(), getUri);
                        uploadurl.enqueue(new Callback<Shop>() {
                            @Override
                            public void onResponse(Call<Shop> call, Response<Shop> response) {
                                Log.w("response", response.toString());
                                //response.
                                if (response.code() == 201) {

                                    Intent intent = new Intent(RepairShopMainActivity.this, LobbyActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            //Log

                            @Override
                            public void onFailure(Call<Shop> call, Throwable t) {

                            }
                        });
                    }
                    break;
            }
        }
    };
    private void uploadYoutubeUrl(){
        customDialog = new Dialog(RepairShopMainActivity.this);
        customDialog.setContentView(R.layout.save_youtube_url);
        editText = (EditText) customDialog.findViewById(R.id.editText);
        mOk = (Button) customDialog.findViewById(R.id.ok);
        mCancel = (Button) customDialog.findViewById(R.id.cancel);
        customDialog.show();

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUri = editText.getText().toString();
                if (!getUri.equals("")) {
                    customDialog.dismiss();
                } else {
                    Toast.makeText(RepairShopMainActivity.this, "url을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQ_GALLERY:
                if (resultCode == RESULT_OK) {
                    fileUri = data.getData();
                    Glide.with(this)
                            .load(fileUri)
                            .into(mImageView);
                }
                break;

            case REQ_CAMERA:
                if (resultCode == RESULT_OK) { // 사진 확인처리됨 RESULT_OK = -1
                    // 롤리팝 체크
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        Log.i("Camera", "data.getData()===============================" + data.getData());
                        fileUri = data.getData();
                    }
                    Log.i("Camera", "fileUri===============================" + fileUri);
                    if (fileUri != null) {
                        // 글라이드로 이미지 세팅하면 자동으로 사이즈 조절
                        Glide.with(this)
                                .load(fileUri)
                                .into(mImageView);
                    }
                    // 각자 고민...
                    else {
                        Toast.makeText(this, "사진파일이 없습니다", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // resultCode 가 0이고 사진이 찍혔으면 uri 가 남는데
                    // uri 가 있을 경우 삭제처리...
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION) {
            if (PermissionControl.onCheckResult(grantResults)) {
                init();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
