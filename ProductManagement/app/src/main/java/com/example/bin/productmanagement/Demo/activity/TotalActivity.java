package com.example.bin.productmanagement.Demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.adapter.ProductDetailAdapter;
import com.example.bin.productmanagement.Demo.model.MProduct;
import com.example.bin.productmanagement.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

/**?
 * Activity khi bấm button Tính
 *
 * Thêm Ad xen kẽ khi chuyển activity
 */
public class TotalActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MProduct> list_Prd;
    TextView txt_Point, txt_Total, txt_Count, txt_finalTotal;
    ImageView btn_back;
    ProductDatabase db;
    RecyclerView rcv;
    ProductDetailAdapter adapter;

    int count = 0;
    double point = 0, price1 = 0, price2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        setData();
        connectView();

    }

    private void connectView(){
        txt_Point = findViewById(R.id.txt_Point);
        txt_Total = findViewById(R.id.txt_Total);
        txt_Count = findViewById(R.id.txt_count);
        txt_finalTotal = findViewById(R.id.txt_finalTotal);
        btn_back = findViewById(R.id.btn_back);
        rcv = findViewById(R.id.rcv_total);
        btn_back.setOnClickListener(this);





        setTotal();
    }

    private void setData(){
        db = new ProductDatabase(this);
        list_Prd = db.getProduct();
        for(int i = 0; i < list_Prd.size();i++){
            MProduct product = list_Prd.get(i);
            Log.e("TotalActivity","\nProduct: "+product.getName()+"\n"
                    +product.getPoint()+"\n"+product.getPrice()
                    +"\n"+product.getDiscount1()+"\n"+product.getDiscount2()
                    +"\n"+product.getDiscount3()+"\n"+product.getDiscount4()+"\n"+product.getAmount());
        }

    }

    private void setTotal(){
        count = db.getCountProductTotal();
        if(count > 0){
            count = 0;
            for(int i = 0; i < list_Prd.size(); i++){
                MProduct pro = list_Prd.get(i);
                point += pro.getPoint();
                price1 += pro.getPrice()*pro.getAmount();
                price2 += pro.getDiscount1()*pro.getAmount();
                count += pro.getAmount();
            }
            price2 += 88000;
            txt_Total.setText(String.valueOf(Math.round(price1*100)/100));
            txt_Point.setText(String.valueOf(Math.round(point*100)/100));
            txt_finalTotal.setText(String.valueOf(Math.round(price2*100)/100));
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
                Intent intent = new Intent(this, MainScreenActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    /*private String editCurrency(double numb){
        int i = 1;
        String s = String.valueOf(Math.round(numb*10)/10);
        do {
            i++;
        }while (s.length() > 3 * i);
        if(i-1>0){
            for(int j = i;j>0;j--){
            }
        }
    }*/
}
