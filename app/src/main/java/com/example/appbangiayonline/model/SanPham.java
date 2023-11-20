package com.example.appbangiayonline.model;

public class SanPham {
    private int masanpham;
    private String tensanpham;
    private String trangthai;



    public SanPham(int masanpham, String tensanpham, String trangthai) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.trangthai = trangthai;
    }

    public SanPham() {
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }
}
