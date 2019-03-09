package com.example.bin.productmanagement.Demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.R;

public class EditProductActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtPoint, edtPrice, edtDis25, edtDis35, edtDis42, edtDis50;
    TextView txtID;
    Button btnCancel, btnEdit;
    ProductDatabase db;
    int productId, posn;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        connectView();
    }

    private void connectView() {
        edtDis25 = findViewById(R.id.edt_price_dis_25);
        edtDis35 = findViewById(R.id.edt_price_dis_35);
        edtDis42 = findViewById(R.id.edt_price_dis_42);
        edtDis50 = findViewById(R.id.edt_price_dis_50);
        edtName = findViewById(R.id.edt_product_name);
        edtPoint = findViewById(R.id.edt_product_point);
        edtPrice = findViewById(R.id.edt_product_price);
        txtID = findViewById(R.id.txt_product_id);
        btnCancel = findViewById(R.id.btn_cancel);
        btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        initData();

    }

    private void initData() {
        db = new ProductDatabase(this);
        Intent intent = getIntent();
        if(intent!=null) {
            posn = intent.getIntExtra("PRODUCT_POSITION",0);
            productId = intent.getIntExtra("PRODUCT_ID", 0);
            txtID.setText(String.valueOf(productId));
        }
        product = db.getProductByID(productId);

        edtName.setText(product.getName());
        edtPoint.setText(String.valueOf(product.getPoint()));
        edtPrice.setText(String.valueOf(product.getPrice()));
        edtDis25.setText(String.valueOf(product.getDiscount1()));
        edtDis35.setText(String.valueOf(product.getDiscount2()));
        edtDis42.setText(String.valueOf(product.getDiscount3()));
        edtDis50.setText(String.valueOf(product.getDiscount4()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel: {
                finish();
                break;
            }
            case R.id.btn_edit: {
                if (isEmptyInfo()) {
                    if(db.updateProduct(getNewProduct())>0){
                        Intent intent = new  Intent();
                        intent.putExtra("PRODUCT_POSITION",posn);
                        intent.putExtra("PRODUCT_ID",productId);
                        setResult(Activity.RESULT_OK,intent);
                    }
                    else {
                        setResult(Activity.RESULT_CANCELED);
                    }
                    finish();
                }
            }
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

    private boolean isEmptyInfo () {
        if (TextUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(this, R.string.IsEmptyInfo, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtPoint.getText().toString())) {
            Toast.makeText(this, R.string.IsEmptyInfo, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(this, R.string.IsEmptyInfo, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtDis25.getText().toString())) {
            Toast.makeText(this, R.string.IsEmptyInfo, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtDis35.getText().toString())) {
            Toast.makeText(this, R.string.IsEmptyInfo, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtDis42.getText().toString())) {
            Toast.makeText(this, R.string.IsEmptyInfo, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtDis50.getText().toString())) {
            Toast.makeText(this, R.string.IsEmptyInfo, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
