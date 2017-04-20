package com.jspark.android.kardoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jspark.android.kardoc.domain.Request;
import com.jspark.android.kardoc.server.ApiServices;
import com.jspark.android.kardoc.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaseActivity extends AppCompatActivity {

    ApiServices apiServices = null;
    RecyclerView lists;
    List<Request> listData;
    CaseAdapter adapter;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);

        setWidgets();
        setRetrofit();
        setCase();
        setBackButton();
    }

    private void setRetrofit() {
        RetrofitUtil retrofit = RetrofitUtil.getInstance();
        apiServices = retrofit.getApiServices();
    }

    private void setCase() {
        Intent i = getIntent();
        listData = new ArrayList<>();
        String caseBy = i.getStringExtra("case");
        Map<String, String> options = new HashMap<>();
        Call<List<Request>> loadCase = null;
        switch (caseBy) {
            case "fender" :
                options.put("broken1", "펜더");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            listData = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                options.clear();
                options.put("broken2", "펜더");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            for(Request item : response.body()) {
                                if(!("펜더".equals(item.getBroken1()))) {
                                    listData.add(item);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                options.clear();
                options.put("broken3", "펜더");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            for(Request item : response.body()) {
                                if(!("펜더".equals(item.getBroken1())&&!("펜더".equals(item.getBroken2())))) {
                                    listData.add(item);
                                }
                            }
                            setRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                break;
            case "bumper" :
                options.put("broken1", "범퍼");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            listData = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                options.clear();
                options.put("broken2", "범퍼");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            for(Request item : response.body()) {
                                if(!("범퍼".equals(item.getBroken1()))) {
                                    listData.add(item);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                options.clear();
                options.put("broken3", "범퍼");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            for(Request item : response.body()) {
                                if(!("범퍼".equals(item.getBroken1())&&!("범퍼".equals(item.getBroken2())))) {
                                    listData.add(item);
                                }
                            }
                            setRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                break;
            case "audi" :
                options.put("brand", "아우디");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            listData = response.body();
                            setRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                break;
            case "benz" :
                options.put("brand", "벤츠");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            listData = response.body();
                            setRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                break;
            case "bmw" :
                options.put("brand", "BMW");
                loadCase = apiServices.loadCase(options);
                loadCase.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            listData = response.body();
                            setRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                break;
            case "all" :
                Call<List<Request>> loadAll = apiServices.loadRequests();
                loadAll.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                        if(response.isSuccessful()) {
                            listData = response.body();
                            setRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {

                    }
                });
                break;
        }
    }

    private void setWidgets() {
        backButton = (ImageButton)findViewById(R.id.backButton);
    }

    private void setBackButton() {
        backButton.setOnClickListener(v -> CaseActivity.super.onBackPressed());
    }

    private void setRecyclerView() {
        lists = (RecyclerView)findViewById(R.id.caseRecycler);
        adapter = new CaseAdapter(CaseActivity.this, listData);
        lists.setLayoutManager(new LinearLayoutManager(CaseActivity.this));
        lists.setAdapter(adapter);
    }

    class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.Holder> {

        Context mContext;
        List<Request> mDataList = new ArrayList<>();

        public CaseAdapter(Context context, List<Request> dataList) {
            mContext = context;
            mDataList = dataList;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.case_item, parent, false);
            Holder holder = new Holder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Request request = mDataList.get(position);

            holder.brandText.setText(request.getBrand());
            holder.modelText.setText(request.getModel());
            holder.partText.setText(request.getBroken1());
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            ImageView repreImage;
            TextView brandText, modelText, partText, priceText;

            public Holder(View itemView) {
                super(itemView);
                repreImage = (ImageView)itemView.findViewById(R.id.repreImage);
                brandText = (TextView)itemView.findViewById(R.id.brandText);
                modelText = (TextView)itemView.findViewById(R.id.modelText);
                partText = (TextView)itemView.findViewById(R.id.partText);
                priceText = (TextView)itemView.findViewById(R.id.priceText);
            }
        }
    }
}