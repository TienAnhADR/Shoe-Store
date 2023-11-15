package com.example.appbangiayonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.model.KhachHang;
import com.example.appbangiayonline.model.NhanVien;

import java.util.ArrayList;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder>{
    private Context context;
    private ArrayList<NhanVien> list;

    public NhanVienAdapter(Context context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NhanVienAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khach_hang, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NhanVien kh = list.get(position);
        holder.txtTen.setText("Tên : "+kh.getHoten());
        holder.txtSDT.setText("SĐT : "+kh.getSdt());
        holder.txtEmail.setText("Email : "+kh.getEmail());
        String chucVu = kh.getChucvu()==0 ? "Nhân viên" : "Admin";
        holder.txtDiaChi.setText("Chức vụ : "+ chucVu);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen,txtSDT,txtEmail,txtDiaChi;
        ImageView btnSua,btnXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmail = itemView.findViewById(R.id.txtEmail_KH);
            txtSDT = itemView.findViewById(R.id.txtSDT_KH);
            txtTen = itemView.findViewById(R.id.txtTenKH);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi_KH);
            btnSua = itemView.findViewById(R.id.img_btn_update_KH);
            btnXoa = itemView.findViewById(R.id.img_btn_delete_KH);
        }
    }
}
