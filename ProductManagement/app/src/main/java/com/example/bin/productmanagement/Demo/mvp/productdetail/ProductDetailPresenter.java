package com.example.bin.productmanagement.Demo.mvp.productdetail;

import android.content.Context;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.model.Product;

public class ProductDetailPresenter implements ProductDetailContract.Presenter {

    private ProductDatabase database;
    private ProductDetailContract.View view;

    public ProductDetailPresenter(Context context,
                                  ProductDetailContract.View view) {
        this.database = new ProductDatabase(context);
        this.view = view;
    }


    @Override
    public void loadProductById(int id) {
        Product product = database.getProductByID(id);

        // Show Lên View
        view.showProduct(product);
    }
}
