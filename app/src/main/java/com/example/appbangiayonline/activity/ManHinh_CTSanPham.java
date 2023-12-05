package com.example.appbangiayonline.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.CTSanPhamAdapter;
import com.example.appbangiayonline.adapter.HoaDonAdapter;
import com.example.appbangiayonline.adapter.MauSacAdapter;
import com.example.appbangiayonline.adapter.SizeAdapter;
import com.example.appbangiayonline.convert.ConvertImage;
import com.example.appbangiayonline.dao.CTSanPhamDao;

import com.example.appbangiayonline.dao.HoaDonCT_Dao;
import com.example.appbangiayonline.dao.HoaDonDao;
import com.example.appbangiayonline.dao.NhanVien_KhachHang_Dao;


import com.example.appbangiayonline.model.CTSanPham;
import com.example.appbangiayonline.model.HoaDon;
import com.example.appbangiayonline.model.KhachHang;

import com.example.appbangiayonline.model.SanPham;
import com.example.appbangiayonline.zalo_pay.ZaloPay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;


public class ManHinh_CTSanPham extends AppCompatActivity implements OnItemClickMauSize {
    String tenchung;

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
    CTSanPham ctSanPham;

    // Biến lưu trữ dữ liệu được chọn từ RecyclerViews

    //nhan vao item
    TextView nhantenct;
    TextView nhankichco;
    TextView nhanmausac;
    TextView nhantensanpham;
    TextView nhangia;
    TextView nhansiluong;
    //lưu dữ được nhiều lần chọn
    private String selectedMauSac;
    private int selectedKichCo;
    //GOi Gio Hang het ra de su dung

    //cong tru
    int tongSoLuongSP = 1;
    int tongGiaSP;
    //cong tru soluong tongtien
    ImageView imgCong;
    ImageView imgTru;
    TextView muangay_soluong;
    TextView muangay_tongtien;
    ImageView quaylai_rc_sanpham, img_ctsp;
    //Xac nhan mua ngay
    Button btnxacnhanhoadon;
    //KhachHang
    NhanVien_KhachHang_Dao dao_nv_kh;
    //HoaDon
    HoaDonDao daohd;
    HoaDonAdapter adapterhd;
    ArrayList<HoaDon> listhd;
    //new soluong moi
    int newslsanpham;
    //lay mactsp để cập nhật số lương mới nè
    int laymactsp;
    //biến thêm số lượng mới
    TextView themSL_CTSanPham;
    Bitmap bitmap;
    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ctsan_pham);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        SharedPreferences sharedPreferences = getSharedPreferences("admin", Context.MODE_PRIVATE);
        check = sharedPreferences.getInt("setting", 0);

        giohang = findViewById(R.id.giohang_sanpham);
        muaNgay = findViewById(R.id.muangay_sanpham);
        if (check != 2) {
            giohang.setVisibility(View.GONE);
            muaNgay.setText("Xem chi tiết");
        }

        quaylai_rc_sanpham = findViewById(R.id.quaylai_rc_sanpham);
        giohang.setOnClickListener(view -> {
            themGioHang();
        });
        quaylai_rc_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra("obj_sanpham")) {

            SanPham sanPham = (SanPham) intent.getSerializableExtra("obj_sanpham");
            bitmap = ConvertImage.ByteToBitmap(sanPham.getImage());
            tenchung = sanPham.getTensanpham();
            nhanten = findViewById(R.id.tensanpham_sanpham);
            img_ctsp = findViewById(R.id.anhSanpham_ctSanpham);
            img_ctsp.setImageBitmap(bitmap);
            nhanten.setText(tenchung);

            muaNgay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCTSanPham();
                }
            });
        }
    }

    private void showCTSanPham() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_muangay);

        TextView txtspct = dialog.findViewById(R.id.txtspct);
        ImageView quaylai = dialog.findViewById(R.id.quaylai);
        themSL_CTSanPham = dialog.findViewById(R.id.themSoLuongMoi);

        themSL_CTSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                them_SL_CTSanPhamMoi();
            }
        });
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
        if (check != 2) {
            btnxacnhanhoadon.setVisibility(View.GONE);
        }
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


        if (check == 2) fl.setVisibility(View.GONE);
        themSL_CTSanPham.setVisibility(View.GONE);
        rcctsanpham.setVisibility(View.GONE);
        txtspct.setVisibility(View.GONE);
//        nhan vao item
        nhantenct = dialog.findViewById(R.id.tensanphamct);
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

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnomation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    //zalo//
    public void thanhToan(String giatien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_xacnhanmua, null);
        builder.setView(v);

        AlertDialog dialog = builder.create();
        RadioButton rdbtn_ttnhanhang = v.findViewById(R.id.rdbtn_ttkhinhan);
        RadioButton rdbtn_ttzalo = v.findViewById(R.id.rdbtn_ttzalo);
        LinearLayout layout = v.findViewById(R.id.layout_zalo);
        TextView code_donhang = v.findViewById(R.id.txt_code_donhang);
        TextView tongtien = v.findViewById(R.id.txt_tongtien_thanhtoan);
        Button xacnhan = v.findViewById(R.id.btn_xacnhan_thanhtoan);

        rdbtn_ttnhanhang.setOnClickListener(view -> {
            if (rdbtn_ttnhanhang.isChecked()) {
                layout.setVisibility(View.GONE);
            }
        });

        rdbtn_ttzalo.setOnClickListener(view -> {
            if (rdbtn_ttzalo.isChecked()) {
                layout.setVisibility(View.VISIBLE);
            }
        });
        String code = ZaloPay.createHoadon(giatien);
        code_donhang.setText(code);
        tongtien.setText(giatien + " VND");

        xacnhan.setOnClickListener(view -> {
            if (!rdbtn_ttnhanhang.isChecked() && !rdbtn_ttzalo.isChecked()) {
                Toast.makeText(this, "Bạn chưa chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
            } else {
                if (rdbtn_ttnhanhang.isChecked()) {
                    dialog.dismiss();
                    Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                }
                if (rdbtn_ttzalo.isChecked()) {
                    ZaloPaySDK.getInstance().payOrder(ManHinh_CTSanPham.this, code_donhang.getText().toString().trim(), "ctsp://app", new PayOrderListener() {
                        @Override
                        public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                            SharedPreferences sharedPreferences = getSharedPreferences("admin", MODE_PRIVATE);
                            String username = sharedPreferences.getString("taikhoan", "");

                            if (!TextUtils.isEmpty(username)) {
                                KhachHang khachHang = dao_nv_kh.getThongTinKhachHang(username);
                                int makh = khachHang.getMakh();
                                if (makh != 0) {
                                    //lay ngay,thangg, nam
                                    Date ngayDate = Calendar.getInstance().getTime();
                                    SimpleDateFormat ngDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    String ngay = ngDateFormat.format(ngayDate);
                                    //lay gio
                                    Date gioDate = new Date();
                                    SimpleDateFormat gioFormat = new SimpleDateFormat("HH:mm");
                                    String gio = gioFormat.format(gioDate);

                                    boolean row = daohd.addHoaDon(makh, tongGiaSP, ngay, gio);
                                    if (row) {
                                        listhd.clear();
                                        listhd.addAll(daohd.getDSHoaDon());
                                        adapterhd.notifyDataSetChanged();
                                        int mahd = daohd.mahd();
                                        //Cap nhat so luong moi trong sql ne

                                        boolean ktsoluongmoi = dao.capNhatSoLuongMoi(laymactsp, newslsanpham);
                                        if (ktsoluongmoi) {

                                            list.clear();
                                            list.addAll(dao.getListCTSanPham(tenchung));
                                            adapter.notifyDataSetChanged();

                                            HoaDonCT_Dao daoCTHD = new HoaDonCT_Dao(ManHinh_CTSanPham.this);
                                            boolean addCTHD = daoCTHD.themCTHD(mahd, laymactsp, tongSoLuongSP);

                                            if (addCTHD) {
                                                list.clear();
                                                list.addAll(dao.getListCTSanPham(tenchung));
                                                adapter.notifyDataSetChanged();
                                                Toast.makeText(ManHinh_CTSanPham.this, "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                                            }

                                            Intent intent1 = new Intent(ManHinh_CTSanPham.this, MainActivity.class);
                                            intent1.putExtra("gethoadon", ":D");
                                            Toast.makeText(ManHinh_CTSanPham.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            startActivity(intent1);

                                        } else {
                                            Toast.makeText(ManHinh_CTSanPham.this, "Cap nhat so luong that bai roi", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                } else {
                                    Toast.makeText(ManHinh_CTSanPham.this, "Khong ton tai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onPaymentCanceled(String zpTransToken, String appTransID) {
                            Toast.makeText(ManHinh_CTSanPham.this, "Bạn đã hủy thanh toán!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                            Toast.makeText(ManHinh_CTSanPham.this, "Lỗi thanh toán!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        dialog.show();
    }

    /////
    private void load(String tenchung) {
        rcctsanpham.setLayoutManager(new LinearLayoutManager(ManHinh_CTSanPham.this, RecyclerView.HORIZONTAL, false));
        list = dao.getListCTSanPham(tenchung);
        adapter = new CTSanPhamAdapter(ManHinh_CTSanPham.this, list);
        rcctsanpham.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // Khi dữ liệu đã được lấy xong, gọi phương thức onDataLoaded()
    }

    private void loadDSKichCo(String tansanpham) {
        rckichco.setLayoutManager(new LinearLayoutManager(ManHinh_CTSanPham.this, RecyclerView.HORIZONTAL, false));
        list = dao.getListDSMauSize(tansanpham);
        kichCoAdapter = new SizeAdapter(ManHinh_CTSanPham.this, list, new OnItemClickMauSize() {
            @Override
            public void onItemClick(CTSanPham ctSanPham) {
                nhankichco.setText(String.valueOf(ctSanPham.getKichco()));
                nhantenct.setText(ctSanPham.getTensanpham());
                //load lại số lượng, tổng giá về con số 0
                muangay_soluong.setText("1");
                muangay_tongtien.setText("0");
                selectedKichCo = ctSanPham.getKichco();
                mauSize(selectedMauSac, selectedKichCo);
            }
        });
        rckichco.setAdapter(kichCoAdapter);
        kichCoAdapter.notifyDataSetChanged();
    }

    private void loadDSMau(String tenchung) {
        rcmau.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        list = dao.getListDSMauSize(tenchung);
        mauSacAdapter = new MauSacAdapter(ManHinh_CTSanPham.this, list, new OnItemClickMauSize() {
            @Override
            public void onItemClick(CTSanPham ctSanPham) {
                nhanmausac.setText(ctSanPham.getTenmausac());
                nhantenct.setText(ctSanPham.getTensanpham());
                //load lại số lượng, tổng giá về con số 0
                muangay_soluong.setText("1");
                muangay_tongtien.setText("0");
                //o day minh dat cho no ten bien de de dang goi
                selectedMauSac = ctSanPham.getTenmausac();
                //thuc hiện truyền dữ liệu trước b1
                mauSize(selectedMauSac, selectedKichCo);
            }
        });
        rcmau.setAdapter(mauSacAdapter);
        mauSacAdapter.notifyDataSetChanged();
    }

    private void mauSize(String selectedMauSac, int selectedKichCo) {
        if (selectedMauSac != null && selectedKichCo != 0) {
            ctSanPham = new CTSanPham();
            ctSanPham = dao.getItemCTSanPham(selectedMauSac, selectedKichCo);
            //Hiện thị thông tin sản phẩm khi click cả 2
            if (ctSanPham != null) {
                laymactsp = ctSanPham.getMactsanpham();
                nhanmausac.setText("Màu: " + selectedMauSac);
                nhankichco.setText("Kích cỡ: " + Integer.toString(selectedKichCo));
                nhangia.setText("Giá: " + Integer.toString(ctSanPham.getGia()));
                nhansiluong.setText("Số lượng: " + Integer.toString(ctSanPham.getSoluong()));
                tongGiaSP = ctSanPham.getGia();
                //CHÚ Ý NÈ: Cong tru va sotien, soluong
                if (imgCong != null) {
                    tongSoLuongSP = 1;
                    muangay_tongtien.setText(Integer.toString(tongGiaSP));
                    imgCong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (tongSoLuongSP >= 0 && ctSanPham != null) {
                                tongSoLuongSP++;
                                tongGiaSP = ctSanPham.getGia() * tongSoLuongSP;
                                muangay_soluong.setText(String.valueOf(tongSoLuongSP));
                                muangay_tongtien.setText("Tổng tiền: " + String.valueOf(tongGiaSP));
                            } else {
                                imgCong.setClickable(false);
                                imgTru.setClickable(false);
                            }
                        }
                    });
                }

                if (imgTru != null) {
                    tongSoLuongSP = 1;
                    muangay_tongtien.setText(Integer.toString(tongGiaSP));
                    imgTru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (tongSoLuongSP >= 1 && ctSanPham != null) {
                                tongSoLuongSP--;
                                tongGiaSP = ctSanPham.getGia() * tongSoLuongSP;
                                muangay_soluong.setText(String.valueOf(tongSoLuongSP));
                                muangay_tongtien.setText("Tổng tiền: " + String.valueOf(tongGiaSP));
                            } else {
                                imgCong.setClickable(false);
                                imgTru.setClickable(false);
                            }
                        }
                    });
                }
            } else {
                nhangia.setText("Giá: " + "0");
                nhansiluong.setText("Số lượng: " + "0");
            }
        } else {
            nhangia.setText("Giá: " + "0");
            nhansiluong.setText("Số lượng: " + "0");
        }
    }


    @Override
    public void onItemClick(CTSanPham ctSanPham) {
        if (selectedMauSac != null && selectedKichCo != 0) {
            mauSize(selectedMauSac, selectedKichCo);
        }
//        else {
//
//        }
    }

    private void themsanpham(String tenchung) {
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
                        boolean row = dao.ThemCTSanPham(tenchung, mau, giaa, soluongg, size);
                        if (row) {
                            Log.d(TAG, "Thêm thành công");
                            list.clear();
                            list.addAll(dao.getListCTSanPham(tenchung));
                            load(tenchung);
                            loadDSKichCo(tenchung);
                            loadDSMau(tenchung);
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

        ctSanPham = new CTSanPham();
        ctSanPham = dao.getItemCTSanPham(selectedMauSac, selectedKichCo);
        //qua bat luc nen phai use flag :<


        if (ctSanPham != null) {
            daohd = new HoaDonDao(this);
            listhd = daohd.getDSHoaDon();
            adapterhd = new HoaDonAdapter(this, listhd);
            dao_nv_kh = new NhanVien_KhachHang_Dao(this);

            if (xemConSoLuong(tongSoLuongSP)) {
                laySoLuongMoi(tongSoLuongSP);
                thanhToan(String.valueOf(tongGiaSP));
            } else {
                Toast.makeText(this, "Sản phẩm này đã hết ạ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Không tồn tại sản phẩm này", Toast.LENGTH_SHORT).show();
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

    //SL cũ + sl mới = sl cần cập nhật
    private void them_SL_CTSanPhamMoi() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        LayoutInflater inflater1 = getLayoutInflater();
        View view1 = inflater1.inflate(R.layout.them_sl_ctsanpham, null);
        builder1.setView(view1);
        builder1.setTitle("Bạn có muốn thêm số lượng mới ?");
        EditText txtma = view1.findViewById(R.id.mactsanpham_them_sl);
        EditText txtsl = view1.findViewById(R.id.soluongthem_sl);
        builder1.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!TextUtils.isEmpty(txtma.getText().toString()) && !TextUtils.isEmpty(txtsl.getText().toString())) {
                    int ma = Integer.parseInt(txtma.getText().toString());
                    int newSl = Integer.parseInt(txtsl.getText().toString());
                    if (!TextUtils.isEmpty(txtma.getText().toString()) && !TextUtils.isEmpty(txtsl.getText().toString())) {
                        int oldSl = dao.getSL(ma);
                        if (oldSl != 0) {
                            int slDuocCapNhat = newSl + oldSl;
                            dao.themSoLuongMoi(ma, slDuocCapNhat);
                            Toast.makeText(ManHinh_CTSanPham.this, "Thêm số lượng mới thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ManHinh_CTSanPham.this, "Số lượng không tồn tai", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ManHinh_CTSanPham.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder1.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder1.create();
        alertDialog.show();

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
        ImageView imageView = dialog.findViewById(R.id.img_sp_themgiohang);
        TextView sl_mua = dialog.findViewById(R.id.sl_sp_themgiohang);
        imageView.setImageBitmap(bitmap);

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
                    ctSanPham1.setSl_mua(slMua);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
