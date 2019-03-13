package com.example.bin.productmanagement.Demo.mvp.productdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.R;

public class ProductDetailActivity extends Activity implements ProductDetailContract.View {

    private static final String INTENT_PRODUCT_ID = "INTENT_PRODUCT_ID";
    TextView txtProductID, txtProductName, txtProductPoint, txtProductPrice;
    ImageView btnBack;
    private ProductDetailPresenter presenter;


    public static void showMe(Activity activity, int productId){

        Intent intent = new Intent(activity, ProductDetailActivity.class);
        intent.putExtra(INTENT_PRODUCT_ID, productId);
        activity.startActivity(intent);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_2);
        connectinitView();
        int productId = getIntent().getIntExtra(INTENT_PRODUCT_ID, 0);
        presenter = new ProductDetailPresenter(this, this);
        presenter.loadProductById(productId+1);
    }

    private void connectinitView(){
        txtProductID = findViewById(R.id.txt_product_id);
        txtProductName = findViewById(R.id.txt_product_name);
        txtProductPoint = findViewById(R.id.txt_product_point);
        txtProductPrice = findViewById(R.id.txt_product_price);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showProduct(Product product) {

        Toast.makeText(this, "Ban dang show " + product.getName(), Toast.LENGTH_LONG).show();
        txtProductID.setText(String.valueOf(product.getId()));
        txtProductName.setText(product.getName());
        txtProductPoint.setText(String.valueOf(product.getPoint()));
        txtProductPrice.setText(String.valueOf(product.getPrice()));

    }
}
