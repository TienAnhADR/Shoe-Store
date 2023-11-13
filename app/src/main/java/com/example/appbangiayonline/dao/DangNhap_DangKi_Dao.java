package com.example.appbangiayonline.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbangiayonline.database.DBHelper;
import com.example.appbangiayonline.model.KhachHang;
import com.example.appbangiayonline.model.NhanVien;

import java.util.ArrayList;

import kotlin.contracts.Returns;

public class DangNhap_DangKi_Dao {
    DBHelper dbHelper;
    Context context;

    public DangNhap_DangKi_Dao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int dang_nhap(String taikhoan, String matkhau) {
        ArrayList<KhachHang> list_kh = new ArrayList<>();
        ArrayList<NhanVien> list_nv = new ArrayList<>();
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        Cursor cursor_nv = sql.rawQuery("select * from nhanvien where taikhoan='" + taikhoan + "' and matkhau ='+" + matkhau + "+'", null);
        Cursor cursor_kh = sql.rawQuery("select * from khachhang where taikhoan='" + taikhoan + "' and matkhau ='+" + matkhau + "+'", null);
        if (cursor_nv.getCount() > 0) {
            list_nv.add(new NhanVien(cursor_nv.getInt(0), cursor_nv.getString(1), cursor_nv.getString(2), cursor_nv.getString(3), cursor_nv.getString(4), cursor_nv.getString(5), cursor_nv.getInt(6)));
            //1 admin
            //0 nhan vien
            if (list_nv.get(0).getChucvu() == 1) {
                return 1;
            } else {
                return 0;
            }
        }
        if (cursor_kh.getCount() > 0) {
            list_kh.add(new KhachHang(cursor_nv.getInt(0), cursor_nv.getString(1), cursor_nv.getString(2), cursor_nv.getString(3), cursor_nv.getString(4), cursor_nv.getString(5), cursor_kh.getString(6)));
            return 2;
        }
        return -1;
    }
}
