package com.example.appbangiayonline.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.activity.DangNhap;
import com.example.appbangiayonline.activity.MainActivity;
import com.example.appbangiayonline.dao.HoaDonDao;
import com.example.appbangiayonline.dao.NhanVien_KhachHang_Dao;
import com.example.appbangiayonline.model.HoaDon;
import com.example.appbangiayonline.model.KhachHang;
import com.example.appbangiayonline.model.NhanVien;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.Viewholder> {
    private Context context;
    private final ArrayList<HoaDon> list;
    NhanVien_KhachHang_Dao dao_nv_kh;

    public HoaDonAdapter(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.ma.setText("Mã hóa đơn : " +Integer.toString(list.get(position).getMahoadon()));
        holder.tennv.setText("Tên nhân viên : " +list.get(position).getTennv());
        holder.tenkh.setText("Tên khách hàng : " +list.get(position).getTenkh());
        holder.tongsl.setText("Tổng số lượng : " +Integer.toString(list.get(position).getTongsl()));
        holder.tongtien.setText("Tổng tiền : " +Integer.toString(list.get(position).getTongTien()));
        holder.btnxacnhan.setText(Integer.toString(list.get(position).getTrangthai()));

        int mahd = list.get(position).getMahoadon();
        String trangthai = "";
        if(list.get(position).getTrangthai() == 0){
            trangthai ="Xac nhan";
            holder.tennv.setVisibility(View.INVISIBLE);
            holder.tennv.setText("Tên nhân viên: " + list.get(position).getTennv());
         }else{
            trangthai = "Da xac nhan";
            holder.tennv.setVisibility(View.VISIBLE);
            holder.btnxacnhan.setVisibility(View.GONE);
            holder.tennv.setText("Tên nhân viên: " + list.get(position).getTennv());
        }
        holder.trangthai_texthd.setText("Trạng thái: " + trangthai);

        SharedPreferences sharedPreferences = context.getSharedPreferences("admin", MODE_PRIVATE);
        int check = sharedPreferences.getInt("setting", 2);
        if(check == 2){
            holder.btnxacnhan.setVisibility(View.GONE);
        }else{
            holder.btnxacnhan.setText("Xác nhận");
        }
        holder.btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDonDao dao = new HoaDonDao(context);
                SharedPreferences sharedPreferences = context.getSharedPreferences("admin", MODE_PRIVATE);
                String taikhoan = sharedPreferences.getString("taikhoan", "");

                if (!TextUtils.isEmpty(taikhoan)) {
                    dao_nv_kh = new NhanVien_KhachHang_Dao(context);
                    NhanVien nhanVien = dao_nv_kh.getThongTinNhanVien(taikhoan);

                    if (nhanVien != null) {
                        int manv = nhanVien.getManv();

                        if (manv != 0) {
                            boolean kt = dao.thayDoiTrangThaiHoaDon(mahd, manv);

                            if (kt) {
                                list.clear();
                                list.addAll(dao.getDSHoaDon());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xac nhan thanh cong", Toast.LENGTH_SHORT).show();
                                holder.btnxacnhan.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(context, "Xac nhan that bai", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Manv khong ton tai", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Thong tin nhanvien khong ton tai", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Tai khoan nhanvien khong ton tai", Toast.LENGTH_SHORT).show();
                }

                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView ma, tenkh, tennv, tongsl, tongtien, trangthai_texthd;
        Button btnxacnhan;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.txtma_Hoadon);
            tennv = itemView.findViewById(R.id.txttennhanvien_Hoadon);
            tenkh = itemView.findViewById(R.id.txttenkh_Hoadon);
            tongsl= itemView.findViewById(R.id.txttongsl_Hoadon);
            tongtien= itemView.findViewById(R.id.txttongtien_Hoadon);
            trangthai_texthd= itemView.findViewById(R.id.trangthai_text_hoadon);
            btnxacnhan = itemView.findViewById(R.id.txttrangthai_Hoadon);
        }
    }
}
