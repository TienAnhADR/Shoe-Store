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
                "tensanpham text," +
                "trangthai text)";
        sqLiteDatabase.execSQL(tbl_sanpham);

        //chi tiet sanpham
        String tbl_ctsanpham = "create table ctsanpham(mactsanpham integer primary key autoincrement," +
                "masanpham integer references sanpham(masanpham)," +
                "mausac text," +
                "kichco integer," +
                "gia integer," +
                "soluong integer)";
        sqLiteDatabase.execSQL(tbl_ctsanpham);

        //hoadon
        String tbl_hoadon = "create table hoadon(mahd integer primary key autoincrement," +
                "manv integer references nhanvien(manv)," +
                "makh integer references khachhang(makh)," +
                "tongsl integer," +
                "tongtien integer," +
                "trangthai integer)";
        //0 chuaxacnhan 1 daxacnhan
        sqLiteDatabase.execSQL(tbl_hoadon);

        String tbl_giohang = "create table giohang(" +
                "magiohang integer primary key autoincrement," +
                "masanpham integer references sanpham(masanpham)," +
                "makhachhang integer references khachhang(makhachhang)," +
                "mausac text," +
                "kichco integer," +
                "gia integer," +
                "soluong integer)";

        //--------------------------
        //chèn dữ liệu

        //nhan vien
        String insert_hoadon = "insert into hoadon" +
                "(mahd,manv,makh,tongsl,tongtien,trangthai) " +
                "values " +
                "(1,1,1,55,66,0)," +
                "(2,1,1,55,66,0) ," +
                "(3,1,1,55,66,0)";

        sqLiteDatabase.execSQL(insert_hoadon);

        //giohang
        String insert_giohang = "insert into giohang(masanpham,makhachhang,mausac,kichco,gia,soluong)" +
                "values " +
                "(1,1,'Màu xanh',34,50000,15)," +
                "(2,2,'Màu tím',35,14000,11)," +
                "(3,3,'Màu vàng',35,50000,11)";
        //sp
        String ins_sp = "insert into sanpham(tensanpham, trangthai) values" +
                "('Loại 1','Còn hàng')," +
                "('Loại 2','Còn hàng')," +
                "('Loại 3','Còn hàng')," +
                "('Loại 4','Còn hàng')";
        sqLiteDatabase.execSQL(ins_sp);
        String ct_sp = "insert into ctsanpham(masanpham,mausac,kichco,gia,soluong) values" +
                "(1,'Màu xanh',34,50000,15)," +
                "(2,'Màu tím',35,14000,11)," +
                "(3,'Màu vàng',30,11000,10)," +
                "(4,'Màu xanh',33,5000,11)," +
                "(1,'Màu hồng',30,17000,11)," +
                "(2,'Màu xanh',31,20000,12)," +
                "(3,'Màu vàng',35,50000,11)," +
                "(4,'Màu tím',33,45000,12)";
        sqLiteDatabase.execSQL(ct_sp);

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
        sqLiteDatabase.execSQL(tbl_giohang);
        sqLiteDatabase.execSQL(insert_khachhang);
        sqLiteDatabase.execSQL(insert_nhanvien);
        sqLiteDatabase.execSQL(insert_giohang);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("drop table khachhang");
            sqLiteDatabase.execSQL("drop table nhanvien");
            sqLiteDatabase.execSQL("drop table sanpham");
            sqLiteDatabase.execSQL("drop table ctsanpham");
            sqLiteDatabase.execSQL("drop table hoadon");
            sqLiteDatabase.execSQL("drop table giohang");
            onCreate(sqLiteDatabase);
        }
    }
}
