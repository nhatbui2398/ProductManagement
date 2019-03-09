package com.example.bin.productmanagement.Demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.adapter.ProductManagementAdapter;
import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.R;

import java.util.ArrayList;

public class ProductManagementActivity extends AppCompatActivity {
    RecyclerView rcvProduct;
    ProductManagementAdapter adapter;
    ImageView btnAdd;
    ArrayList<Product> products;
    ProductDatabase db;

    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_EDIT = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        connectView();
    }

    private void connectView(){
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"ADD",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("PRODUCT_ID",products.get(products.size()-1).getId());
                startActivityForResult(intent,REQUEST_CODE_ADD);
            }
        });
        rcvProduct = findViewById(R.id.rcv_product_management);
        db = new ProductDatabase(this);
        products = db.getAllProduct();
        for (int i = 0; i < products.size(); i++){
            Log.e("Product","id: "+products.get(i).getId()+"\nname: "+products.get(i).getName());
        }
        adapter = new ProductManagementAdapter(this, products,db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvProduct.setLayoutManager(linearLayoutManager);
        rcvProduct.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD:{
                if(resultCode == Activity.RESULT_OK){
                    int productID = data.getIntExtra("PRODUCT_ID",0);
                    products.add(db.getProductByID(productID));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CODE_EDIT:{
                if(resultCode == Activity.RESULT_OK){
                    int posn = data.getIntExtra("PRODUCT_POSITION",0);
                    int productID = data.getIntExtra("PRODUCT_ID",0);
                    products.remove(posn);
                    products.add(posn,db.getProductByID(productID));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }
}
