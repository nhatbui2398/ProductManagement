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
import com.example.bin.productmanagement.R;

import java.util.ArrayList;


/**
 * Đây là Class Activity Đầu tiên khi vào sử dụng app
 */
public class MainScreen extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rcv_Product;
    Button btn_Total;
    static final int REQUEST_CODE_TOTAL = 100;

    ProductDatabase db;
    ProductAdapter adapter;
    ArrayList<MProduct> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initDB();
        initView();
    }

    private void initView(){
        rcv_Product = findViewById(R.id.rcv_prd);
        btn_Total = findViewById(R.id.btn_Total);
        products = db.getAllProduct();
        adapter = new ProductAdapter(products,this);
        rcv_Product.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcv_Product.setLayoutManager(linearLayoutManager);
        btn_Total.setOnClickListener(this);
    }

    private void initDB(){
        db = new ProductDatabase(this);
        if(db.getProductCount() == 0){
            db.initDefaultData();
        }
    }

    private static MainScreen instance;

    public static MainScreen getInstance() {
        if (instance == null) {
            instance = new MainScreen();
        }
        return instance;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Total:{
                Intent intent = new Intent(this, Total.class);
                for(int i = 0; i<products.size();i++){
                    if(products.get(i).getAmount() != db.getProductAmount(products.get(i).getId())){
                        db.updateProductAmount(products.get(i).getAmount(),products.get(i).getId());
                    }
                    Log.e("Amount",""+products.get(i).getId()+"\n"+products.get(i).getAmount());
                }
                if(db.getCountProductTotal() > 0)
                    startActivity(intent);
                else {
                    Toast.makeText(this,"Phải nhập ít nhất 1 sản phẩm",Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
