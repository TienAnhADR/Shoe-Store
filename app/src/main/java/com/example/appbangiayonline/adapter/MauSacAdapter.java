package com.example.appbangiayonline.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.activity.OnItemClickMauSize;
import com.example.appbangiayonline.model.CTSanPham;

import java.util.ArrayList;
import java.util.HashMap;

public class MauSacAdapter extends RecyclerView.Adapter<MauSacAdapter.Viewholder> {
    private Context context;
    private final ArrayList<CTSanPham> list;
    private OnItemClickMauSize onItemClickMauSize;
    private HashMap<String, Integer> mauCountMap = new HashMap<>();

    public MauSacAdapter(Context context, ArrayList<CTSanPham> list, OnItemClickMauSize onItemClickMauSize) {
        this.context = context;
        this.list = list;
        this.onItemClickMauSize = onItemClickMauSize;
    }

    @NonNull
    @Override
    public MauSacAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mausac, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MauSacAdapter.Viewholder holder, int position) {
        CTSanPham ctSanPham = list.get(position);
        holder.mau.setText(list.get(position).getTenmausac());


        String tenmau = list.get(position).getTenmausac();

        int count = mauCountMap.getOrDefault(tenmau, 0);


        if (count == 0) {
            holder.mau.setText(tenmau);
//            holder.mau.setBackgroundColor(getColorFromName(tenmau));
        } else {
            holder.mau.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(0, 0);
            holder.itemView.setLayoutParams(params);
        }

        mauCountMap.put(tenmau, count + 1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickMauSize.onItemClick(ctSanPham);
            }
        });
    }
    private int getColorFromName(String tenmau) {
        try {
            return Color.parseColor(tenmau);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Color.BLACK;
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView mau;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mau = itemView.findViewById(R.id.mausacsanpham_ctsanpham);
        }
    }
}