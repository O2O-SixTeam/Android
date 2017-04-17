package com.jspark.android.kardoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.jspark.android.kardoc.RequestEstimationActivity.BRAND_CODE;

public class BrandActivity extends AppCompatActivity {

    Context mContext;

    RecyclerView brands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        mContext = BrandActivity.this;

        setLayout();
    }

    private void setLayout() {
        brands = (RecyclerView)findViewById(R.id.brandRecycler);
        brands.setLayoutManager(new GridLayoutManager(this, 4));
        BrandAdapter adapter = new BrandAdapter(mContext);
        brands.setAdapter(adapter);
    }

    public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.Holder> {

        Context mContext;

        int brandImages[] = {R.mipmap.trans_acura, R.mipmap.trans_alfaromeo, R.mipmap.trans_astonmartin, R.mipmap.trans_audi
                            ,R.mipmap.trans_bentley, R.mipmap.trans_benz, R.mipmap.trans_bmw, R.mipmap.trans_bugatti
                            ,R.mipmap.trans_buick, R.mipmap.trans_cadillac, R.mipmap.trans_chevrolet, R.mipmap.trans_chrysler
                            ,R.mipmap.trans_citroen, R.mipmap.trans_dodge, R.mipmap.trans_ferrari, R.mipmap.trans_fiat
                            ,R.mipmap.trans_ford, R.mipmap.trans_genesis, R.mipmap.trans_gm, R.mipmap.trans_honda
                            ,R.mipmap.trans_hummer, R.mipmap.trans_hyundai, R.mipmap.trans_infiniti, R.mipmap.trans_isuzu
                            ,R.mipmap.trans_jaguar, R.mipmap.trans_jeep, R.mipmap.trans_kia, R.mipmap.trans_lamborghini
                            ,R.mipmap.trans_landrover, R.mipmap.trans_lexus, R.mipmap.trans_lincoln, R.mipmap.trans_lotus
                            ,R.mipmap.trans_maserati, R.mipmap.trans_maybach, R.mipmap.trans_mazda, R.mipmap.trans_mini
                            ,R.mipmap.trans_mitsubishi, R.mipmap.trans_nissan, R.mipmap.trans_peugeot, R.mipmap.trans_pontiac
                            ,R.mipmap.trans_porsche, R.mipmap.trans_renault, R.mipmap.trans_rollsroyce, R.mipmap.trans_saab
                            ,R.mipmap.trans_scion, R.mipmap.trans_smart, R.mipmap.trans_ssangyong, R.mipmap.trans_subaru
                            ,R.mipmap.trans_suzuki, R.mipmap.trans_toyota, R.mipmap.trans_volkswagen, R.mipmap.trans_volvo};
        String brandNames[] = {"Acura", "Alfa Romeo", "Aston Martin", "Audi"
                            , "Bentley", "Benz", "BMW", "Bugatti"
                            , "Buick", "Cadillac", "Chevrolet", "Chrysler"
                            , "Citroen", "Dodge", "Ferrari", "Fiat"
                            , "Ford", "Genesis", "GM", "Honda"
                            , "Hummer", "Hyundai", "Infiniti", "Isuzu"
                            , "Jaguar", "Jeep", "Kia", "Lamborghini"
                            , "LandRover", "Lexus", "Lincoln", "Lotus"
                            , "Maserati", "Maybach", "Mazda", "Mini"
                            , "Mitsubishi", "Nissan", "Peugeot", "Pontiac"
                            , "Porsche", "Renault", "RollsRoyce", "Saab"
                            , "Scion", "Smart", "SsangYong", "Subaru"
                            , "Suzuki", "Toyota", "Volkswagen", "Volvo"};

        public BrandAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.brand_item, parent, false);

            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Glide.with(mContext).load(brandImages[position]).into(holder.brandImageButton);
            holder.brandName.setText(brandNames[position]);

            holder.brandImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.putExtra("result", holder.brandName.getText().toString());
                    setResult(BRAND_CODE, i);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return brandImages.length;
        }

        class Holder extends RecyclerView.ViewHolder {

            ImageButton brandImageButton;
            TextView brandName;

            public Holder(View itemView) {
                super(itemView);
                brandImageButton = (ImageButton)itemView.findViewById(R.id.brandImage);
                brandName = (TextView)itemView.findViewById(R.id.brandName);
            }
        }
    }
}
