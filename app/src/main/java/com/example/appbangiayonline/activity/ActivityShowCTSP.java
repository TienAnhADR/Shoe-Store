package com.example.appbangiayonline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.CTSanPhamAdapter;
import com.example.appbangiayonline.adapter.KhachHangAdapter;
import com.example.appbangiayonline.convert.ConvertImage;
import com.example.appbangiayonline.dao.CTSanPhamDao;
import com.example.appbangiayonline.dao.NhanVien_KhachHang_Dao;
import com.example.appbangiayonline.model.CTSanPham;
import com.example.appbangiayonline.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityShowCTSP extends AppCompatActivity {
    private RecyclerView rcvCTSP;
    private FloatingActionButton fl_btn_addCTSP;
    private ArrayList<CTSanPham> list;
    private ImageView btnBack;
    private CTSanPhamDao dao;
    SanPham sanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ctsp);
        rcvCTSP = findViewById(R.id.recylerV_CTSP);
        fl_btn_addCTSP = findViewById(R.id.fload_btn_Add_CTSP);
        btnBack = findViewById(R.id.img_btnBack_showctsp);

        Intent intent = getIntent();

        if (intent.hasExtra("obj_sanpham")) {

            sanPham = (SanPham) intent.getSerializableExtra("obj_sanpham");

        }
        reload();
        fl_btn_addCTSP.setOnClickListener(v -> showAddCTSP(sanPham.getMasanpham()));
        btnBack.setOnClickListener(v -> finish());
    }

    private void reload() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvCTSP.setLayoutManager(manager);
        dao = new CTSanPhamDao(ActivityShowCTSP.this);
        list = dao.getDSCTSP(sanPham.getTensanpham());
        CTSanPhamAdapter adapter = new CTSanPhamAdapter(this, list);
        rcvCTSP.setAdapter(adapter);

    }

    private void showAddCTSP(int masp) {
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
        Button btnHuy = view.findViewById(R.id.huy_ThemCTSP);

        AlertDialog alertDialog = builder.create();
        btnHuy.setOnClickListener(v -> alertDialog.dismiss());
        btnadd.setOnClickListener(new View.OnClickListener() {
            int size = 0, gia = 0, soluong = 0;

            @Override
            public void onClick(View v) {
                String mausac = txttenmau.getText().toString().trim();
                if (mausac.equals("") || txtgia.getText().toString().equals("") ||
                        txtsoluong.getText().toString().equals("") || txtkichco.getText().toString().equals("")) {

                    Toast.makeText(ActivityShowCTSP.this, "Chưa nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    size = Integer.parseInt(txtkichco.getText().toString().trim());
                    gia = Integer.parseInt(txtgia.getText().toString().trim());
                    soluong = Integer.parseInt(txtsoluong.getText().toString().trim());
                    if (dao.checkCTSP(mausac.toLowerCase(), size)) {
                        Boolean check = dao.addCTSP(masp, mausac, size, gia, soluong);
                        if (check) {
                            reload();
                            Toast.makeText(ActivityShowCTSP.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(ActivityShowCTSP.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ActivityShowCTSP.this, "Màu sắc và kích cỡ đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.show();

    }
}