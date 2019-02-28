package com.example.bin.productmanagement.Demo.mvp.productdetail;


import com.example.bin.productmanagement.Demo.model.Product;

public interface ProductDetailContract {

    public interface View{
        public void showProduct(Product product);

    }

    public interface Presenter{
        public void loadProductById(int id);
    }
}
