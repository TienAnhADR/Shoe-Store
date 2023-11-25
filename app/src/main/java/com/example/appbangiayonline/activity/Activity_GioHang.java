package com.example.appbangiayonline.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.appbangiayonline.dao.HoaDonCT_Dao;
import com.example.appbangiayonline.dao.HoaDonDao;
import com.example.appbangiayonline.dao.NhanVien_KhachHang_Dao;
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
    ArrayList<HoaDon> listhd;
    public int s;
    int id_kh, makh, s2;
    HoaDonCT_Dao daoHDCT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__gio_hang);

        daoHDCT = new HoaDonCT_Dao(this);
        dao = new Giohang_Dao(this);
        recyclerView = findViewById(R.id.rcv_giohang);
        tongtien = findViewById(R.id.tongTien_giohang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reload();
        ImageButton back = findViewById(R.id.img_btn_back_giohang);
        back.setOnClickListener(view -> {
            finish();
        });

        //----------------
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("themgiohang")) {
            CTSanPham sanPham = (CTSanPham) intent.getSerializableExtra("themgiohang");

            SharedPreferences sharedPreferences = getSharedPreferences("khachhang", MODE_PRIVATE);
            id_kh = sharedPreferences.getInt("id_kh", -1);
            if (id_kh != -1) {
                dao.themGioHang(sanPham, id_kh);
                Toast.makeText(this, "Thêm sản phẩm " + sanPham.getTensanpham() + " thành công!" , Toast.LENGTH_SHORT).show();
                reload();
            }
        }
        //------------------

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

        NhanVien_KhachHang_Dao dao_nv_kh = new NhanVien_KhachHang_Dao(this);
        SharedPreferences sharedPreferences = getSharedPreferences("admin", MODE_PRIVATE);
        String username = sharedPreferences.getString("taikhoan", "");

        if (!TextUtils.isEmpty(username)) {
            KhachHang khachHang = dao_nv_kh.getThongTinKhachHang(username);
            if (khachHang != null) {
                makh = khachHang.getMakh();
            } else {
                makh = 1;
            }
        }

        Button button_thanhToan = findViewById(R.id.btn_thanhtoan_giohang);
        button_thanhToan.setOnClickListener(view -> {
            HoaDonDao dao2 = new HoaDonDao(this);
            boolean check = dao2.addHoaDon(makh, s);

            if (check) {
                listhd = dao2.getDSHoaDon();
                AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
                if (listchk.size() != 0) {
                    alBuilder.setTitle("Thanh toán sản phẩm trong giỏ hàng!").setIcon(R.drawable.baseline_error_outline_24).setMessage("Bạn có chắc chắn muốn Thanh toán sản phẩm").setPositiveButton("Cóa", ((dialogInterface, i) -> {
                        listchk.forEach(e -> {
                            list.forEach(e1 -> {
                                if (e1.getMagiohang() == e) {
                                    s2 = e1.getSl_mua();
                                }
                            });
                            int mactsp = dao.getMaCTSP(e);
                            daoHDCT.themCTHD(listhd.size(), mactsp, s2);
                            dao.remove_data(e);
                        });
                        listchk.clear();
                        tongtien.setText("0 VNĐ");
                        reload();
                    })).setNegativeButton("Hông", ((dialogInterface, i) -> {
                    }));
                }
                alBuilder.show();
            }
        });
    }

    void reload() {
        list = dao.getList();
        adap = new adapter_giohang(list, this);
        recyclerView.setAdapter(adap);
    }

    public void addHoaDonCT(int id_CTSP, int soLuongMua) {

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
