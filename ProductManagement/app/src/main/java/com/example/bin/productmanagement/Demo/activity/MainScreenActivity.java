package com.example.bin.productmanagement.Demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.adapter.ProductAdapter;
import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.Demo.mvp.productdetail.ProductDetailActivity;
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
 */
public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rcvProduct;
    private Button btnTotal;
    private AdView adView;
    private DrawerLayout mDrawerLayout;
    private ImageView btnMenu;

    private ProductDatabase db;
    private ProductAdapter adapter;
    private ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initDB();
        initView();
        myAdsHandle();

    }

    private void initView(){
        rcvProduct = findViewById(R.id.rcv_prd);
        btnTotal = findViewById(R.id.btn_Total);
        products = db.getAllProduct();
        adapter = new ProductAdapter(products,this);
        adView = findViewById(R.id.banner_ads);
        btnMenu = findViewById(R.id.btn_home);
        btnMenu.setOnClickListener(this);
        rcvProduct.setAdapter(adapter);

        mDrawerLayout = findViewById(R.id.drawer);
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                switch (menuItem.getItemId()){
                    case R.id.nav_product_manager:{
                        Toast.makeText(getApplicationContext(),"nav_product_manager",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                return true;
            }
        });
        //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvProduct.setLayoutManager(linearLayoutManager);
        btnTotal.setOnClickListener(this);

        adapter.setOnItemClickListener(new ProductAdapter.MyProductClickListener() {
            @Override
            public void setOnItemClick(Context context, int positon) {
                ProductDetailActivity.showMe((Activity) context, positon);
            }
        });
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

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

        Log.e("Ads Handle","Already");
    }

    private void initDB(){
        db = new ProductDatabase(this);
        if(db.getProductCount() == 0){
            db.initDefaultData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Total:{
                checkChangeActivity();
                break;
            }
            case R.id.btn_home:{
                mDrawerLayout.openDrawer(GravityCompat.START);
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
