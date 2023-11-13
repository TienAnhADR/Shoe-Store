package com.example.appbangiayonline.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbangiayonline.database.DBHelper;

public class DangNhap_DangKi_Dao {
    DBHelper dbHelper;
    Context context;

    public DangNhap_DangKi_Dao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void dangNhap(String taikhoan, String matkhau) {
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        Cursor cursor_nv = sql.rawQuery("select * from nhanvien where taikhoan='" + taikhoan + "' and matkhau ='+" + matkhau + "+'", null);
    }
}
