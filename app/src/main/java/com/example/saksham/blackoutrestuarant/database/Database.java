package com.example.saksham.blackoutrestuarant.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.saksham.blackoutrestuarant.item.CartDetail;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by saksham_ on 06-Apr-18.
 */

public class Database extends SQLiteAssetHelper {

    private static final String db_name = "BR.db";
    private static final int db_ver = 1;

    public Database(Context context) {
        super(context, db_name, null, db_ver);
    }

    public List<CartDetail> getCart() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"ItemId", "Phone", "ProductName", "Price", "Size", "Quantity"};
        String sqlTable = "CartDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        final List<CartDetail> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Log.i("cms", c.getString(c.getColumnIndex("ItemId")));
                result.add(new CartDetail(c.getString(c.getColumnIndex("ItemId")),
                        c.getString(c.getColumnIndex("Phone")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Size")),
                        c.getDouble(c.getColumnIndex("Price")),
                        c.getInt(c.getColumnIndex("Quantity"))
                ));
            } while (c.moveToNext());
        }

        //  Log.i("cms",result.get(0).getProductName());

        return result;
    }

    public void addToCart(CartDetail cartDetail) {
        //cleanCart();
        SQLiteDatabase db = getReadableDatabase();
        db.setLocale(Locale.getDefault());
        /*String query=String.format(Locale.ENGLISH,"Insert into CartDetail(ItemId,Phone,ProductName,Price,Size,Quantity) values ('%s','%s','%s','%f','%s','%d');",
                cartDetail.getItemId(),
                cartDetail.getPhone(),
                cartDetail.getProductName(),
                cartDetail.getPrice(),
                cartDetail.getSize(),
                cartDetail.getQuantity()
                );*/

        //Log.i("cms","he" +cartDetail.getItemId());
        String tempQuery = "Select * from CartDetail where ItemId='" + cartDetail.getItemId() + "' and size='" + cartDetail.getSize() + "'";
        Cursor cursor = db.rawQuery(tempQuery, null);
        if (cursor.getCount() > 0) {
            String againQuery = "Delete from CartDetail where ItemId='" + cartDetail.getItemId() + "' and size='" + cartDetail.getSize() + "'";
            db.execSQL(againQuery);
        }
        String sql1 = "insert into CartDetail (ItemId,Phone,ProductName,Price,Size,Quantity) values('" + cartDetail.getItemId() + "','" + cartDetail.getPhone() + "', '" + cartDetail.getProductName() + "', '" + cartDetail.getPrice() + "', '" + cartDetail.getSize() + "','" + cartDetail.getQuantity() + "')";
        db.execSQL(sql1);
    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("Delete from CartDetail");
        db.execSQL(query);
    }

    public void deleteItem(String name,String size)
    {
        SQLiteDatabase db=getReadableDatabase();
        db.setLocale(Locale.getDefault());
        String tempQ="Delete from CartDetail where ProductName='" + name + "' and size='" + size + "'";
        db.execSQL(tempQ);

    }

}
