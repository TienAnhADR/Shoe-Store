package com.example.appbangiayonline.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.adapter_giohang;
import com.example.appbangiayonline.dao.Giohang_Dao;
import com.example.appbangiayonline.fragmentTA.FragmentHoaDon;
import com.example.appbangiayonline.model.CTSanPham;
import com.example.appbangiayonline.model.GioHang;
import com.example.appbangiayonline.model.HoaDon;
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

        //----------------
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("themgiohang")) {
            CTSanPham sanPham = (CTSanPham) intent.getSerializableExtra("themgiohang");
            SharedPreferences sharedPreferences = getSharedPreferences("khachhang", MODE_PRIVATE);
            int id_kh = sharedPreferences.getInt("id_kh", -1);
            if (id_kh == -1) {
                Toast.makeText(this, "Đăng nhập dưới quyền thành viên để thêm giỏ hàng!", Toast.LENGTH_SHORT).show();
            } else {
                dao.themGioHang(sanPham, id_kh);
                Toast.makeText(this, "Thêm sản phẩm " + sanPham.getTensanpham() + " thành công!", Toast.LENGTH_SHORT).show();
                reload();
            }

        }
        //------------------
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
            }
            alBuilder.show();
        });
        Button button_thanhToan = findViewById(R.id.btn_thanhtoan_giohang);
        button_thanhToan.setOnClickListener(view -> {
            Intent intent1 = new Intent(Activity_GioHang.this, MainActivity.class);
            intent1.putExtra("open_hoadon", "hoadon");
            startActivity(intent1);
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
