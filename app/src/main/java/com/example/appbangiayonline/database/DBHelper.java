package com.example.appbangiayonline.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static String name_db = "ShoseStore";
    public static int version_db = 1;

    public DBHelper(@Nullable Context context) {
        super(context, name_db, null, version_db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //tạo bảng
        //khach hang
        String tbl_khachhang = "create table khachhang (" +
                "makh integer primary key autoincrement," +
                "hoten text," +
                "taikhoan text," +
                "matkhau text," +
                "sdt text," +
                "email text," +
                "diachi text)";
        //nhanvien
        String tbl_nhanvien = "create table nhanvien (" +
                "manv integer primary key autoincrement," +
                "hoten text," +
                "taikhoan text," +
                "matkhau text," +
                "sdt text," +
                "email text," +
                "chucvu integer)";//1 la admin , 0 la nhanvien
        //san pham
        String tbl_sanpham = "create table sanpham(masanpham integer primary key autoincrement," +
                "tensanpham text )";
        sqLiteDatabase.execSQL(tbl_sanpham);

        String ins_sp = "insert into sanpham(tensanpham) values" +
                "('Loại 1')," +
                "('Loại 2')," +
                "('Loại 3')";
        sqLiteDatabase.execSQL(ins_sp);
        //chi tiet sanpham
        String tbl_ctsanpham = "create table ctsanpham(mactsanpham integer primary key autoincrement," +
                "masanpham integer references sanpham(masanpham)," +
                "mausac text," +
                "kichco integer," +
                "gia integer," +
                "soluong integer)";
        sqLiteDatabase.execSQL(tbl_ctsanpham);
        //--------------------------
        //chèn dữ liệu
        //khach hang
        String insert_khachhang = "insert into khachhang" +
                "(hoten,taikhoan,matkhau,sdt,email,diachi) " +
                "values " +
                "('Nguyễn Thành Trung','trungnt1','trung','0918273645','trungnt123@gmail.com','Hà Nội')," +
                "('Bùi Văn Hoàng','hoangbv2','hoang','0318403662','hoangbv321@gmail.com','Bắc Giang')," +
                "('Trương Minh Đức','ductm3','duc','0818456781','ductm456@gmail.com','Thanh Hóa')";
        //nhan vien
        String insert_nhanvien = "insert into nhanvien" +
                "(hoten,taikhoan,matkhau,sdt,email,chucvu) " +
                "values " +
                "('Nguyễn Tiến Anh','anhntph37315','ph37315','0882618529','anhntph37315@gmail.com',1)," +
                "('Phạm Hoàng Yến','yenphph34781','ph34781','0358164951','yenphph34781@gmail.com',0)," +
                "('Hoàng Quốc Quân','quanhqph33420','ph33420','0975460402','quanhqph33420@gmail.com',1)";

        sqLiteDatabase.execSQL(tbl_khachhang);
        sqLiteDatabase.execSQL(tbl_nhanvien);
        sqLiteDatabase.execSQL(insert_khachhang);
        sqLiteDatabase.execSQL(insert_nhanvien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("drop table khachhang");
            sqLiteDatabase.execSQL("drop table nhanvien");
            sqLiteDatabase.execSQL("drop table sanpham");
            sqLiteDatabase.execSQL("drop table ctsanpham");
            onCreate(sqLiteDatabase);
        }
    }
}
