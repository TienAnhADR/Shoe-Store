package com.example.appbangiayonline.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.CTSanPhamAdapter;
import com.example.appbangiayonline.adapter.MauSacAdapter;
import com.example.appbangiayonline.adapter.SizeAdapter;
import com.example.appbangiayonline.dao.CTSanPhamDao;
import com.example.appbangiayonline.dao.SanPhamDao;
import com.example.appbangiayonline.model.CTSanPham;
import com.example.appbangiayonline.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManHinh_CTSanPham extends AppCompatActivity implements OnItemClickMauSize {
    String tenchung;
    //nhan bundel
    TextView nhanten;
    TextView muangay;

    CTSanPhamDao dao;
    ArrayList<CTSanPham> list;
    CTSanPhamAdapter adapter;
    MauSacAdapter mauSacAdapter;
    SizeAdapter kichCoAdapter;

    RecyclerView rcmau;
    RecyclerView rckichco;
    RecyclerView rcctsanpham;
    //    String tentrung = null;
    CTSanPham ctSanPham;

    // Biến lưu trữ dữ liệu được chọn từ RecyclerViews
    String mausacDen;
    int kiccoDen;
    //    //nhan vao item
    TextView nhankichco;
    TextView nhanmausac;
    TextView nhantensanpham;
    TextView nhangia;
    TextView nhansiluong;
    //them giohang
    Button themGH;
    //lưu dữ được nhiều lần chọn
    private String selectedMauSac;
    private int selectedKichCo;
    //GOi Gio Hang het ra de su dung

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ctsan_pham);
        muangay = findViewById(R.id.muangay_sanpham);

        SanPhamDao sanPhamDao = new SanPhamDao(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            ArrayList<SanPham> list = sanPhamDao.getListSanPham();
            //tenchung = bundle.getString("tensanpham");
            nhanten = findViewById(R.id.tensanpham_sanpham);
            // nhanten.setText(tenchung);
            nhanten.setText(list.get(bundle.getInt("vitri", 0)).getTensanpham());
        }

        muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCTSanPham();
            }
        });
    }

    private void showCTSanPham() {
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_muangay);

        //  ImageView quaylai = dialog.findViewById(R.id.quaylai);
//        quaylai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        //


        rckichco = dialog.findViewById(R.id.kichco_detail);
        rcmau = dialog.findViewById(R.id.mausac_detail);
        rcctsanpham = dialog.findViewById(R.id.rcctsanpham_detail);
        rcctsanpham.setLayoutManager(new LinearLayoutManager(ManHinh_CTSanPham.this, RecyclerView.HORIZONTAL, false));
        dao = new CTSanPhamDao(this);

        ctSanPham = new CTSanPham();
        FloatingActionButton fl = dialog.findViewById(R.id.flthemsanpham);
//        nhan vao item
        nhankichco = dialog.findViewById(R.id.kiccosanphamct);
        nhanmausac = dialog.findViewById(R.id.mausacsanphamct);
        nhantensanpham = dialog.findViewById(R.id.tensanphamct);
        nhansiluong = dialog.findViewById(R.id.soluongsanphamct);
        nhangia = dialog.findViewById(R.id.giasanphamct);

        //them giohang
//        themGH = dialog.findViewById(R.id.themGioHang_detail);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcctsanpham.setLayoutManager(layoutManager);

        load(tenchung);
        loadDSKichCo(tenchung);
        loadDSMau(tenchung);

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themsanpham(tenchung);
            }
        });
        //
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnomation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void load(String tansanpham) {
        list = dao.getListCTSanPham(tansanpham);
        adapter = new CTSanPhamAdapter(ManHinh_CTSanPham.this, list);
        rcctsanpham.setAdapter(adapter);
        // Khi dữ liệu đã được lấy xong, gọi phương thức onDataLoaded()
    }

    private void loadDSKichCo(String tansanpham) {
        rckichco.setLayoutManager(new LinearLayoutManager(ManHinh_CTSanPham.this, RecyclerView.HORIZONTAL, false));
        list = dao.getListDSMauSize(tansanpham);
        kichCoAdapter = new SizeAdapter(ManHinh_CTSanPham.this, list, new OnItemClickMauSize() {
            @Override
            public void onItemClick(CTSanPham ctSanPham) {
                nhankichco.setText(String.valueOf(ctSanPham.getKichco()));
                selectedKichCo = ctSanPham.getKichco();
                mauSize(selectedMauSac, selectedKichCo);
            }
        });
        rckichco.setAdapter(kichCoAdapter);
        // Khi dữ liệu đã được lấy xong, gọi phương thức onDataLoaded()
    }

    private void loadDSMau(String tansanpham) {
        rcmau.setLayoutManager(new GridLayoutManager(ManHinh_CTSanPham.this, 3));
        list = dao.getListDSMauSize(tansanpham);
        mauSacAdapter = new MauSacAdapter(ManHinh_CTSanPham.this, list, new OnItemClickMauSize() {
            @Override
            public void onItemClick(CTSanPham ctSanPham) {
                nhanmausac.setText(ctSanPham.getTenmausac());
//                mausacDen = ctSanPham.getTenmau();
                //o day minh dat cho no ten bien de de dang goi
                selectedMauSac = ctSanPham.getTenmausac();
                //thuc hiện truyền dữ liệu trước b1
                mauSize(selectedMauSac, selectedKichCo);
            }
        });
        rcmau.setAdapter(mauSacAdapter);
    }

    private void mauSize(String mausac, int kichCo) {
        if (mausac != null && kichCo != 0) {
            ctSanPham = new CTSanPham();
            ctSanPham = dao.getItemCTSanPham(mausac, kichCo);
            if (ctSanPham != null) {
                nhanmausac.setText(mausac);
                nhankichco.setText(Integer.toString(kichCo));
                nhangia.setText(Integer.toString(ctSanPham.getGia()));
                nhansiluong.setText(Integer.toString(ctSanPham.getSoluong()));
                //CHÚ Ý NÈ: đây sẽ là chỗ cho nút mua ngay

            } else {
                nhangia.setText("0");
                nhansiluong.setText("0");
            }
        } else {
            nhangia.setText("0");
            nhansiluong.setText("0");
        }
    }


    @Override
    public void onItemClick(CTSanPham ctSanPham) {
        if (mausacDen != null && kiccoDen != 0) {
            mauSize(mausacDen, kiccoDen);
        } else {
        }
    }

    private void themsanpham(String tensanpham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ctsanpham_them, null);
        builder.setView(view);

        EditText txttenmau, txtkichco, txtsoluong, txtgia;
        txttenmau = view.findViewById(R.id.tenmausac_ctsanpham_A);
        txtkichco = view.findViewById(R.id.kicco_ctsanpham_A);
        txtsoluong = view.findViewById(R.id.soluongsanpham_ctsanpham_A);
        txtgia = view.findViewById(R.id.giasanpham_ctsanpham_A);


        Button btnadd = view.findViewById(R.id.them_ctsanpham_A);


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenmau = txttenmau.getText().toString();
                int size = Integer.parseInt(txtkichco.getText().toString());
                int soluongg = Integer.parseInt(txtsoluong.getText().toString());
                int giaa = Integer.parseInt(txtgia.getText().toString());
                if (tenmau.equals("")) {
                    Toast.makeText(ManHinh_CTSanPham.this, "Chua nhap du thong tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean row = dao.ThemCTSanPham(tensanpham, tenmau, size, giaa, soluongg);
                    if (row) {
                        list.clear();
                        list.addAll(dao.getListCTSanPham(tensanpham));
                        adapter.notifyDataSetChanged();
                        mauSacAdapter.notifyDataSetChanged();
                        kichCoAdapter.notifyDataSetChanged();
//                            alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ManHinh_CTSanPham.this, "That bai", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

