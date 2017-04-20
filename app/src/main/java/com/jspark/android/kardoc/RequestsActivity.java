package com.jspark.android.kardoc;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jspark.android.kardoc.SignActivity.MY_TOKEN;

public class RequestsActivity extends AppCompatActivity {

    RecyclerView requestsList;
    ApiServices apiServices;
    List<Request> listData = new ArrayList<>();
    RequestsAdapter adapter;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        setWidgets();
        setRetrofit();
        setRequestsDatas();
        setBackButton();
    }

    private void setRetrofit() {
        RetrofitUtil retrofit = RetrofitUtil.getInstance();
        apiServices = retrofit.getApiServices();
    }

    private void setRequestsDatas() {
        Call<List<Request>> loadAll = apiServices.loadRequests();
        loadAll.enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                if(response.isSuccessful()) {
                    if(listData.size()!=0) {
                        listData.clear();
                    }
                    listData = response.body();
                    SharedPreferences sharedPreferences = getSharedPreferences(MY_TOKEN, MODE_PRIVATE);
                    String userName = sharedPreferences.getString("USERNAME", null);
                    List<Request> postData = new ArrayList<>();
                    for(Request item : listData) {
                        if(item.getRequestedby().equals(userName)) {
                            postData.add(item);
                        }
                    }
                    setRecyclerView(postData);
                }
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {

            }
        });
    }

    private void setWidgets() {
        backButton = (ImageButton)findViewById(R.id.backButton);
    }

    private void setBackButton() {
        backButton.setOnClickListener(v -> RequestsActivity.super.onBackPressed());
    }

    private void setRecyclerView(List<Request> listData) {
        requestsList = (RecyclerView)findViewById(R.id.requestsRecycler);
        adapter = new RequestsAdapter(RequestsActivity.this, listData);
        requestsList.setLayoutManager(new LinearLayoutManager(RequestsActivity.this));
        requestsList.setAdapter(adapter);
    }

    class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.Holder> {

        Context mContext;
        List<Request> mDataList = new ArrayList<>();

        public RequestsAdapter(Context context, List<Request> dataList) {
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
            TextView brandText, modelText, partText;

            public Holder(View itemView) {
                super(itemView);
                repreImage = (ImageView)itemView.findViewById(R.id.repreImage);
                brandText = (TextView)itemView.findViewById(R.id.brandText);
                modelText = (TextView)itemView.findViewById(R.id.modelText);
                partText = (TextView)itemView.findViewById(R.id.partText);
            }
        }
    }
}
