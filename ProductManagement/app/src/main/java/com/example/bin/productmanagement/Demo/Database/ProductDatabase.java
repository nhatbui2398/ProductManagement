package com.example.bin.productmanagement.Demo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bin.productmanagement.Demo.model.MProduct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProductDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final String FILENAME = "Product10.sql";
    //Database
    private static final String DATABASE_NAME= "ProductManagement";
    private static int DATABASE_VERSION = 3;

    //Table product
    private static final String TABLE_PRODUCT = "Product";
    private static final String COL_PRODUCT_ID = "Product_ID";
    private static final String COL_PRODUCT_NAME = "Product_Name";
    private static final String COL_PRODUCT_POINT = "Product_Point";
    private static final String COL_PRODUCT_PRICE = "Product_Price";
    private static final String COL_PRODUCT_DIS1 = "Product_Discount1";
    private static final String COL_PRODUCT_DIS2 = "Product_Discount2";
    private static final String COL_PRODUCT_DIS3 = "Product_Discount3";
    private static final String COL_PRODUCT_DIS4 = "Product_Discount4";
    private static final String COL_PRODUCT_AMT = "Product_Amount";
    Context mContext;
    //
    public ProductDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        initProductTable(db);
        Log.e(TAG,"onCreate");
    }

    private void initProductTable(SQLiteDatabase db){
        String query =" CREATE TABLE "+TABLE_PRODUCT+"( "+COL_PRODUCT_ID+" INTEGER NOT NULL,"
                +COL_PRODUCT_NAME+" TEXT NOT NULL,"
                +COL_PRODUCT_PRICE+" REAL,"
                +COL_PRODUCT_POINT+ " REAL,"
                +COL_PRODUCT_DIS1+" REAL,"
                +COL_PRODUCT_DIS2+" REAL,"
                +COL_PRODUCT_DIS3+" REAL,"
                +COL_PRODUCT_DIS4+" REAL,"
                +COL_PRODUCT_AMT+" INTEGER,"
                +"PRIMARY KEY("+COL_PRODUCT_ID+"))";
        db.execSQL(query);
        Log.e(TAG,"initProductTable");
    }

    private void dropTable(SQLiteDatabase db){
        String dropProduct= "DROP TABLE IF EXISTS "+TABLE_PRODUCT;
        db.execSQL(dropProduct);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG,"Upgrade db");
        dropTable(db);
        onCreate(db);
    }

    private ContentValues getParams(MProduct product){
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_ID, product.getId());
        values.put(COL_PRODUCT_NAME, product.getName());
        values.put(COL_PRODUCT_POINT, product.getPoint());
        values.put(COL_PRODUCT_PRICE, product.getPrice());
        values.put(COL_PRODUCT_DIS1, product.getDiscount1());
        values.put(COL_PRODUCT_DIS2, product.getDiscount2());
        values.put(COL_PRODUCT_DIS3, product.getDiscount3());
        values.put(COL_PRODUCT_DIS4, product.getDiscount4());
        values.put(COL_PRODUCT_AMT, product.getAmount());
        return values;
    }

    public MProduct getProductByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        //tạo cursor đọc dòng dl theo cột
        Cursor cursor = db.query(TABLE_PRODUCT
                , new String[]{COL_PRODUCT_ID,COL_PRODUCT_NAME,COL_PRODUCT_POINT,COL_PRODUCT_PRICE
                        ,COL_PRODUCT_DIS1,COL_PRODUCT_DIS2,COL_PRODUCT_DIS3,COL_PRODUCT_DIS4,COL_PRODUCT_AMT}
                ,COL_PRODUCT_ID+"=?"
                ,new String[] {String.valueOf(id)},null,null,null,null);
        if (cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
        }
        else return null;

        MProduct product = new MProduct(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2)
                ,cursor.getDouble(3),cursor.getDouble(4),cursor.getDouble(5),cursor.getDouble(6)
                ,cursor.getDouble(7),cursor.getInt(8));
        cursor.close();
        db.close();
        return product;
    }

    public int getProductAmount(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+COL_PRODUCT_AMT+" FROM "+TABLE_PRODUCT+" WHERE "+COL_PRODUCT_ID+" = "+id;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor != null){
            cursor.moveToFirst();
            int amount = cursor.getInt(0);
            return amount;
        }
        else
            return 0;
    }

    public ArrayList<MProduct> getAllProduct(){
        ArrayList<MProduct> listProduct = new ArrayList<>();
        String query = "SELECT "+COL_PRODUCT_ID+", "+COL_PRODUCT_NAME+", "+COL_PRODUCT_PRICE
                +", "+COL_PRODUCT_POINT+", "+COL_PRODUCT_DIS1+", "+COL_PRODUCT_DIS2+", "+COL_PRODUCT_DIS3+", "+COL_PRODUCT_DIS4+", "+COL_PRODUCT_AMT +" FROM "+TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null){
            cursor.moveToFirst();
            do {
                listProduct.add( new MProduct(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2)
                        ,cursor.getDouble(3),cursor.getDouble(4),cursor.getDouble(5),cursor.getDouble(6)
                        ,cursor.getDouble(7),cursor.getInt(8)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listProduct;
    }

    public ArrayList<MProduct> getProduct(){
        ArrayList<MProduct> listProduct = new ArrayList<>();
        String query = "SELECT "+COL_PRODUCT_ID+", "+COL_PRODUCT_NAME+", "+COL_PRODUCT_PRICE
                +", "+COL_PRODUCT_POINT+", "+COL_PRODUCT_DIS1+", "+COL_PRODUCT_DIS2+", "+COL_PRODUCT_DIS3+", "+COL_PRODUCT_DIS4
                +", "+COL_PRODUCT_AMT +" FROM "+TABLE_PRODUCT+" WHERE "+COL_PRODUCT_AMT+ " > 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null){
            cursor.moveToFirst();
            do {
                listProduct.add( new MProduct(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2)
                        ,cursor.getDouble(3),cursor.getDouble(4),cursor.getDouble(5),cursor.getDouble(6)
                        ,cursor.getDouble(7),cursor.getInt(8)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listProduct;
    }

    public ArrayList<Integer> getListID(){
        ArrayList<Integer> listID = new ArrayList<>();
        String query = "SELECT "+COL_PRODUCT_ID+" FROM "+TABLE_PRODUCT +" WHERE "+COL_PRODUCT_AMT +" > 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null){
            cursor.moveToFirst();
            do {
                listID.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listID;
    }

    public int getProductCount(){
        String query = "SELECT * FROM "+TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        Log.e(TAG,"Products: "+count);
        return count;
    }

    public int getCountProductTotal(){
        String query = "SELECT * FROM "+TABLE_PRODUCT+" WHERE "+COL_PRODUCT_AMT+" >0 ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        Log.e(TAG,"Products: "+count);
        if(count > 0)
            return count;
        else
            return 0;
    }

    public int updateProductAmount(int amount, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+TABLE_PRODUCT+" SET "+COL_PRODUCT_AMT+" = "+amount +" WHERE "+COL_PRODUCT_ID+" = "+id;
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void initDefaultData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = getDatebase(FILENAME,mContext);
        StringTokenizer stk = new StringTokenizer(query,";");
        while (stk.hasMoreTokens()){
            db.execSQL(stk.nextToken());
        }
        db.close();
        Log.e(TAG,"initDefaultData");
    }

    public String getDatebase(String FileName, Context context){
        BufferedReader reader = null;
        String query = "";
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(FileName)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                Log.e("product",""+mLine);
                query = String.valueOf(builder.append(mLine));
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return query;
    }

}

