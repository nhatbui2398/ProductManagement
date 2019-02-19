package com.example.bin.productmanagement.DemoOld;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.bin.productmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class EditProduct extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtPrice, edtDesc;
    ImageView btnPrv,btnNext;
    Button btnUpdate, btnCancel;
    Spinner spnID;
    ArrayList<Product> listProduct;
    List<Integer> listID;
    MyDatabase db;
    int posn, productID;
    private static final String TAG = "EditProduct";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        connectView();
    }
    private void connectView(){
        edtDesc = findViewById(R.id.edtProdDesc);
        edtName = findViewById(R.id.edtProdName);
        edtPrice = findViewById(R.id.edtProdPrice);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        btnPrv = findViewById(R.id.btn_prv);
        btnNext = findViewById(R.id.btn_next);
        spnID = findViewById(R.id.spn_ID);

        setSpnData();
        setDefault();

        btnNext.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnPrv.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    private void setSpnData() {
        listID = new ArrayList<>();
        db = new MyDatabase(this);

        listProduct = db.getProduct();
        if (listProduct != null && listProduct.size() > 0) {
            for (int i = 0; i < listProduct.size(); i++) {
                listID.add(listProduct.get(i).getID());
                Log.e("Product","\nProduct ID: "+listProduct.get(i).getID()
                        +"\n\t Song Name: "+listProduct.get(i).getName()
                        +"\n\t URL: "+listProduct.get(i).getDesc()
                        +"\n\t Thumbnail: "+listProduct.get(i).getPrice()
                );
            }
        }

        ArrayAdapter adapterID = new ArrayAdapter(this, R.layout.spn_item, R.id.txt_spnID, listID);
        spnID.setAdapter(adapterID);
        posn = spnID.getSelectedItemPosition();
        Log.e(TAG, "Position = " + posn);
        spnID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posn = position;
                Product product = listProduct.get(posn);
                selectionHandler(product);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectionHandler(Product product) {
        productID = product.getID();
        edtName.setText(product.getName());
        edtPrice.setText(String.valueOf(product.getPrice()));
        edtDesc.setText(product.getDesc());

        for (int i = 0; i < listID.size(); i++) {
            if (listProduct.get(i).getID() == productID) {
                spnID.setSelection(i);
                break;
            }
        }
    }

    private void setDefault(){
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        if(bundle!=null) {
            productID = bundle.getInt("SONG_ID");
            Product product = db.getProductByID(productID);
            selectionHandler(product);
            return;
        }
        else {
            Product product = listProduct.get(0);
            selectionHandler(product);
        }
    }

    private Product getProduct(){
        Product product = new Product (productID,edtName.getText().toString(),edtDesc.getText().toString(),"...",Double.parseDouble(edtPrice.getText().toString()));
        return product;
    }

    private void acceptUpdate(){
        AlertDialog.Builder updateDialog = new AlertDialog.Builder(this);
        updateDialog.setMessage("Đồng ý cập nhật");
        updateDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //song before update
                Product songBefore = db.getProductByID(productID);
                Log.e("Product","\nProduct ID: "+songBefore.getID()
                        +"\n\t Song Name: "+songBefore.getName()
                        +"\n\t URL: "+songBefore.getDesc()
                        +"\n\t Thumbnail: "+songBefore.getPrice()
                );
                int numberOfRows = db.updateProduct(getProduct());
                //song after update
                Product product= db.getProductByID(productID);
                Log.e(TAG,"\nPosition: "+posn+"Row number: "+numberOfRows+"\nProduct ID: "+product.getID()
                        +"\n\t Song Name: "+product.getName()
                        +"\n\t URL: "+product.getDesc()
                        +"\n\t Thumbnail: "+product.getPrice()
                );
                Intent intent = MainActivity.getInstance().sendId(posn,productID);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
        updateDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        updateDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUpdate:{
                acceptUpdate();
                break;
            }
            case R.id.btnCancel:{
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
            }
            case R.id.btn_prv:{
                if(posn>0){
                    posn--;

                }
                else {
                    posn = listProduct.size()-1;
                }
                Product product = listProduct.get(posn);
                selectionHandler(product);
                break;
            }
            case R.id.btn_next:{
                if(posn<listProduct.size()-1){
                    posn++;
                }
                else {
                    posn = 0;
                }
                Product product = listProduct.get(posn);
                selectionHandler(product);
                break;
            }
        }
    }
}
