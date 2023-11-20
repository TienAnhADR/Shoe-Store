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
import android.widget.GridLayout;
import android.widget.ImageButton;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ManHinh_CTSanPham extends AppCompatActivity implements OnItemClickMauSize {
    String tenchung;
    //nhan bundel
    TextView nhanten;
    ImageButton giohang;
    Button muaNgay;

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

    //cong tru
    int tongSoLuongSP = 1;
    int tongGiaSP = 0;
    //cong tru soluong tongtien
    ImageView imgCong;
    ImageView imgTru;
    TextView muangay_soluong;
    TextView muangay_tongtien;
    ImageView quaylai_rc_sanpham;
    //Xac nhan mua ngay
    Button btnxacnhanhoadon;
    //KhachHang
    NhanVien_KhachHang_Dao dao_nv_kh;
    //HoaDon
    HoaDonDao daohd;
    HoaDonAdapter adapterhd;
    ArrayList<HoaDon> listhd;
    int newslsanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ctsan_pham);
        giohang = findViewById(R.id.giohang_sanpham);
        muaNgay = findViewById(R.id.muangay_sanpham);
        quaylai_rc_sanpham = findViewById(R.id.quaylai_rc_sanpham);
        giohang.setOnClickListener(view -> {
            themGioHang();
        });
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

        muaNgay.setOnClickListener(new View.OnClickListener() {
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
        dialog.dismiss();
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
        nhansiluong = dialog.findViewById(R.id.soluongsanphamct);
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
                nhansiluong.setText(Integer.toString(ctSanPham.getSoluong()));
                //CHÚ Ý NÈ: đây sẽ là chỗ cho nút mua ngay
                //CHÚ Ý NÈ: Cong tru va sotien, soluong

                boolean kt = dao.kiemTraTonTaiTrongMactsp(ctSanPham.getMactsanpham(), mausac, kichCo, ctSanPham.getGia(), ctSanPham.getSoluong());
                if (kt) {
                    if (imgCong != null) {
                        imgCong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (tongSoLuongSP >= 0) {
                                    tongSoLuongSP++;
                                    tongGiaSP = ctSanPham.getGia() * tongSoLuongSP;
                                    muangay_soluong.setText(String.valueOf(tongSoLuongSP));
                                    muangay_tongtien.setText(String.valueOf(tongGiaSP));
                                }
                            }
                        });
                    }

                    if (imgTru != null) {
                        imgTru.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (tongSoLuongSP > 1) {
                                    tongSoLuongSP--;
                                    tongGiaSP = ctSanPham.getGia() * tongSoLuongSP;
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

        AlertDialog alertDialog = builder.create();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                } else {
                    Toast.makeText(ManHinh_CTSanPham.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }

    private void XacNhanMuaNgay(int tongSoLuongSP, int tongGiaSP) {
        daohd = new HoaDonDao(this);
        listhd = daohd.getDSHoaDon();
        adapterhd = new HoaDonAdapter(this, listhd);
        dao_nv_kh = new NhanVien_KhachHang_Dao(this);
        if (xemConSoLuong(tongSoLuongSP)) {
            laySoLuongMoi(tongSoLuongSP);
            //
            SharedPreferences sharedPreferences = getSharedPreferences("admin", MODE_PRIVATE);
            String username = sharedPreferences.getString("taikhoan", "");
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
                        Toast.makeText(getApplicationContext(), "Dat hang that bai", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Khong ton tai", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Khong ton tai", Toast.LENGTH_SHORT).show();
            }
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Het so luong roi", Snackbar.LENGTH_SHORT);
            snackbar.show();
            Toast.makeText(this, "Het so luong roi", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean xemConSoLuong(int tongSoLuongSP) {
        return ctSanPham.getSoluong() >= tongSoLuongSP;
    }

    private void laySoLuongMoi(int tongSoLuongSP) {
        ctSanPham.setSoluong(ctSanPham.getSoluong() - tongSoLuongSP);
        newslsanpham = ctSanPham.getSoluong();
        //load so luong moi
    }

    //-------------------------------------------------
    TextView txt_giasp;
    TextView txt_slsp;
    int slMua = 1;
    Button clickedColor;
    Button clickedSize;

    private void themGioHang() {
        final Dialog dialog = new Dialog(this);

        dao = new CTSanPhamDao(this);
        ArrayList<CTSanPham> list1 = dao.getList(tenchung);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_giohang);

        Set<String> set_color = new HashSet<>();
        list1.forEach(e -> {
            set_color.add(e.getTenmausac());
        });

        ArrayList<String> list_color = new ArrayList<>(set_color);
        GridLayout gridLayout_color = dialog.findViewById(R.id.container_color_themsp_giohang);
        createButton_Color(gridLayout_color, list_color);
        //-----------------------------------------
        Set<Integer> set_size = new HashSet<>();
        list1.forEach(e -> {
            set_size.add(e.getKichco());
        });

        ArrayList<Integer> list_size = new ArrayList<>(set_size);
        GridLayout gridLayout_size = dialog.findViewById(R.id.container_size_themsp_giohang);
        createButton_Quality(gridLayout_size, list_size);
        //--------------------------
        txt_giasp = dialog.findViewById(R.id.txt_giasp_themgiohang);
        txt_slsp = dialog.findViewById(R.id.txt_slsp_themgiohang);

        ImageButton tang = dialog.findViewById(R.id.btn_tang_themgiohang);
        ImageButton giam = dialog.findViewById(R.id.btn_giam_themgiohang);
        TextView sl_mua = dialog.findViewById(R.id.sl_sp_themgiohang);

        tang.setOnClickListener(view -> {
            slMua++;
            sl_mua.setText(slMua + "");
        });
        giam.setOnClickListener(view -> {
            if (slMua > 1) {
                slMua--;
                sl_mua.setText(slMua + "");
            }
        });
        //----------------------------------------
        Button btnThem = dialog.findViewById(R.id.btn_them_themgiohang);
        btnThem.setOnClickListener(view -> {
            Intent intent = new Intent(ManHinh_CTSanPham.this, Activity_GioHang.class);
            if (clickedColor != null && clickedSize != null) {
                CTSanPham ctSanPham1 = dao.getItemCTSanPham_config(tenchung, clickedColor.getText().toString(), Integer.parseInt(clickedSize.getText().toString()));

                if (ctSanPham1 == null) {
                    Toast.makeText(this, "Sản phẩm đã hết hàng :(", Toast.LENGTH_SHORT).show();
                } else {
                    ctSanPham1.setSoluong_mua(slMua);
                    intent.putExtra("themgiohang", ctSanPham1);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Bạn hãy chọn màu sắc và kích cỡ phù hợp!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnomation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public void createButton_Color(GridLayout gridLayout, ArrayList<String> list2) {
        list2.forEach(e -> {
            Button button = createButton_color(e);
            gridLayout.addView(button);
        });
    }

    public void createButton_Quality(GridLayout gridLayout, ArrayList<Integer> list2) {
        list2.forEach(e -> {
            Button button = createButton_size(e + "");
            gridLayout.addView(button);
        });
    }

    private Button createButton_color(String text) {
        Context context = this;
        Button button = new Button(context);
        button.setText(text);
        button.setBackgroundColor(Color.WHITE);
        button.setTextColor(Color.BLACK);
        button.setTextSize(15);
        button.setGravity(Gravity.CENTER);

        button.setOnClickListener(v -> {
            if (clickedColor != null) {
                clickedColor.setBackgroundColor(Color.WHITE);
                clickedColor.setTextColor(Color.BLACK);
            }

            button.setBackgroundColor(Color.BLACK);
            button.setTextColor(Color.WHITE);
            clickedColor = button;
            if (clickedSize != null) {
                ButtonClick(text, Integer.parseInt(clickedSize.getText().toString()));
            }
        });
        //-------------------------------
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setMargins(5, 5, 5, 5);
        button.setLayoutParams(layoutParams);
        return button;
    }

    private Button createButton_size(String text) {
        Context context = this;
        Button button = new Button(context);
        button.setText(text);
        button.setBackgroundColor(Color.WHITE);
        button.setTextColor(Color.BLACK);
        button.setTextSize(15);
        button.setGravity(Gravity.CENTER);

        button.setOnClickListener(v -> {
            if (clickedSize != null) {
                clickedSize.setBackgroundColor(Color.WHITE);
                clickedSize.setTextColor(Color.BLACK);

            }

            button.setBackgroundColor(Color.BLACK);
            button.setTextColor(Color.WHITE);
            clickedSize = button;
            if (clickedColor != null) {
                ButtonClick(clickedColor.getText().toString(), Integer.parseInt(text));
            }
        });

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setMargins(5, 5, 5, 5);
        button.setLayoutParams(layoutParams);
        return button;
    }

    private void ButtonClick(String color, int size) {
        CTSanPham ctSanPham = dao.getItemctSP_config(color, size);
        if (ctSanPham != null) {
            txt_giasp.setText(ctSanPham.getGia() + "VNĐ");
            txt_slsp.setText("Kho: " + ctSanPham.getSoluong());
        } else {
            txt_giasp.setText(0 + "VNĐ");
            txt_slsp.setText("Kho: " + 0);
        }
    }
}


