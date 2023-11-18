package com.example.appbangiayonline.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appbangiayonline.database.DBHelper;
import com.example.appbangiayonline.model.HoaDon;

import java.util.ArrayList;

public class HoaDonDao {
    private final DBHelper dbHelper;

    public HoaDonDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<HoaDon> getDSHoaDon() {
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("select hd.mahd, nv.hoten, kh.hoten, hd.tongsl, hd.tongtien, hd.trangthai from hoadon hd, nhanvien nv, khachhang kh where hd.makh = kh.makh and hd.manv = nv.manv ", null);
            while (cursor.moveToNext()) {
                list.add(new HoaDon(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
            }
            cursor.close();
        } catch (Exception e) {
            Log.i(TAG, "loi", e);
        }
        return list;
    }
    public boolean ThemHoaDon(int makh, int tongsl, int tongtien){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv", 1);
        values.put("makh", makh);
        values.put("tongsl", tongsl);
        values.put("tongtien", tongtien);
        values.put("trangthai", 0);
        long kt = db.insert("hoadon", null, values);
        return (kt > 0);
    }
    public boolean thayDoiTrangThaiHoaDon(int mahd, int manv){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("trangthai", 1);
        values.put("manv", manv);
        long row = db.update("hoadon", values, "mahd = ?", new String[]{String.valueOf(mahd)});
        return (row > 0);
    }
}
