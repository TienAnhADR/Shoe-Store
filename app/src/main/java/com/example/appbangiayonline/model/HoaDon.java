package com.example.appbangiayonline.model;

public class HoaDon {
    private int mahoadon;
    private String tenkh;
    private String tennv;
    private int tongsl;
    private int tongTien;
    private int trangthai;


    public HoaDon(int mahoadon,String tennv,  String tenkh,  int tongsl, int tongTien, int trangthai) {
        this.mahoadon = mahoadon;
        this.tennv = tennv;
        this.tenkh = tenkh;
        this.tongsl = tongsl;
        this.tongTien = tongTien;
        this.trangthai = trangthai;
    }

    public HoaDon() {
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(int mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public int getTongsl() {
        return tongsl;
    }

    public void setTongsl(int tongsl) {
        this.tongsl = tongsl;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }


}
