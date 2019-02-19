package com.example.bin.productmanagement.DemoOld;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bin.productmanagement.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProductAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Product> listProduct;
    MyDatabase db;
    ImageView btnAdd, btnSearch;
    Button btnShowAll;
    EditText edtSearch;
    TextView txtCount;
    /*Button btnCheck;*/
    int posn = -1;
    public static final int REQUEST_CODE_EDIT = 100;
    public static final int REQUEST_CODE_ADD = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDB();
        connectView();
    }

    private void initDB(){
        db = new MyDatabase(this);
        if(db.getProductCount() == 0){
            db.initDefaultData();
        }
    }

    private void connectView() {
        // btnCheck = findViewById(R.id.btnCheck);
        txtCount = findViewById(R.id.txtCount);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnShowAll = findViewById(R.id.btnShowAll);
        btnAdd = findViewById(R.id.btnAddMusic);
        recyclerView = findViewById(R.id.rcv_albumMusic);
        listProduct = db.getProduct();
        adapter = new ProductAdapter(listProduct, this, db);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                   // searchHandle();
                } else {
                   // listProduct = db.getProduct();
                    //getSong(listSong);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnAdd.setOnClickListener(this);
        btnShowAll.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    private static MainActivity instance;

    public static MainActivity getInstance() {
        if (instance == null) {
            instance = new MainActivity();
        }
        return instance;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddMusic: {
                Intent intent = new Intent(this, AddProduct.class);
                intent.putExtra("PRODUCT_ID", listProduct.get(listProduct.size() - 1).getID()+1);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                break;
            }
        }
    }

    public Intent sendId(int posn, int id){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("POSN", posn);
        bundle.putInt("PRODUCT_ID", id);
        intent.putExtra("DATA", bundle);
        return intent;
    }

    public Intent sendData(int id, String name, String desc, Double price){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("PRODUCT_ID", id);
        bundle.putString("PRODUCT_NAME", name);
        bundle.putString("PRODUCT_DESC", desc);
        bundle.putDouble("PRODUCT_PRICE", price);
        intent.putExtra("DATA", bundle);
        return intent;
    }

    private Product getData(Intent data) {
        Bundle bundle = data.getBundleExtra("DATA");
        this.posn = bundle.getInt("POSN");
        int productID = bundle.getInt("PRODUCT_ID");

        return db.getProductByID(productID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                Product product = getData(data);
                listProduct.remove(posn);
                listProduct.add(posn, product);
                adapter.notifyDataSetChanged();
                Log.e("EditMain", "\nPosition: " + posn + "\tPRODUCT_NAME"
                        + product.getName() + "\nPRODUCT_DESC" + product.getDesc()+ "\nPRODUCT_PRICE" + product.getPrice());
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
        if (requestCode == REQUEST_CODE_ADD) {
            if (resultCode == Activity.RESULT_OK) {
                Product product = getData(data);
                posn = listProduct.size();
                Log.e("Result", "Posn: " + posn);
                listProduct.add(product);
                Log.e("Result", "List size: " + listProduct.size() + "\nOK");
                adapter.notifyDataSetChanged();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("Result", "Canceled");
            }
        }
    }
}
