package com.example.bin.productmanagement.Demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.MViewHolder> {
    ArrayList<Product> list_prd;
    Context context;
    View view;

    @NonNull
    @Override
    public ProductDetailAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_total, viewGroup, false);
        return new ProductDetailAdapter.MViewHolder(view);
    }

    public ProductDetailAdapter(ArrayList<Product> list_prd, Context context) {
        this.list_prd = list_prd;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailAdapter.MViewHolder mViewHolder, int i) {
        DecimalFormat dcf = new DecimalFormat("#,###");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
        dfs.setGroupingSeparator(',');
        dcf.setDecimalFormatSymbols(dfs);

        Product product = list_prd.get(i);
        mViewHolder.txt_prd_id.setText(String.valueOf(i + 1));
        mViewHolder.txt_prd_name.setText(product.getName());
        mViewHolder.txt_prd_point.setText(String.valueOf(product.getPoint()));
        mViewHolder.txt_prd_price.setText(String.valueOf(dcf.format(product.getPrice())) + " đ");
        mViewHolder.txt_prd_amount.setText(String.valueOf(product.getAmount()));
    }

    @Override
    public int getItemCount() {
        return list_prd.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder {
        TextView txt_prd_id, txt_prd_point, txt_prd_price, txt_prd_amount, txt_prd_name;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_prd_amount = itemView.findViewById(R.id.txt_prd_amount);
            txt_prd_id = itemView.findViewById(R.id.txt_prd_id);
            txt_prd_name = itemView.findViewById(R.id.txt_prd_name);
            txt_prd_point = itemView.findViewById(R.id.txt_prd_point);
            txt_prd_price = itemView.findViewById(R.id.txt_prd_price);
        }
    }
}
