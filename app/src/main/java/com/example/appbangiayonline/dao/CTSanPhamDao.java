package com.example.appbangiayonline.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appbangiayonline.database.DBHelper;
import com.example.appbangiayonline.model.CTSanPham;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CTSanPhamDao {
    private final DBHelper dbHelper;

    public CTSanPhamDao(Context context) {
        dbHelper = new DBHelper(context);
    }
    public ArrayList<CTSanPham> getListCTSanPham(String tensanpham){
        ArrayList<CTSanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("select ctsp.mactsanpham, sp.tensanpham, ctsp.mausac, ctsp.kichco, ctsp.gia, ctsp.soluong from sanpham sp, ctsanpham ctsp where ctsp.masanpham = sp.masanpham and sp.tensanpham = ?", new String[]{tensanpham});
            while (cursor.moveToNext()){
                list.add(new CTSanPham(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5) ));
            }
            cursor.close();
        }catch (Exception e){
            Log.i(TAG, "loi", e);
        }
        return list;
    }
    public  ArrayList<CTSanPham> getListDSMauSize(String tensanpham){
        ArrayList<CTSanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("select sp.tensanpham, ctsp.mausac, ctsp.kichco, ctsp.gia, ctsp.soluong from sanpham sp, ctsanpham ctsp where sp.masanpham = ctsp.masanpham and sp.tensanpham = ?",new String[]{tensanpham});
            while (cursor.moveToNext()){
                list.add(new CTSanPham(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3),cursor.getInt(4) ));
            }
            cursor.close();
        }catch (Exception e){
            Log.i(TAG, "loi", e);
        }
        return list;
    }
    public boolean ThemCTSanPham(String tensanpham, String tenmausac, int kichco, int gia, int soluong){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select masanpham from sanpham where tensanpham = ?", new String[]{tensanpham});
        if(cursor.moveToFirst()){
            int masanpham = cursor.getInt(0);
            ContentValues values = new ContentValues();
            values.put("masanpham", masanpham);
            values.put("mausac", tenmausac);
            values.put("gia", gia);
            values.put("soluong", soluong);
            values.put("kichco", kichco);
            long kt = db.insert("ctsanpham", null, values);
            return (kt > 0);
        }
        cursor.close();
        return false;
    }
    public CTSanPham getItemCTSanPham(String mausac, int kichco){
        CTSanPham ctSanPham = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("select ctsp.mactsanpham, sp.tensanpham, ctsp.mausac, ctsp.kichco, ctsp.gia, ctsp.soluong from sanpham sp, ctsanpham ctsp where sp.masanpham = ctsp.masanpham and mausac = ? AND kichco = ?",new String[]{mausac, String.valueOf(kichco)});
            while (cursor.moveToNext()){
                ctSanPham = new CTSanPham(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
            }
        }catch (Exception e){
            Log.i(TAG, "loi", e);
        }
        return ctSanPham;
    }
    public boolean kiemTraTonTaiTrongMactsp(int mactsanpham, String tenmausac, int kichco, int gia, int soluong) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ctsanpham WHERE mactsanpham = ? AND mausac = ? AND kichco = ? and gia = ? and soluong =?",
                new String[]{String.valueOf(mactsanpham), tenmausac, String.valueOf(kichco), String.valueOf(gia), String.valueOf(soluong)});
        boolean tonTai = cursor.moveToFirst();
        cursor.close();
        return tonTai;
    }
}