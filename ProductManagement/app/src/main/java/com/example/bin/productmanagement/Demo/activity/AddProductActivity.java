package com.example.bin.productmanagement.Demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.Util.AnimationUtil;
import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.R;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtPoint, edtPrice, edtDis25, edtDis35, edtDis42, edtDis50;
    TextView txtID;
    Button btnCancel, btnAdd;
    int productId;
    ProductDatabase db;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        connectView();
    }
    private void connectView(){
        linearLayout = findViewById(R.id.layoutLinear);
        edtDis25 = findViewById(R.id.edt_price_dis_25);
        edtDis35 = findViewById(R.id.edt_price_dis_35);
        edtDis42 = findViewById(R.id.edt_price_dis_42);
        edtDis50 = findViewById(R.id.edt_price_dis_50);
        edtName = findViewById(R.id.edt_product_name);
        edtPoint = findViewById(R.id.edt_product_point);
        edtPrice = findViewById(R.id.edt_product_price);
        txtID = findViewById(R.id.txt_product_id);
        btnCancel = findViewById(R.id.btn_cancel);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        initData();

    }

    private void initData(){
        db = new ProductDatabase(this);
        Intent intent = getIntent();
        if(intent!=null) {
            productId = intent.getIntExtra("PRODUCT_ID", 0)+1;
            txtID.setText(String.valueOf(productId));
        }
    }


    private Product getNewProduct(){
        int id = Integer.parseInt(txtID.getText().toString());
        String name = edtName.getText().toString();
        double point = Double.parseDouble(edtPoint.getText().toString());
        double price = Double.parseDouble(edtPrice.getText().toString());
        double dis25 = Double.parseDouble(edtDis25.getText().toString());
        double dis35 = Double.parseDouble(edtDis35.getText().toString());
        double dis42 = Double.parseDouble(edtDis42.getText().toString());
        double dis50 = Double.parseDouble(edtDis50.getText().toString());
        return new Product(id,name,price,point,dis25,dis35,dis42,dis50,0);
    }

    private boolean checkEmptyInfo(){
        if(TextUtils.isEmpty(edtName.getText().toString())){
            AnimationUtil.shakeView(edtName, this);
            Snackbar.make(linearLayout, R.string.IsEmptyInfo, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(edtPoint.getText().toString())){
            AnimationUtil.shakeView(edtPoint, this);
            Snackbar.make(linearLayout, R.string.IsEmptyInfo, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(edtName.getText().toString())){
            AnimationUtil.shakeView(edtName, this);
            Snackbar.make(linearLayout, R.string.IsEmptyInfo, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(edtDis25.getText().toString())){
            AnimationUtil.shakeView(edtDis25, this);
            Snackbar.make(linearLayout, R.string.IsEmptyInfo, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(edtDis35.getText().toString())){
            AnimationUtil.shakeView(edtDis35, this);
            Snackbar.make(linearLayout, R.string.IsEmptyInfo, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(edtDis42.getText().toString())){
            AnimationUtil.shakeView(edtDis42, this);
            Snackbar.make(linearLayout, R.string.IsEmptyInfo, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(edtDis50.getText().toString())){
            AnimationUtil.shakeView(edtDis50, this);
            Snackbar.make(linearLayout, R.string.IsEmptyInfo, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:{
                finish();
                break;
            }
            case R.id.btn_add:{
                if(checkEmptyInfo()){
                    if(db.addProduct(getNewProduct()) >= 0){
                        Intent intent = new Intent();
                        intent.putExtra("PRODUCT_ID",productId);
                        setResult(Activity.RESULT_OK,intent);
                    }
                    else {
                        setResult(Activity.RESULT_CANCELED);
                    }
                    finish();
                }
                break;
            }
        }
    }
}
