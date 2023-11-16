package com.example.appbangiayonline.dao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbangiayonline.database.DBHelper;
import com.example.appbangiayonline.model.GioHang;

import java.util.ArrayList;

public class Giohang_Dao {
    Context context;
    DBHelper helper;

    public Giohang_Dao(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    public ArrayList<GioHang> getList() {
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " +
                "giohang.magiohang,sanpham.masanpham,khachhang.makh," +
                "sanpham.tensanpham, " +
                "giohang.mausac,giohang.kichco,giohang.gia,giohang.soluong " +
                "from sanpham " +
                "join giohang " +
                "on sanpham.masanpham=giohang.masanpham " +
                "join khachhang " +
                "on khachhang.makh=giohang.makhachhang", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new GioHang(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getInt(7)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void remove_data(int magiohang) {
        SQLiteDatabase sql = helper.getWritableDatabase();
        sql.delete("giohang", "magiohang=?", new String[]{String.valueOf(magiohang)});
        sql.close();
    }
}