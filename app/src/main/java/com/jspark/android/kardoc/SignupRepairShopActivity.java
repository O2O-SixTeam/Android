package com.jspark.android.kardoc;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jspark.android.kardoc.domain.Shop;
import com.jspark.android.kardoc.server.ApiServices;
import com.jspark.android.kardoc.util.EditUtil;
import com.jspark.android.kardoc.util.RetrofitUtil;

import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jspark.android.kardoc.SignActivity.MY_TOKEN;

public class SignupRepairShopActivity extends AppCompatActivity {

    public static final String SHOP_OWNER = "shop_owner";

    Button btnCancle;
    Button btnSignup;

    EditText editShopName, editShopNumber, editDetailAddress, editCompanyIntroduction, editOwnerNumber;

    WebView zipCodeWebView;
    TextView zipCodeTest;

    Handler handler;

    ApiServices apiServices = null;

    String shopNameData = "";
    String addressData = "";
    String zoneData = "";
    String detailData = "";
    String numberData = "";
    String longitudeData = "";
    String latitudeData = "";
    String bnumberData = "";

    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_repair_shop);

        btnSignup = (Button) findViewById(R.id.buttonSignupCompany);
        btnCancle = (Button) findViewById(R.id.buttonCancleCompany);

        initWebView();
        handler = new Handler();

        // 레트로핏 연결
        setRetrofitInShop();

        setWidget();
        setBtnSignupCompany();
        setBtnCancle();

        geocoder = new Geocoder(this, Locale.KOREA);
    }

    private void setRetrofitInShop() {
        RetrofitUtil retrofit = RetrofitUtil.getInstance();
        apiServices = retrofit.getApiServices();
    }

    private void setWidget() {
        editShopName = (EditText) findViewById(R.id.editShopName);
        editShopNumber = (EditText)findViewById(R.id.editShopNumber);
        zipCodeTest = (TextView)findViewById(R.id.zipCodeText);
        editDetailAddress = (EditText)findViewById(R.id.editDetialAddress);
        editCompanyIntroduction = (EditText) findViewById(R.id.editCompanyIntroduction);
        editOwnerNumber = (EditText)findViewById(R.id.editOwnerNumber);
        // 위젯 선언

        // 전화번호에 하이픈 없이 달라고 함
        // 전화번호 하이픈 자동 입력
        //editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private void initWebView() {
        zipCodeWebView = (WebView)findViewById(R.id.zipCodeWebView);
        zipCodeWebView.getSettings().setJavaScriptEnabled(true);
        zipCodeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        zipCodeWebView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        zipCodeWebView.setWebChromeClient(new WebChromeClient());
        zipCodeWebView.loadUrl("http://codeman77.ivyro.net/getAddress.php");
    }

    public class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(() -> {
                zipCodeTest.setText(String.format("(%s) %s [%s]", arg1, arg2, arg3));
                addressData = arg2+" "+arg3;
                zoneData = arg2.split(" ")[1];
                initWebView();
            });
        }
    }

    private void setBtnSignupCompany() {
        btnSignup.setOnClickListener(v -> {
            boolean hasError = false;

            //상호명 검사
            if (!("".equals(EditUtil.gTFE(editShopName)))) {
                shopNameData = EditUtil.gTFE(editShopName);
            } else {
                hasError = true;
                Toast.makeText(SignupRepairShopActivity.this, "상호명을 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            // 공업사 연락처 검사
            if(!("".equals(EditUtil.gTFE(editShopNumber)))) {
                numberData = EditUtil.gTFE(editShopNumber);
            } else {
                hasError = true;
                Toast.makeText(SignupRepairShopActivity.this, "연락처를 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            if(zipCodeTest.length()<10) {
                hasError = true;
                Toast.makeText(SignupRepairShopActivity.this, "우편번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            // 상세주소 검사
            if (!("".equals(EditUtil.gTFE(editDetailAddress)))) {
                detailData = EditUtil.gTFE(editDetailAddress);
                if(!addressData.contains(detailData)) addressData = addressData + " " + detailData;
            } else {
                hasError = true;
                Toast.makeText(SignupRepairShopActivity.this, "상세주소를 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            // 주소로부터 위경도 가져오기
            try {
                longitudeData = String.valueOf(geocoder.getFromLocationName(addressData, 1).get(0).getLongitude());
                latitudeData = String.valueOf(geocoder.getFromLocationName(addressData, 1).get(0).getLatitude());
            } catch(Exception e) {
                e.printStackTrace();
            }

            //상호 소개 검사
            if (!("".equals(EditUtil.gTFE(editCompanyIntroduction)))) {
                detailData = EditUtil.gTFE(editCompanyIntroduction);
            } else {
                hasError = true;
                Toast.makeText(SignupRepairShopActivity.this, "회사 소개를 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            if(!("".equals(EditUtil.gTFE(editOwnerNumber)))) {
                bnumberData = EditUtil.gTFE(editOwnerNumber);
            } else {
                hasError = true;
                Toast.makeText(SignupRepairShopActivity.this, "사업자 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            }

            if (!hasError) {
                SharedPreferences sharedPreferences = getSharedPreferences(MY_TOKEN, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(MY_TOKEN, "null");

                Shop shop = new Shop();
                shop.setShopname(shopNameData);
                shop.setAddress(addressData);
                shop.setZone(zoneData);
                shop.setDetail(detailData);
                shop.setNumber(numberData);
                shop.setBnumber(bnumberData);
                shop.setLongitude(longitudeData);
                shop.setLatitude(latitudeData);

                Call<ResponseBody> remoteData = apiServices.createShop("Token "+token, shop);
                remoteData.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("error", t.toString());
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
