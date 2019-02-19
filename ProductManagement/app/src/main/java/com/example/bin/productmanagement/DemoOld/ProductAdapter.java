package com.example.bin.productmanagement.DemoOld;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bin.productmanagement.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVH> {
        ArrayList<Product> listProduct;
        Activity context;
        View view;
        MyDatabase db;

@NonNull
@Override
public ProductAdapter.ProductVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.product_item, viewGroup,false);
        return new ProductVH(view);
        }

public ProductAdapter(ArrayList<Product> listProduct, Activity context, MyDatabase db){
        this.listProduct = listProduct;
        this.context = context;
        this.db = db;
        }

@Override
public void onBindViewHolder(@NonNull ProductAdapter.ProductVH productVH, final int i) {
    final Product product = listProduct.get(i);
    productVH.txtName.setText(product.getName());
    productVH.txtPrice.setText(String.valueOf(product.getPrice()));
    productVH.btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            acceptDelete(product, i);
        }
    });
    productVH.btnEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EditProduct.class);
            Bundle bundle = new Bundle();
            bundle.putInt("SONG_ID", product.getID());
            intent.putExtra("Bundle", bundle);
            //context.startActivity(intent);
            context.startActivityForResult(intent, MainActivity.REQUEST_CODE_EDIT);
        }
    });
}

private void acceptDelete(final Product product, final int i){
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
        deleteDialog.setMessage("Bạn muốn xóa bài "+product.getName()+" không ?");
        deleteDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
        db.deleteProduct(product.getID());
        listProduct.remove(i);
        notifyDataSetChanged();
        }
        });
        deleteDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {

        }
        });
        deleteDialog.show();
        }

@Override
public int getItemCount() {
        return listProduct.size();
        }

public class ProductVH extends RecyclerView.ViewHolder {
    TextView txtName, txtPrice;
    ImageView imgThumb;
    ImageView btnEdit, btnDelete;
    public ProductVH(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtPrice = itemView.findViewById(R.id.txtPrice);
        imgThumb = itemView.findViewById(R.id.img_thumb);
        btnDelete = itemView.findViewById(R.id.btnDelete);
        btnEdit= itemView.findViewById(R.id.btnEdt);
    }
}
}