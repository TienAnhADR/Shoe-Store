package com.example.appbangiayonline.model;

public class CTSanPham {
    private int mactsanpham;
    private int masanpham;
    private String tensanpham;
    private String tenmausac;
    private int kichco;
    private int gia;
    private int soluong;

    public CTSanPham(int mactsanpham, String tensanpham, String tenmausac, int kichco, int gia, int soluong) {
        this.mactsanpham = mactsanpham;
        this.tensanpham = tensanpham;
        this.tenmausac = tenmausac;
        this.kichco = kichco;
        this.gia = gia;
        this.soluong = soluong;
    }
    public CTSanPham( String tensanpham, String tenmausac, int kichco, int gia, int soluong) {
        this.tensanpham = tensanpham;
        this.tenmausac = tenmausac;
        this.kichco = kichco;
        this.gia = gia;
        this.soluong = soluong;
    }

    public CTSanPham() {
    }

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public int getMactsanpham() {
        return mactsanpham;
    }

    public void setMactsanpham(int mactsanpham) {
        this.mactsanpham = mactsanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getTenmausac() {
        return tenmausac;
    }

    public void setTenmausac(String tenmausac) {
        this.tenmausac = tenmausac;
    }

    public int getKichco() {
        return kichco;
    }

    public void setKichco(int kichco) {
        this.kichco = kichco;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
