package com.example.bin.productmanagement.Demo.mvp.productdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.bin.productmanagement.Demo.model.MProduct;
import com.example.bin.productmanagement.R;

public class ProductDetailActivity extends Activity implements ProductDetailContract.View {

    private static final String INTENT_PRODUCT_ID = "INTENT_PRODUCT_ID";


    private ProductDetailPresenter presenter;


    public static void showMe(Activity activity, int productId){

        Intent intent = new Intent(activity, ProductDetailActivity.class);
        intent.putExtra(INTENT_PRODUCT_ID, productId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_screen);


        int productId = getIntent().getIntExtra(INTENT_PRODUCT_ID, 0);
        presenter = new ProductDetailPresenter(this, this);
        presenter.loadProductById(productId);

    }

    @Override
    public void showProduct(MProduct product) {

        Toast.makeText(this, "Ban dang show " + product.getName(), Toast.LENGTH_LONG).show();


    }
}
