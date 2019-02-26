package com.example.bin.productmanagement.Demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.adapter.ProductAdapter;
import com.example.bin.productmanagement.Demo.model.MProduct;
import com.example.bin.productmanagement.Demo.mvp.productdetail.ProductDetailActivity;
import com.example.bin.productmanagement.Demo.mvp.productdetail.ProductDetailContract;
import com.example.bin.productmanagement.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;


/**
 * Đây là Class Activity Đầu tiên khi vào sử dụng app
 *
 * Chỉnh lại UI
 * Thêm Ads banner
 * Interstitial Ad(Ad xen kẽ) chưa hiển thị được
 */
public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rcv_Product;
    private Button btn_Total;
    private AdView adView;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;

    private ProductDatabase db;
    private ProductAdapter adapter;
    private ArrayList<MProduct> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initDB();
        initView();
        myAdsHandle();
        /*MobileAds.initialize(this,
                "ca-app-pub-8101540683171429~9714814472");*/
    }

    private void initView(){
        rcv_Product = findViewById(R.id.rcv_prd);
        btn_Total = findViewById(R.id.btn_Total);
        products = db.getAllProduct();
        adapter = new ProductAdapter(products,this);
        adView = findViewById(R.id.banner_ads);
        rcv_Product.setAdapter(adapter);
        mInterstitialAd = new InterstitialAd(this);
        //


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcv_Product.setLayoutManager(linearLayoutManager);
        btn_Total.setOnClickListener(this);
    }

    private void myAdsHandle(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.e("Banner ads","onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.e("Banner ads","onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.e("Banner ads","onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.e("Banner ads","onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Log.e("Banner ads","onAdClosed");
            }
        });

        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.e("Interstitial ads","onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                checkChangeActivity();
                Log.e("Interstitial ads","onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.e("Interstitial ads","onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.e("Interstitial ads","onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Log.e("Interstitial ads","onAdClosed");
                checkChangeActivity();
            }
        });

        Log.e("Ads Handle","Already");
    }

    private void initDB(){
        db = new ProductDatabase(this);
        if(db.getProductCount() == 0){
            db.initDefaultData();
        }
    }

    private static MainScreenActivity instance;

    public static MainScreenActivity getInstance() {
        if (instance == null) {
            instance = new MainScreenActivity();
        }
        return instance;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Total:{
//                if (mInterstitialAd.isLoaded()) {
//                     mInterstitialAd.show();
//                } else {
//                    Log.e("TAG", "The interstitial wasn't loaded yet.");
//                }

                ProductDetailActivity.showMe(this, 6);


                break;
            }
        }
    }
    private void checkChangeActivity(){
        Intent intent = new Intent(this, TotalActivity.class);
        for(int i = 0; i<products.size();i++){
            if(products.get(i).getAmount() != db.getProductAmount(products.get(i).getId())){
                db.updateProductAmount(products.get(i).getAmount(),products.get(i).getId());
            }
            Log.e("Amount",""+products.get(i).getId()+"\n"+products.get(i).getAmount());
        }
        if(db.getCountProductTotal() > 0) {
             startActivity(intent);

        }
        else {
            Toast.makeText(this,"Phải nhập ít nhất 1 sản phẩm",Toast.LENGTH_LONG).show();
        }
    }
}
