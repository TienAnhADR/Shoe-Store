package com.example.appbangiayonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.model.HoaDon;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.Viewholder> {
    private Context context;
    private final ArrayList<HoaDon> list;

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
        holder.ma.setText(Integer.toString(list.get(position).getMahoadon()));
        holder.tennv.setText(list.get(position).getTennv());
        holder.tenkh.setText(list.get(position).getTenkh());
        holder.tongsl.setText(Integer.toString(list.get(position).getTongsl()));
        holder.tongtien.setText(Integer.toString(list.get(position).getTongTien()));
        holder.trangthai.setText(Integer.toString(list.get(position).getTrangthai()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView ma, tenkh, tennv, tongsl, tongtien, trangthai;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.txtma_Hoadon);
            tennv = itemView.findViewById(R.id.txttennhanvien_Hoadon);
            tenkh = itemView.findViewById(R.id.txttenkh_Hoadon);
            tongsl= itemView.findViewById(R.id.txttongsl_Hoadon);
            tongtien= itemView.findViewById(R.id.txttongtien_Hoadon);
            trangthai= itemView.findViewById(R.id.txttrangthai_Hoadon);
        }
    }
}
