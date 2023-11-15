package com.example.appbangiayonline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.CTSanPhamAdapter;
import com.example.appbangiayonline.adapter.MauSacAdapter;
import com.example.appbangiayonline.adapter.SizeAdapter;
import com.example.appbangiayonline.dao.CTSanPhamDao;
import com.example.appbangiayonline.model.CTSanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Detail_CTSanPham extends AppCompatActivity implements OnItemClickMauSize{
    //nhan tensanpham tu bundle
    String tenchung;

    RecyclerView rc_ctsanpham;
    RecyclerView rc_mausac;
    RecyclerView rc_kichco;
    CTSanPhamDao ctSanPhamDao;
    ArrayList<CTSanPham> list_ctsanpham;
    CTSanPhamAdapter adapter_ctsanpham;
    MauSacAdapter mauSacAdapter;
    SizeAdapter sizeAdapter;

    //nhan vao item
    TextView nhankichco;
    TextView nhanmausac;
    TextView nhantensanpham;
    TextView nhangia;
    TextView nhansoluong;
    // biến lưu trữ dữ liệu được chọn tu item nhé(recycleview đó)
    String mausacDen;
    int kiccoDen;
    //Biến này luu được nhiều lần chọn
    String mauSacDuocChon;
    int kichCoDuocChon;
    //
    CTSanPham ctSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ctsan_pham);
        FloatingActionButton fl = findViewById(R.id.fl_ctsanpham);

        rc_ctsanpham = findViewById(R.id.rc_ctsanpham_detail);
        rc_mausac = findViewById(R.id.rc_mausac_detail);
        rc_kichco = findViewById(R.id.rc_kichco_detail);
        ctSanPhamDao = new CTSanPhamDao(Detail_CTSanPham.this);
        list_ctsanpham = new ArrayList<>();

//        ctSanPham = new CTSanPham();

        //        nhan vao item
        nhankichco = findViewById(R.id.kicco_sanphamct);
        nhanmausac = findViewById(R.id.mausac_sanphamct);
        nhantensanpham = findViewById(R.id.ten_sanphamct);
        nhansoluong = findViewById(R.id.soluong_sanphamct);
        nhangia = findViewById(R.id.gia_sanphamct);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rc_ctsanpham.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            tenchung = bundle.getString("tensanpham");
        }

        loadCTSanPham(tenchung);
        loadMauSac(tenchung);
        loadKichCo(tenchung);


        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themCTSanPham(tenchung);
            }
        });
    }
    private void loadCTSanPham(String tenchung){
        nhantensanpham.setText(tenchung);
        rc_ctsanpham.setLayoutManager(new LinearLayoutManager(Detail_CTSanPham.this, RecyclerView.HORIZONTAL, false));
        list_ctsanpham = ctSanPhamDao.getListCTSanPham(tenchung);
        adapter_ctsanpham = new CTSanPhamAdapter(Detail_CTSanPham.this, list_ctsanpham);
        rc_ctsanpham.setAdapter(adapter_ctsanpham);
    }
    private void loadMauSac(String tenchung){
        rc_mausac.setLayoutManager(new GridLayoutManager(Detail_CTSanPham.this, 2));
        list_ctsanpham = ctSanPhamDao.getListDSMauSize(tenchung);
        mauSacAdapter = new MauSacAdapter(Detail_CTSanPham.this, list_ctsanpham, new OnItemClickMauSize() {
            @Override
            public void onItemClick(CTSanPham ctSanPham) {
                nhanmausac.setText(ctSanPham.getTenmausac());
                //đặt tên r cho nó ra ngoài làm biến toàn cục để sử dungj
                mauSacDuocChon = ctSanPham.getTenmausac();
                mauSize(mauSacDuocChon, kichCoDuocChon);
            }
        });
        rc_mausac.setAdapter(mauSacAdapter);
    }
    private void loadKichCo(String tenchung){
        rc_kichco.setLayoutManager(new GridLayoutManager(Detail_CTSanPham.this,2 ));
        list_ctsanpham = ctSanPhamDao.getListDSMauSize(tenchung);
        sizeAdapter = new SizeAdapter(Detail_CTSanPham.this, list_ctsanpham, new OnItemClickMauSize() {
            @Override
            public void onItemClick(CTSanPham ctSanPham) {
                nhankichco.setText(Integer.toString(ctSanPham.getKichco()));
                //đặt tên r cho nó ra ngoài làm biến toàn cục để sử dungj
                kichCoDuocChon = ctSanPham.getKichco();
                mauSize(mauSacDuocChon, kichCoDuocChon);
            }
        });
        rc_kichco.setAdapter(sizeAdapter);
    }

    private void mauSize(String mausac, int kichCo) {
        if (mausac != null && kichCo != 0) {
            ctSanPham = new CTSanPham();
            ctSanPham = ctSanPhamDao.getItemCTSanPham(mausac, kichCo);
            if (ctSanPham != null) {
                nhantensanpham.setText(ctSanPham.getTensanpham());
                nhanmausac.setText(mausac);
                nhankichco.setText(Integer.toString(kichCo));
                nhangia.setText(Integer.toString(ctSanPham.getGia()));
                nhansoluong.setText(Integer.toString(ctSanPham.getSoluong()));
                //CHÚ Ý NÈ: đây sẽ là chỗ cho nút thêm vào giỏ hàng

            }else {
                nhangia.setText("0");
                nhansoluong.setText("0");
            }
        } else {
            nhangia.setText("0");
            nhansoluong.setText("0");
        }
    }

    private void themCTSanPham(String tenchung){
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

        AlertDialog alertDialog = builder.create();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 //                String mau = txttenmau.getText().toString();
//                int kichco = Integer.parseInt(txtkichco.getText().toString());
//                int soluong = Integer.parseInt(txtsoluong.getText().toString());
//                int gia = Integer.parseInt(txtgia.getText().toString());
//                ctSanPham.setMasanpham(ctSanPham.getMasanpham());
                String mau = txttenmau.getText().toString();
                int kichco = Integer.parseInt(txtkichco.getText().toString());
                int soluong = Integer.parseInt(txtsoluong.getText().toString());
                int gia = Integer.parseInt(txtgia.getText().toString());
                boolean kt = ctSanPhamDao.ThemCTSanPham(tenchung, mau, kichco, soluong, gia);
                if(kt){
                    list_ctsanpham.clear();
                    list_ctsanpham.addAll(ctSanPhamDao.getListCTSanPham(tenchung));
                    adapter_ctsanpham.notifyDataSetChanged();
                    mauSacAdapter.notifyDataSetChanged();
                    sizeAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                    Toast.makeText(Detail_CTSanPham.this, "Them thanh cong chi tiet san pham", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Detail_CTSanPham.this, "That bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void onItemClick(CTSanPham ctSanPham) {
        if (mausacDen != null && kiccoDen != 0) {
            //dữ liệu đã được chọn thì gọi ham nay thôi
            mauSize(mausacDen, kiccoDen);
        } else {

        }
    }
}