package com.example.bin.productmanagement.DemoOld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final String FILENAME = "Product.sql";
    //Database
    private static final String DATABASE_NAME= "ProductManagement";
    private static int DATABASE_VERSION = 1;

    //Table product
    private static final String TABLE_PRODUCT = "Product";
    private static final String COL_PRODUCT_ID = "Product_ID";
    private static final String COL_PRODUCT_NAME = "Product_Name";
    private static final String COL_PRODUCT_THUMB = "Product_Thumb";
    private static final String COL_PRODUCT_PRICE = "Product_Price";
    private static final String COL_PRODUCT_DESC = "Product_Describe";
    Context mContext;
    //
    public MyDatabase(Context context){
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
                +COL_PRODUCT_DESC+" TEXT,"
                +COL_PRODUCT_THUMB+" TEXT,"
                +COL_PRODUCT_PRICE+ " REAL,"
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

    //insert Song
    public long addProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        long rowNumber = db.insert(TABLE_PRODUCT,null,getParams(product));
        db.close();
        return rowNumber;
    }

    private ContentValues getParams(Product product){
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_ID, product.getID());
        values.put(COL_PRODUCT_NAME, product.getName());
        values.put(COL_PRODUCT_THUMB, product.getThumb());
        values.put(COL_PRODUCT_PRICE, product.getPrice());
        return values;
    }

    public Product getProductByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        //tạo cursor đọc dòng dl theo cột
        Cursor cursor = db.query(TABLE_PRODUCT
                , new String[]{COL_PRODUCT_ID,COL_PRODUCT_NAME,COL_PRODUCT_DESC,COL_PRODUCT_THUMB,COL_PRODUCT_PRICE}
                ,COL_PRODUCT_ID+"=?"
                ,new String[] {String.valueOf(id)},null,null,null,null);
        if (cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
        }
        else return null;

        Product product = new Product(Integer.parseInt(cursor.getString(0))
                ,cursor.getString(1),cursor.getString(2),cursor.getString(3)
                ,cursor.getDouble(4));
        cursor.close();
        db.close();
        return product;
    }

    public int updateProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        int rowNumber = db.update(TABLE_PRODUCT,getParams(product),COL_PRODUCT_ID+"=?", new String[] {String.valueOf(product.getID())});
        db.close();
        return rowNumber;
    }

    public int deleteProduct(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        int rowNumber = db.delete(TABLE_PRODUCT,COL_PRODUCT_ID+"=?", new String[] {String.valueOf(ID)});
        db.close();
        return rowNumber;
    }

    public ArrayList<Product> getProduct(){
        ArrayList<Product> listProduct = new ArrayList<>();
        String query = "SELECT "+COL_PRODUCT_ID+", "+COL_PRODUCT_NAME+", "+COL_PRODUCT_DESC+","+COL_PRODUCT_THUMB+","+COL_PRODUCT_PRICE +" FROM "+TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null){
            cursor.moveToFirst();
            do {
                listProduct.add( new Product(Integer.parseInt(cursor.getString(0))
                        ,cursor.getString(1),cursor.getString(2),cursor.getString(3)
                        ,cursor.getDouble(4)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listProduct;
    }


    public ArrayList<Product> getSearchSongs(String keyWord){
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM song WHERE "+COL_PRODUCT_NAME+" LIKE '%"+keyWord+"%' OR "
                +COL_PRODUCT_NAME+" LIKE '%"+keyWord+"%'";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        if(cursor != null) {
            if (count > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new Product(Integer.parseInt(cursor.getString(0))
                            ,cursor.getString(1),cursor.getString(2),cursor.getString(3)
                            ,cursor.getDouble(4)));
                } while (cursor.moveToNext());
                return list;
            }
        }
        return null;

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
