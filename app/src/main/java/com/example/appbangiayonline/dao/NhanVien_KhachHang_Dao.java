package com.example.appbangiayonline.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.appbangiayonline.database.DBHelper;
import com.example.appbangiayonline.model.KhachHang;
import com.example.appbangiayonline.model.NhanVien;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhanVien_KhachHang_Dao {
    private DBHelper helper;
    private Context context;

    public NhanVien_KhachHang_Dao(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }
    public ArrayList<NhanVien> getList_NV(){
//        int manv, int chucvu, String hoten, String sdt, String email
//        String tbl_nhanvien = "create table nhanvien (" +
//                "manv integer primary key autoincrement," +
//                "hoten text," +
//                "taikhoan text," +
//                "matkhau text," +
//                "sdt text," +
//                "email text," +
//                "chucvu integer)";
        ArrayList<NhanVien> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM nhanvien ",null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new NhanVien(cursor.getInt(0),cursor.getInt(6),cursor.getString(1),cursor.getString(4),cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int newNhanVien(String hoTen,String userName,String pass,String sdt,String email){
        SQLiteDatabase sql = helper.getWritableDatabase();
        Cursor cursor = sql.rawQuery("SELECT * FROM nhanvien WHERE nhanvien.taikhoan='" + userName + "'", null);
        if (cursor.getCount() > 0) {
            return 0;
        } else if (isEmailValid(email)) {
            Toast.makeText(context, "Email sai định dạng!", Toast.LENGTH_SHORT).show();
        } else if (isNumberValid(sdt)) {
            Toast.makeText(context, "Số điện thoại sai định dạng!", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hoten", hoTen);
            contentValues.put("taikhoan", userName);
            contentValues.put("matkhau", pass);
            contentValues.put("sdt", sdt);
            contentValues.put("email", email);
            sql.insert("khachhang", null, contentValues);
            sql.close();
            return 1;
        }
        return -10;
    }

    public boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isNumberValid(String number) {
        if (number.charAt(0) == '0' && number.length() == 10) {
            return true;
        }
        return false;
    }
//    int makh, String hoten, String sdt, String email, String diachi
//String tbl_khachhang = "create table khachhang (" +
//        "makh integer primary key autoincrement," +
//        "hoten text," +
//        "taikhoan text," +
//        "matkhau text," +
//        "sdt text," +
//        "email text," +
//        "diachi text)";
    public ArrayList<KhachHang> getList_KH(){
        ArrayList<KhachHang> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM khachhang",null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new KhachHang(cursor.getInt(0),cursor.getString(1),cursor.getString(4),cursor.getString(5),cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }
}