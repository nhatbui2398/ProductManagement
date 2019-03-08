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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bin.productmanagement.Demo.model.Product;
import com.example.bin.productmanagement.R;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MViewHolder> {
    ArrayList<Product> listPrd;
    View view;
    Context context;
    @NonNull
    @Override
    public ProductAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_product, viewGroup,false);
        return new MViewHolder(view);
    }
    public ProductAdapter(ArrayList<Product> listPrd, Context context){
        this.listPrd = listPrd;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.MViewHolder mViewHolder, final int i) {
        final Product product = listPrd.get(i);
        mViewHolder.txt_ID.setText(String.valueOf(product.getId()));
        mViewHolder.txt_Name.setText(product.getName());
        if(product.getAmount() > 0){
            mViewHolder.edt_Amt.setText(String.valueOf(product.getAmount()));
        }
        else {
            mViewHolder.edt_Amt.setText("0");
        }

        mViewHolder.txt_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProductClickListener.setOnItemClick(context,i);
            }
        });

        mViewHolder.edt_Amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //s = current text, start = position input, count = số ký tự bị xóa, after = số ký tự thêm vào
                Log.e("beforeTextChanged","Current text: "+mViewHolder.edt_Amt.getText().toString()
                        +"\ns : "+s+"\nstart : "+start+"\ncount : "+count+"\nafter : "+after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //s = current text, start = position input,
                // before = 0, nếu xóa thì before = số char trước đó, count = số lượng char nhập vào
                Log.e("onTextChanged","Current text: "+mViewHolder.edt_Amt.getText().toString()
                        +"\ns : "+s+"\nstart : "+start+"\nbefore : "+before+"\ncount : "+count);

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("afterTextChanged","Editable : "+s.toString());
                if (TextUtils.isEmpty(s)){
                    mViewHolder.edt_Amt.setText("0");
                    product.setAmount(0);
                }
                else {
                    product.setAmount(Integer.parseInt(s.toString()));
                }
            }
        });

        mViewHolder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmptyAmount(mViewHolder.edt_Amt.getText().toString()) && Integer.parseInt(mViewHolder.edt_Amt.getText().toString())-1>=0 ){
                    product.setAmount(product.getAmount()-1);
                    mViewHolder.edt_Amt.setText(String.valueOf(product.getAmount()));
                }
            }
        });

        mViewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setAmount(product.getAmount()+1);
                mViewHolder.edt_Amt.setText(String.valueOf(product.getAmount()));
            }
        });


    }

    public interface MyProductClickListener{
        void setOnItemClick(Context context, int positon);
    }
    private boolean isEmptyAmount(String s){
        return s.equals("0")||TextUtils.isEmpty(s);
    }

    MyProductClickListener myProductClickListener;

    public void setOnItemClickListener(MyProductClickListener myProductClickListener){
        this.myProductClickListener = myProductClickListener;
    }
    @Override
    public int getItemCount() {
        return listPrd.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder {
        TextView txt_ID;
        TextView txt_Name;
        EditText edt_Amt;
        ImageView btnSub, btnPlus;
        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_Name = itemView.findViewById(R.id.txt_prdName);
            txt_ID = itemView.findViewById(R.id.txt_prdID);
            edt_Amt = itemView.findViewById(R.id.edt_prdAmt);
            btnSub = itemView.findViewById(R.id.btn_sub);
            btnPlus = itemView.findViewById(R.id.btn_plus);
        }
    }
}
