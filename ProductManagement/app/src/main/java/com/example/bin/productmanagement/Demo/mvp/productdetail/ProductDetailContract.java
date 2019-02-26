package com.example.bin.productmanagement.Demo.mvp.productdetail;

import com.example.bin.productmanagement.Demo.model.MProduct;

public interface ProductDetailContract {

    public interface View{
        public void showProduct(MProduct product);

    }

    public interface Presenter{
        public void loadProductById(int id);
    }
}
