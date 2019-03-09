package com.example.bin.productmanagement.Demo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bin.productmanagement.Demo.Database.ProductDatabase;
import com.example.bin.productmanagement.Demo.activity.EditProductActivity;
import com.example.bin.productmanagement.Demo.activity.ProductManagementActivity;
import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.R;

import java.util.ArrayList;

public class ProductManagementAdapter extends RecyclerView.Adapter<ProductManagementAdapter.ViewHolder> {
    ArrayList<Product> products;

    Activity context;
    ProductDatabase db;
    @NonNull
    @Override
    public ProductManagementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_management,viewGroup,false);
        return new ViewHolder(view);
    }

    public ProductManagementAdapter(Activity context, ArrayList<Product> products, ProductDatabase db){
        this.context = context;
        this.products = products;
        this.db = db;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductManagementAdapter.ViewHolder viewHolder, final int i) {
        final Product product = products.get(i);
        viewHolder.txtNo.setText(String.valueOf(i+1));
        viewHolder.txtName.setText(String.valueOf(product.getName()));
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setMessage("Bạn có muốn xóa sản phẩm '"+product.getName()+"' không?");
                deleteDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        products.remove(product);
                        notifyDataSetChanged();
                        db.deleteProduct(product.getId());
                    }
                });
                deleteDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                deleteDialog.show();
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"EDIT",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,EditProductActivity.class);
                intent.putExtra("PRODUCT_ID",product.getId());
                intent.putExtra("PRODUCT_POSITION",i);
                context.startActivityForResult(intent,ProductManagementActivity.REQUEST_CODE_EDIT);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNo, txtName;
        ImageView btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNo = itemView.findViewById(R.id.txt_no);
            txtName = itemView.findViewById(R.id.txt_name);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
