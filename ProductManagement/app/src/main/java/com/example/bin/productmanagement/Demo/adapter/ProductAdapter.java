package com.example.bin.productmanagement.Demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bin.productmanagement.Demo.model.MProduct;
import com.example.bin.productmanagement.R;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MViewHolder> {
    ArrayList<MProduct> listPrd;
    View view;
    Context context;
    int amountBefore = -1, amountAfter = -1;
    //String
    @NonNull
    @Override
    public ProductAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_product, viewGroup,false);
        return new MViewHolder(view);
    }
    public ProductAdapter(ArrayList<MProduct> listPrd, Context context){
        this.listPrd = listPrd;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.MViewHolder mViewHolder, final int i) {
        final MProduct product = listPrd.get(i);
        mViewHolder.txt_ID.setText(String.valueOf(product.getId()));
        mViewHolder.txt_Name.setText(product.getName());
        if(product.getAmount() > 0){
            mViewHolder.edt_Amt.setText(String.valueOf(product.getAmount()));
        }
        else {
            mViewHolder.edt_Amt.setText("0");
        }

        mViewHolder.edt_Amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)){
                    mViewHolder.edt_Amt.setText("0");
                    product.setAmount(0);
                }
                else {
                    product.setAmount(Integer.parseInt(s.toString()));
                }
            }
        });
    }

    public static void main(String[] agrs){

    }

    @Override
    public int getItemCount() {
        return listPrd.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder {
        TextView txt_ID;
        TextView txt_Name;
        EditText edt_Amt;
        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_Name = itemView.findViewById(R.id.txt_prdName);
            txt_ID = itemView.findViewById(R.id.txt_prdID);
            edt_Amt = itemView.findViewById(R.id.edt_prdAmt);
        }
    }
}
