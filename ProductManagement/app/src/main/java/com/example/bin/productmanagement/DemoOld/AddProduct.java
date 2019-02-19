package com.example.bin.productmanagement.DemoOld;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bin.productmanagement.R;


public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtPrice, edtDesc;
    Button btnAdd, btn_Cancel;
    TextView txtID;
    MyDatabase db;
    int prodID = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        connectView();
    }

    private void connectView(){
        edtDesc = findViewById(R.id.edtProdDesc);
        edtName = findViewById(R.id.edtProdName);
        edtPrice = findViewById(R.id.edtProdPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btn_Cancel = findViewById(R.id.btnCancel);
        txtID = findViewById(R.id.txtID);
        db = new MyDatabase(this);

        btnAdd.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);

        getProdID();
    }

    private void getProdID(){
        Intent intent = getIntent();
        if(intent!=null) {
            prodID = intent.getIntExtra("PRODUCT_ID", 0);
            txtID.setText(String.valueOf(prodID));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd: {
                long numberOfRows = db.addProduct(getProduct());
                Product product= db.getProductByID(prodID);
                Log.e("","Row number: "+numberOfRows+"\nProduct ID: "+product.getID()
                        +"\n\t Song Name: "+product.getName()
                        +"\n\t URL: "+product.getDesc()
                        +"\n\t Thumbnail: "+product.getPrice()
                );
                Intent intent = MainActivity.getInstance().sendData(product.getID(), product.getName(), product.getDesc(), product.getPrice());
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            }
            case R.id.btnCancel: {
                finish();
                break;
            }
        }
    }
    private Product getProduct(){

        Product product = new Product (prodID,edtName.getText().toString(),edtDesc.getText().toString(),"...",Double.parseDouble(edtPrice.getText().toString()));
        return product;
    }
}
