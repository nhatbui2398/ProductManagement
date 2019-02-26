package com.example.bin.productmanagement.Demo.activity;

import android.content.Context;
import android.util.Log;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.model.MProduct;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class MainScreenPresenter {

    private ProductDatabase db;


    public void initDatabase(Context context){
        db = new ProductDatabase(context);
        if(db.getProductCount() == 0){
            db.initDefaultData();
        }


    }


    public ArrayList<MProduct> loadData(){
        return db.getAllProduct();
    }

    public void loadAds(Context context){

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

    }

}

