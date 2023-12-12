package com.example.appbangiayonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.convert.ConvertImage;
import com.example.appbangiayonline.dao.CTSanPhamDao;
import com.example.appbangiayonline.dao.Giohang_Dao;
import com.example.appbangiayonline.dao.HoaDonCT_Dao;
import com.example.appbangiayonline.dao.HoaDonDao;
import com.example.appbangiayonline.dao.NhanVien_KhachHang_Dao;
import com.example.appbangiayonline.model.CTSanPham;
import com.example.appbangiayonline.model.KhachHang;
import com.example.appbangiayonline.model.SanPham;
import com.example.appbangiayonline.zalo_pay.Api.CreateOrder;

import org.json.JSONObject;

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


public class ManHinh_CTSanPham extends AppCompatActivity {
    String tenchung;
    ImageButton giohang;
    TextView muaNgay;
    CTSanPhamDao dao;
    CTSanPham ctSanPham;
    //cong tru
    TextView imgCong;
    TextView imgTru;
    ImageView quaylai_rc_sanpham, img_ctsp;
    //KhachHang
    NhanVien_KhachHang_Dao dao_nv_kh;
    //HoaDon
    HoaDonDao daohd;
    int laymactsp;
    Bitmap bitmap;
    int check = 0;
    //-------
    int tongsl_Mua = 1;
    int tongSoLuongSP = 1;
    int tongGiaSP = 0;
    int temp_tongGiaSP = 0;
    private Button selectedButton_mausac;
    private Button selectedButton_kichco;
    TextView txt_slctsp;
    TextView muangay_soluong;
    TextView muangay_tongtien;
    private int newslsanpham;
    HoaDonCT_Dao daoCTHD;

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
        dao_nv_kh = new NhanVien_KhachHang_Dao(this);
        daohd = new HoaDonDao(this);
        daoCTHD = new HoaDonCT_Dao(ManHinh_CTSanPham.this);

        if (check != 2) {
            giohang.setVisibility(View.GONE);
            muaNgay.setText("Xem chi tiết");
        }

        quaylai_rc_sanpham = findViewById(R.id.quaylai_rc_sanpham);

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
            img_ctsp = findViewById(R.id.anhSanpham_ctSanpham);
            img_ctsp.setImageBitmap(bitmap);
            TextView tenctsp = findViewById(R.id.txt_tensp_ctsp);
            tenctsp.setText(sanPham.getTensanpham());
            muaNgay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XacNhanMuaNgay(tongSoLuongSP, tongGiaSP);
                }
            });

        }
        //CHÚ Ý NÈ: Cong tru va sotien, soluong
        imgCong = findViewById(R.id.imgCong);
        imgTru = findViewById(R.id.imgTru);
        txt_slctsp = findViewById(R.id.txt_tongsoluong_ctsp);

        muangay_soluong = findViewById(R.id.txt_soluong_ctsp);
        muangay_tongtien = findViewById(R.id.tongtien_ctsp);
        muangay_soluong.setText("" + tongsl_Mua);

        dao = new CTSanPhamDao(this);

        giohang.setOnClickListener(view -> {
            themGioHang();
        });

        imgCong.setOnClickListener(v -> {
            if (tongsl_Mua < tongSoLuongSP) {
                tongsl_Mua++;
                muangay_soluong.setText(tongsl_Mua + "");
                tongGiaSP += temp_tongGiaSP;
                muangay_tongtien.setText(tongGiaSP + "VND");
            }
        });

        imgTru.setOnClickListener(v -> {
            if (tongsl_Mua > 1) {
                tongsl_Mua--;
                tongGiaSP -= temp_tongGiaSP;
                muangay_soluong.setText(tongsl_Mua + "");
                muangay_tongtien.setText(tongGiaSP + "VND");
            }
        });
        //-----
        ArrayList<CTSanPham> mausac_kichco = dao.getListDSMauSize(tenchung);
        ArrayList<String> list_mausac = new ArrayList<>();
        ArrayList<String> list_kichco = new ArrayList<>();
        mausac_kichco.forEach(e -> {
            list_mausac.add(e.getTenmausac());
            list_kichco.add(e.getKichco() + "");
        });
        //---
        Set<String> set_mausac = new HashSet<>(list_mausac);
        Set<String> set_kichco = new HashSet<>(list_kichco);
        //----
        ArrayList<String> mausac = new ArrayList<>(set_mausac);
        ArrayList<String> kichco = new ArrayList<>(set_kichco);
        ///---
        GridLayout layout_mausac = findViewById(R.id.layout_mausac_ctsp);
        GridLayout layout_kichco = findViewById(R.id.layout_kichco_ctsp);
        createButtonMausac(layout_mausac, mausac);
        createButtonKichco(layout_kichco, kichco);
        //------
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
        //---
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(giatien);
            String code = data.getString("return_code");
            if (code.equals("1")) {
                code_donhang.setText(data.getString("zp_trans_token"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        tongtien.setText(giatien + " VND");
//--------
        xacnhan.setOnClickListener(view -> {
            if (!rdbtn_ttnhanhang.isChecked() && !rdbtn_ttzalo.isChecked()) {
                Toast.makeText(this, "Bạn chưa chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
            } else {
                if (rdbtn_ttnhanhang.isChecked()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("admin", MODE_PRIVATE);
                    String username = sharedPreferences.getString("taikhoan", "");

                    if (!TextUtils.isEmpty(username)) {
                        KhachHang khachHang = dao_nv_kh.getThongTinKhachHang(username);
                        ctSanPham = dao.getItemCTSanPham(selectedButton_mausac.getText().toString(),
                                Integer.parseInt(selectedButton_kichco.getText().toString()));
                        int makh = khachHang.getMakh();
                        laymactsp = ctSanPham.getMactsanpham();
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
                                int mahd = daohd.mahd();
                                boolean addCTHD = daoCTHD.themCTHD(mahd, laymactsp, tongSoLuongSP);
                                Intent intent1 = new Intent(ManHinh_CTSanPham.this, MainActivity.class);
                                intent1.putExtra("gethoadon", ":D");
                                Toast.makeText(ManHinh_CTSanPham.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                startActivity(intent1);
                            }
                        } else {
                            Toast.makeText(ManHinh_CTSanPham.this, "Khong ton tai", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (rdbtn_ttzalo.isChecked()) {
                    ZaloPaySDK.getInstance().payOrder(ManHinh_CTSanPham.this, code_donhang.getText().toString(), "sanpham://app", new PayOrderListener() {
                        @Override
                        public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                            SharedPreferences sharedPreferences = getSharedPreferences("admin", MODE_PRIVATE);
                            String username = sharedPreferences.getString("taikhoan", "");

                            if (!TextUtils.isEmpty(username)) {
                                KhachHang khachHang = dao_nv_kh.getThongTinKhachHang(username);
                                ctSanPham = dao.getItemCTSanPham(selectedButton_mausac.getText().toString(),
                                        Integer.parseInt(selectedButton_kichco.getText().toString()));
                                int makh = khachHang.getMakh();
                                laymactsp = ctSanPham.getMactsanpham();
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
                                        int mahd = daohd.mahd();
                                        daoCTHD.themCTHD(mahd, laymactsp, tongsl_Mua);

                                        Intent intent1 = new Intent(ManHinh_CTSanPham.this, MainActivity.class);
                                        intent1.putExtra("gethoadon", ":D");
                                        Toast.makeText(ManHinh_CTSanPham.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        startActivity(intent1);
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

    //Thêm vào giỏ
    private void themGioHang() {
        Intent intent = new Intent(ManHinh_CTSanPham.this, Activity_GioHang.class);
        if (selectedButton_mausac != null && selectedButton_kichco != null) {
            CTSanPham ctSanPham1 = dao.getItemCTSanPham_config(tenchung, selectedButton_mausac.getText().toString(), Integer.parseInt(selectedButton_kichco.getText().toString()));

            if (ctSanPham1 == null) {
                Toast.makeText(this, "Sản phẩm đã hết hàng :(", Toast.LENGTH_SHORT).show();
            } else if ((new Giohang_Dao(this).checkGioHang(ctSanPham1.getTenmausac(), ctSanPham1.getKichco()))) {
                Toast.makeText(this, "Sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
            } else {
                ctSanPham1.setSl_mua(tongsl_Mua);
                intent.putExtra("themgiohang", ctSanPham1);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Bạn hãy chọn màu sắc và kích cỡ phù hợp!", Toast.LENGTH_SHORT).show();
        }
    }

    private void XacNhanMuaNgay(int tongSoLuongSP, int tongGiaSP) {
        if (selectedButton_mausac != null && selectedButton_kichco != null) {
            ctSanPham = dao.getItemCTSanPham(selectedButton_mausac.getText().toString(), Integer.parseInt(selectedButton_kichco.getText().toString()));

            if (ctSanPham != null) {

                if (xemConSoLuong(tongSoLuongSP)) {
                    thanhToan(String.valueOf(tongGiaSP));
                } else {
                    Toast.makeText(this, "Sản phẩm này đã hết!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Sản phẩm này đã hết!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean xemConSoLuong(int tongSoLuongSP) {
        if (tongSoLuongSP <= 0) {
            Toast.makeText(this, " Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            return false;
        }
        return ctSanPham.getSoluong() >= tongSoLuongSP;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    public void createButtonMausac(GridLayout gridLayout, ArrayList<String> mausac) {
        int buttonCount = mausac.size();
        Button[] buttons = new Button[buttonCount];
        for (int i = 0; i < buttonCount; i++) {
            final int buttonIndex = i;
            buttons[i] = new Button(this);
            buttons[i].setText(mausac.get(i));
            buttons[i].setOnClickListener(view -> handleButtonClick_mausac(buttons[buttonIndex]));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            int marginInDp = 5;
            float scale = getResources().getDisplayMetrics().density;
            int marginInPixels = (int) (marginInDp * scale + 0.5f);
            params.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
            buttons[i].setLayoutParams(params);
            buttons[i].setBackgroundResource(R.drawable.btn_non_click);
            gridLayout.addView(buttons[i]);
        }
    }

    public void createButtonKichco(GridLayout gridLayout, ArrayList<String> kichco) {
        int buttonCount = kichco.size();
        Button[] buttons = new Button[buttonCount];
        for (int i = 0; i < buttonCount; i++) {
            final int buttonIndex = i;
            buttons[i] = new Button(this);
            buttons[i].setText(kichco.get(i));
            buttons[i].setOnClickListener(view -> handleButtonClick_kichco(buttons[buttonIndex]));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            int marginInDp = 5;
            float scale = getResources().getDisplayMetrics().density;
            int marginInPixels = (int) (marginInDp * scale + 0.5f);
            params.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
            buttons[i].setLayoutParams(params);
            buttons[i].setBackgroundResource(R.drawable.btn_non_click);
            gridLayout.addView(buttons[i]);
        }
    }

    private void handleButtonClick_mausac(Button clickedButton) {
        if (selectedButton_mausac != null) {
            selectedButton_mausac.setBackgroundResource(R.drawable.btn_non_click);
        }
        selectedButton_mausac = clickedButton;
        selectedButton_mausac.setBackgroundResource(R.drawable.btn_click);
        if (selectedButton_kichco != null) {
            changeTongTien();
        }
    }

    private void handleButtonClick_kichco(Button clickedButton) {
        if (selectedButton_kichco != null) {
            selectedButton_kichco.setBackgroundResource(R.drawable.btn_non_click);
        }
        selectedButton_kichco = clickedButton;
        selectedButton_kichco.setBackgroundResource(R.drawable.btn_click);
        if (selectedButton_mausac != null) {
            changeTongTien();
        }
    }

    public void changeTongTien() {
        CTSanPham sp = dao.getItemCTSanPham(selectedButton_mausac.getText().toString(), Integer.parseInt(selectedButton_kichco.getText().toString()));

        if (sp != null) {
            tongGiaSP = sp.getGia();
            tongSoLuongSP = sp.getSoluong();

        } else {
            tongGiaSP = 0;
            tongSoLuongSP = 0;

        }
        tongsl_Mua = 1;
        muangay_soluong.setText(tongsl_Mua + "");
        txt_slctsp.setText("Số lượng: " + tongSoLuongSP);
        muangay_tongtien.setText(tongGiaSP + "VND");
        temp_tongGiaSP = tongGiaSP;
    }
}