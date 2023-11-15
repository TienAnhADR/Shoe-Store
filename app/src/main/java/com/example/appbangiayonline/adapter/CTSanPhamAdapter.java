package com.example.appbangiayonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.model.CTSanPham;

import java.util.ArrayList;

public class CTSanPhamAdapter extends RecyclerView.Adapter<CTSanPhamAdapter.Viewholder> {
    private Context context;
    private final ArrayList<CTSanPham> list;

    public CTSanPhamAdapter(Context context, ArrayList<CTSanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CTSanPhamAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ctsanpham, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CTSanPhamAdapter.Viewholder holder, int position) {
        holder.ma.setText(Integer.toString(list.get(position).getMactsanpham()));
        holder.tensp.setText(list.get(position).getTensanpham());
        holder.tenmau.setText(list.get(position).getTenmausac());
        holder.kichco.setText(Integer.toString(list.get(position).getKichco()));
        holder.soluong.setText(Integer.toString(list.get(position).getSoluong()));
        holder.gia.setText(Integer.toString(list.get(position).getGia()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView ma, tensp, tenmau, kichco, soluong, gia;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.mactsanpham_ctsanpham);
            tensp = itemView.findViewById(R.id.tensanpham_ctsanpham);
            tenmau = itemView.findViewById(R.id.tenmausac_ctsanpham);
            kichco = itemView.findViewById(R.id.sokicco_ctsanpham);
            soluong = itemView.findViewById(R.id.soluongsanpham_ctsanpham);
            gia = itemView.findViewById(R.id.giasanpham_ctsanpham);
        }
    }
}
