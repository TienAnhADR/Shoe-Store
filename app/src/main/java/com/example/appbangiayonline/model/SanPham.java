package com.example.appbangiayonline.model;

public class SanPham {
    private int masanpham;
    private String tensanpham;

    public SanPham(int masanpham, String tensanpham) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
    }

    public SanPham() {
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
