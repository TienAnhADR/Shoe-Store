package com.example.appbangiayonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.activity.Activity_GioHang;
import com.example.appbangiayonline.model.GioHang;

import java.util.List;

public class adapter_giohang extends RecyclerView.Adapter<adapter_giohang.rcv_holder> {
    List<GioHang> list;
    Activity_GioHang context;
    int sl = 1;

    public adapter_giohang(List<GioHang> list, Activity_GioHang context) {
        this.list = list;
        this.context = context;
    }

    public class rcv_holder extends RecyclerView.ViewHolder {
        CheckBox chck_item;
        ImageView img_item;
        TextView tensp_item, mausac_item, giasp_item, sl_sp_item, kichco_item;

        Button tang_item, giam_item;

        public rcv_holder(@NonNull View itemView) {
            super(itemView);
            chck_item = itemView.findViewById(R.id.chck_item_giohang);
            img_item = itemView.findViewById(R.id.img_item_giohang);
            giasp_item = itemView.findViewById(R.id.txt_giasp_giohang);
            tensp_item = itemView.findViewById(R.id.txt_tensp_giohang);
            mausac_item = itemView.findViewById(R.id.txt_mausac_item_giohang);
            giam_item = itemView.findViewById(R.id.btn_giam_giohang);
            tang_item = itemView.findViewById(R.id.btn_tang_giohang);
            sl_sp_item = itemView.findViewById(R.id.sl_sp_item_giohang);
            kichco_item = itemView.findViewById(R.id.txt_kichco_giohang);
        }
    }

    @NonNull
    @Override
    public rcv_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new rcv_holder(LayoutInflater.from(context).inflate(R.layout.item_giohang, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull rcv_holder holder, int position) {
        holder.tensp_item.setText(list.get(position).getTensp());
        holder.mausac_item.setText("Màu: " + list.get(position).getMausac());
        holder.giasp_item.setText(list.get(position).getGiasp() + "");
        holder.sl_sp_item.setText(sl + "");
        holder.kichco_item.setText("Size: " + list.get(position).getKichco());
        holder.giam_item.setOnClickListener(view -> {
            if (sl > 1) {
                sl--;
                holder.sl_sp_item.setText(sl + "");
            }
        });
        holder.tang_item.setOnClickListener(view -> {
            sl++;
            holder.sl_sp_item.setText(sl + "");
        });
        holder.chck_item.setOnClickListener(view -> {
            if (holder.chck_item.isChecked()) {
                context.add_chck(list.get(position).getMagiohang());
            }
            if (!holder.chck_item.isChecked()) {
                context.rm_chck(list.get(position).getMagiohang());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
