package com.example.bin.productmanagement.Demo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.adapter.ProductDetailAdapter;
import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**?
 * Activity xuất hiện sau khi bấm button Tính
 *
 */
public class TotalActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Product> list_Prd;
    TextView txt_Point, txt_Total, txt_Count, txt_finalTotal;
    ImageView btn_back;
    Button btnDiscount25, btnDiscount35, btnDiscount42, btnDiscount50, btnCurrent;
    ProductDatabase db;
    RecyclerView rcv;
    ProductDetailAdapter adapter;
    InterstitialAd mInterstitialAd;
    final String DISCOUNT_25 ="discount_25";
    final String DISCOUNT_35 ="discount_35";
    final String DISCOUNT_42 ="discount_42";
    final String DISCOUNT_50 ="discount_50";

    DecimalFormat dcf;
    DecimalFormatSymbols dfs;

    String mCurrentDisType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        //myAdsHandle();
        setData();
        connectView();

    }

    private void myAdsHandle() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.e("Interstitial ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.e("Interstitial ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.e("Interstitial ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.e("Interstitial ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Log.e("Interstitial ads", "onAdClosed");
            }
        });

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.e("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    private void connectView(){
        txt_Point = findViewById(R.id.txt_Point);
        txt_Total = findViewById(R.id.txt_Total);
        txt_Count = findViewById(R.id.txt_count);
        txt_finalTotal = findViewById(R.id.txt_finalTotal);
        btn_back = findViewById(R.id.btn_back);
        btnDiscount25 = findViewById(R.id.btn_discount_25);
        btnDiscount35 = findViewById(R.id.btn_discount_35);
        btnDiscount42 = findViewById(R.id.btn_discount_42);
        btnDiscount50 = findViewById(R.id.btn_discount_50);
        rcv = findViewById(R.id.rcv_total);
        btn_back.setOnClickListener(this);
        btnDiscount25.setOnClickListener(this);
        btnDiscount35.setOnClickListener(this);
        btnDiscount42.setOnClickListener(this);
        btnDiscount50.setOnClickListener(this);

        cashOrders();
    }

    private void changeTypeDiscount(Button selectionBtnDis, String selectionDisID){
        if(mCurrentDisType.equals(selectionDisID)){
            return;
        }
        else {
            if(btnCurrent!=null){
                btnCurrent.setBackgroundColor(getResources().getColor(R.color.white20));
            }
            selectionBtnDis.setBackgroundColor(getResources().getColor(R.color.black));
            btnCurrent = findViewById(selectionBtnDis.getId());
            mCurrentDisType = selectionDisID;
            //selectionBtnDis.setBackgroundColor();
        }
    }

    private double getDiscountPrice(Product product){
        switch (mCurrentDisType){
            case DISCOUNT_25:{
                return product.getDiscount1();
            }
            case DISCOUNT_35:{
                return product.getDiscount2();
            }
            case DISCOUNT_42:{
                return product.getDiscount3();
            }
            case DISCOUNT_50:{
                return product.getDiscount4();
            }
            default:{
                return product.getPrice();
            }
        }
    }

    private void changeResultDiscount(){
        formatCurrent();
        double price2 = 0;
        if(db.getCountProductTotal() > 0){
            for(int i = 0; i < list_Prd.size(); i++){
                Product pro = list_Prd.get(i);
                price2 += getDiscountPrice(pro)*pro.getAmount();
            }
            price2 += 88000;
            txt_finalTotal.setText(String.valueOf(dcf.format((Math.round(price2*100)/100))) + " đ");
        }
    }

    private void formatCurrent() {
        dcf = new DecimalFormat("#,###");
        dfs = new DecimalFormatSymbols(Locale.getDefault());
        dfs.setGroupingSeparator(',');
        dcf.setDecimalFormatSymbols(dfs);
    }

    private void setData(){
        db = new ProductDatabase(this);
        list_Prd = db.getProduct();
        for(int i = 0; i < list_Prd.size();i++){
            Product product = list_Prd.get(i);
            Log.e("TotalActivity","\nProduct: "+product.getName()+"\n"
                    +product.getPoint()+"\n"+product.getPrice()
                    +"\n"+product.getDiscount1()+"\n"+product.getDiscount2()
                    +"\n"+product.getDiscount3()+"\n"+product.getDiscount4()+"\n"+product.getAmount());
        }

    }

    private void cashOrders(){
        formatCurrent();
        if(list_Prd.size() > 0){
            int count = 0;
            double point = 0, price1 = 0, price2 = 0;
            for(int i = 0; i < list_Prd.size(); i++){
                Product pro = list_Prd.get(i);
                point += pro.getPoint();
                price1 += pro.getPrice()*pro.getAmount();
                price2 += getDiscountPrice(pro)*pro.getAmount();
                Log.e("Cashing","Type Discount: "+mCurrentDisType+" discount price: "+getDiscountPrice(pro));
                count += pro.getAmount();
            }
            price2 += 88000;
            txt_Total.setText(String.valueOf(dcf.format(Math.round(price1*100)/100)) + " đ");
            txt_Point.setText(String.valueOf(Math.round(point*100)/100));
            txt_finalTotal.setText(String.valueOf(dcf.format(Math.round(price2*100)/100)) + " đ");
            txt_Count.setText(String.valueOf(count));
        }

        adapter = new ProductDetailAdapter(list_Prd, this);
        rcv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:{
                finish();
                break;
            }
            case R.id.btn_discount_25:{
                changeTypeDiscount(btnDiscount25, DISCOUNT_25);
                changeResultDiscount();
                break;
            }
            case R.id.btn_discount_35:{
                changeTypeDiscount(btnDiscount35, DISCOUNT_35);
                changeResultDiscount();
                break;
            }
            case R.id.btn_discount_42:{
                changeTypeDiscount(btnDiscount42, DISCOUNT_42);
                changeResultDiscount();
                break;
            }
            case R.id.btn_discount_50:{
                changeTypeDiscount(btnDiscount50, DISCOUNT_50);
                changeResultDiscount();
                break;
            }
        }
    }
}
