package com.example.appbangiayonline.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.appbangiayonline.adapter.HoaDonAdapter;
import com.example.appbangiayonline.adapter.MauSacAdapter;
import com.example.appbangiayonline.adapter.SizeAdapter;
import com.example.appbangiayonline.dao.CTSanPhamDao;

import com.example.appbangiayonline.dao.HoaDonDao;
import com.example.appbangiayonline.dao.NhanVien_KhachHang_Dao;

import com.example.appbangiayonline.model.CTSanPham;

import com.example.appbangiayonline.model.HoaDon;
import com.example.appbangiayonline.model.KhachHang;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManHinh_CTSanPham extends AppCompatActivity implements OnItemClickMauSize {
    String tenchung;
    //nhan bundel
    TextView nhanten;
    TextView giohang;

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
    TextView nhansoluong;
    //them giohang
    Button themGH;
    //lưu dữ được nhiều lần chọn
    private String selectedMauSac;
    private int selectedKichCo;
    //GOi Gio Hang het ra de su dung

    //cong tru
    int tongSoLuongSP = 1;
    int tongGiaSP = 0;
    //cong tru soluong tongtien
    ImageView imgCong;
    ImageView imgTru;
    TextView muangay_soluong;
    TextView muangay_tongtien;
    //Xac nhan mua ngay
    Button btnxacnhanhoadon;
    //KhachHang
    NhanVien_KhachHang_Dao dao_nv_kh;
    //HoaDon
    HoaDonDao daohd;
    HoaDonAdapter adapterhd;
    ArrayList<HoaDon> listhd;
    ImageView quaylai_rc_sanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ctsan_pham);
        giohang = findViewById(R.id.giohang_sanpham);

        quaylai_rc_sanpham = findViewById(R.id.quaylai_rc_sanpham);

        quaylai_rc_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManHinh_CTSanPham.this, MainActivity.class));
            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            tenchung = bundle.getString("tensanpham");
            nhanten = findViewById(R.id.tensanpham_sanpham);
            nhanten.setText(tenchung);
        }

        giohang.setOnClickListener(v -> showCTSanPham());
    }

    private void showCTSanPham() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_muangay);

        ImageView quaylai = dialog.findViewById(R.id.quaylai);

        //CHÚ Ý NÈ: Cong tru va sotien, soluong
        imgCong = dialog.findViewById(R.id.imgCong);
        imgTru = dialog.findViewById(R.id.imgTru);
        muangay_soluong = dialog.findViewById(R.id.tongsoluong_muangay);
        muangay_tongtien = dialog.findViewById(R.id.tongtien_muangay);
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //XacNhanMuaNgay
        btnxacnhanhoadon = dialog.findViewById(R.id.xacnhanhoadon);
        btnxacnhanhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanMuaNgay(tongSoLuongSP, tongGiaSP);
            }
        });

        //
        rckichco = dialog.findViewById(R.id.kichco_detail);
        rcmau = dialog.findViewById(R.id.mausac_detail);
        rcctsanpham = dialog.findViewById(R.id.rcctsanpham_detail);
        dao = new CTSanPhamDao(this);
        ctSanPham = new CTSanPham();
        FloatingActionButton fl = dialog.findViewById(R.id.flthemsanpham);
//        nhan vao item
        nhankichco = dialog.findViewById(R.id.kiccosanphamct);
        nhanmausac = dialog.findViewById(R.id.mausacsanphamct);
        nhantensanpham = dialog.findViewById(R.id.tensanphamct);
        nhansoluong = dialog.findViewById(R.id.soluongsanphamct);
        nhangia = dialog.findViewById(R.id.giasanphamct);

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

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnomation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private void load(String tansanpham) {
        rcctsanpham.setLayoutManager(new LinearLayoutManager(ManHinh_CTSanPham.this, RecyclerView.HORIZONTAL, false));
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
                nhansoluong.setText(Integer.toString(ctSanPham.getSoluong()));
                //CHÚ Ý NÈ: đây sẽ là chỗ cho nút mua ngay
                //CHÚ Ý NÈ: Cong tru va sotien, soluong

                boolean kt = dao.kiemTraTonTaiTrongMactsp(ctSanPham.getMactsanpham(), mausac, kichCo, ctSanPham.getGia(), ctSanPham.getSoluong());
                if (kt) {
                    if (imgCong != null) {
                        imgCong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              //  if (tongSoLuongSP >= 0) {
                                    tongSoLuongSP++;
                                    tongGiaSP = ctSanPham.getGia() * tongSoLuongSP;
                                    Log.d(TAG, "Giá trị tongGiaSP: " + tongGiaSP);
                                    muangay_soluong.setText(String.valueOf(tongSoLuongSP));
                                    muangay_tongtien.setText(String.valueOf(tongGiaSP));
                              //  }
                            }
                        });
                    }

                    if (imgTru != null) {
                        imgTru.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                tongSoLuongSP = 1;
//                                tongGiaSP = 0;
                                if (tongSoLuongSP > 1) {
                                    tongSoLuongSP--;
                                    tongGiaSP = ctSanPham.getGia() * tongSoLuongSP;
                                    Log.d(TAG, "Giá trị tongGiaSP: " + tongGiaSP);
                                    muangay_soluong.setText(String.valueOf(tongSoLuongSP));
                                    muangay_tongtien.setText(String.valueOf(tongGiaSP));
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Khong ton tai", Toast.LENGTH_SHORT).show();
                }
            } else {
                nhangia.setText("0");
                nhansoluong.setText("0");
            }
        } else {
            nhangia.setText("0");
            nhansoluong.setText("0");
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
        View view = LayoutInflater.from(this).inflate(R.layout.item_ctsanpham_them, null, false);
        builder.setView(view);

        EditText txttenmau, txtkichco, txtsoluong, txtgia;
        txttenmau = view.findViewById(R.id.tenmausac_ctsanpham_A);
        txtkichco = view.findViewById(R.id.kicco_ctsanpham_A);
        txtsoluong = view.findViewById(R.id.soluongsanpham_ctsanpham_A);
        txtgia = view.findViewById(R.id.giasanpham_ctsanpham_A);
        Button btnadd = view.findViewById(R.id.them_ctsanpham_A);


        btnadd.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(txttenmau.getText().toString()) && !TextUtils.isEmpty(txtkichco.getText().toString()) && !TextUtils.isEmpty(txtgia.getText().toString()) && !TextUtils.isEmpty(txtsoluong.getText().toString())) {
                try {
                    String mau = txttenmau.getText().toString();
                    int size = Integer.parseInt(txtkichco.getText().toString());
                    int soluongg = Integer.parseInt(txtsoluong.getText().toString());
                    int giaa = Integer.parseInt(txtgia.getText().toString());
                    boolean row = dao.ThemCTSanPham(tensanpham, mau, giaa, soluongg, size);
                    if (row) {
                        Log.d(TAG, "Thêm thành công");
                        list.clear();
                        list.addAll(dao.getListCTSanPham(tensanpham));
                        adapter.notifyDataSetChanged();
                        mauSacAdapter.notifyDataSetChanged();
                        kichCoAdapter.notifyDataSetChanged();

                        Toast.makeText(ManHinh_CTSanPham.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(ManHinh_CTSanPham.this, "That bai", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Log.i(TAG, "Phai la so", e);
                }

                String tenmau = txttenmau.getText().toString();
                int size = Integer.parseInt(txtkichco.getText().toString());
                int soluongg = Integer.parseInt(txtsoluong.getText().toString());
                int giaa = Integer.parseInt(txtgia.getText().toString());
                if (tenmau.equals("")) {
                    Toast.makeText(ManHinh_CTSanPham.this, "Chua nhap du thong tin", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ManHinh_CTSanPham.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.show();
    }


    private void XacNhanMuaNgay(int tongSoLuongSP, int tongGiaSP) {
        daohd = new HoaDonDao(this);
        listhd = daohd.getDSHoaDon();
        adapterhd = new HoaDonAdapter(this, listhd);
        dao_nv_kh = new NhanVien_KhachHang_Dao(this);
        SharedPreferences sharedPreferences = getSharedPreferences("admin", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("taikhoan", "a");

        if (!TextUtils.isEmpty(username)) {
            KhachHang khachHang = new KhachHang();
            khachHang = dao_nv_kh.getThongTinKhachHang(username);
            int makh = khachHang.getMakh();
            if (makh != 0) {
                boolean kt = daohd.ThemHoaDon(makh, tongSoLuongSP, tongGiaSP);
                if (kt) {
                    listhd.clear();
                    listhd.addAll(daohd.getDSHoaDon());
                    adapterhd.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Dat hang thanh cong", Toast.LENGTH_SHORT).show();
                } else {
//                    showConfirmationDialog();
                    Toast.makeText(getApplicationContext(), "Dat hang that bai", Toast.LENGTH_SHORT).show();
                }
            } else {
//                showConfirmationDialog();
                Toast.makeText(this, "Khong ton tai", Toast.LENGTH_SHORT).show();
            }
        } else {
//            showConfirmationDialog();
            Toast.makeText(getApplicationContext(), "Khong ton tai", Toast.LENGTH_SHORT).show();
        }
    }
}

