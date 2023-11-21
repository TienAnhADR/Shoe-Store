package com.example.appbangiayonline.model;

import java.io.Serializable;
import java.util.Objects;

public class CTSanPham implements Serializable {
    private int mactsanpham;
    private int masanpham;
    private String tensanpham;
    private String tenmausac;
    private int kichco;
    private int gia;
    private int soluong;
    private int soluong_mua;

    public CTSanPham(int gia, int soluong) {
        this.gia = gia;
        this.soluong = soluong;
    }

    public CTSanPham(int mactsanpham, String tensanpham, String tenmausac, int kichco, int gia, int soluong) {
        this.mactsanpham = mactsanpham;
        this.tensanpham = tensanpham;
        this.tenmausac = tenmausac;
        this.kichco = kichco;
        this.gia = gia;
        this.soluong = soluong;
    }

    public CTSanPham(int mactsanpham, int masanpham, String tensanpham, String tenmausac, int kichco, int gia, int soluong) {
        this.mactsanpham = mactsanpham;
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.tenmausac = tenmausac;
        this.kichco = kichco;
        this.gia = gia;
        this.soluong = soluong;
    }

    public CTSanPham(String tensanpham, String tenmausac, int kichco, int gia, int soluong) {
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

    public int getSoluong_mua() {
        return soluong_mua;
    }

    public void setSoluong_mua(int soluong_mua) {
        this.soluong_mua = soluong_mua;
    }

    @Override
    public String toString() {
        return "CTSanPham{" +
                "mactsanpham=" + mactsanpham +
                ", masanpham=" + masanpham +
                ", tensanpham='" + tensanpham + '\'' +
                ", tenmausac='" + tenmausac + '\'' +
                ", kichco=" + kichco +
                ", gia=" + gia +
                ", soluong=" + soluong +
                ", soluong_mua=" + soluong_mua +
                '}';
    }
}

