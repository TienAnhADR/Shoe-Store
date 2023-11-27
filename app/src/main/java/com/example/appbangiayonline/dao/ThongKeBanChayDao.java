package com.example.appbangiayonline.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbangiayonline.database.DBHelper;
import com.example.appbangiayonline.model.SanPham;

import java.util.ArrayList;

public class ThongKeBanChayDao {
    private final DBHelper dbHelper;

    public ThongKeBanChayDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<SanPham> getTop10() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<SanPham> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT s.* " +
                "FROM cthoadon ct " +
                "JOIN sanpham s ON ct.mactsanpham = s.masanpham " +
                "GROUP BY ct.mactsanpham " +
                "ORDER BY SUM(ct.soluongmua) DESC " +
                "LIMIT 10", null);

        while (cursor.moveToNext()) {
            list.add(new SanPham(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4) ));
        }
        return list;
    }

}
