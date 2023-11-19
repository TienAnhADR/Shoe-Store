package com.example.appbangiayonline.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.adapter_giohang;
import com.example.appbangiayonline.dao.Giohang_Dao;
import com.example.appbangiayonline.model.GioHang;
import com.example.appbangiayonline.model.KhachHang;

import java.util.ArrayList;

public class Activity_GioHang extends AppCompatActivity {
    Giohang_Dao dao;
    RecyclerView recyclerView;
    adapter_giohang adap;
    ArrayList<Integer> listchk = new ArrayList<>();

    ArrayList<GioHang> list;
    TextView tongtien;
    public int s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__gio_hang);

        dao = new Giohang_Dao(this);
        recyclerView = findViewById(R.id.rcv_giohang);
        tongtien = findViewById(R.id.tongTien_giohang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton back = findViewById(R.id.img_btn_back_giohang);
        back.setOnClickListener(view -> {
            finish();
        });
        reload();

        TextView edit = findViewById(R.id.edit_sp_giohang);
        edit.setOnClickListener(view -> {
            AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
            if (listchk.size() != 0) {
                alBuilder.setTitle("Xóa sản phẩm trong giỏ hàng!").setIcon(R.drawable.baseline_error_outline_24).setMessage("Bạn có chắc chắn muốn xóa sản phẩm").setPositiveButton("Cóa", ((dialogInterface, i) -> {
                    listchk.forEach(e -> {
                        dao.remove_data(e);
                    });
                    listchk.clear();
                    tongtien.setText("0 VNĐ");
                    reload();
                })).setNegativeButton("Hông", ((dialogInterface, i) -> {
                }));
            } else {
                alBuilder.setTitle("Có cái mẹ gì đâu mà xóa =)))").setIcon(R.drawable.baseline_error_outline_24).setMessage("Thêm sản phẩm đi !").setPositiveButton("Để coi", ((dialogInterface, i) -> {
                    listchk.forEach(e -> {
                        dao.remove_data(e);
                    });
                    listchk.clear();
                    tongtien.setText("0 VNĐ");
                    reload();
                })).setNegativeButton("Không thích", ((dialogInterface, i) -> {
                })).show();
            }
            alBuilder.show();
        });

    }

    void reload() {
        list = dao.getList();
        adap = new adapter_giohang(list, this);
        recyclerView.setAdapter(adap);
    }

    public void add_chck(int i) {
        listchk.add(i);
        reload_tongtien();
    }


    public void rm_chck(int i) {
        listchk.remove(listchk.indexOf(i));
        reload_tongtien();
    }

    public void reload_tongtien() {
        s = 0;
        listchk.forEach(e -> {
            list.forEach(e1 -> {
                if (e1.getMagiohang() == e) {
                    s += (e1.getGiasp() * e1.getSl_mua());
                }
            });
        });
        tongtien.setText(s + " VNĐ");
    }
}
