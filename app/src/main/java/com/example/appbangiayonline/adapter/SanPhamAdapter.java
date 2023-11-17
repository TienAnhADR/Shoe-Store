package com.example.appbangiayonline.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.activity.ManHinh_CTSanPham;
import com.example.appbangiayonline.model.SanPham;
import com.example.appbangiayonline.tab.Shoes_tab;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.Viewholder> {
    private Context context;
    private Shoes_tab fragment;
    private final ArrayList<SanPham> list;

    public SanPhamAdapter(Context context, Shoes_tab fragment, ArrayList<SanPham> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public SanPhamAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoes_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.Viewholder holder, int position) {
        SanPham sp = list.get(position);
        holder.ma.setText("Ma san pham: " + list.get(position).getMasanpham());
        holder.ten.setText("Ten san pham: " + list.get(position).getTensanpham());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //sao put posison?

//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(context, ManHinh_CTSanPham.class);
//                bundle.putString("tensanpham", sp.getTensanpham());
//                intent.putExtras(bundle);
//                bundle.putInt("pos", position);
//                context.startActivity(intent);

                fragment.click_item(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                fragment.update_Sp();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView ma, ten;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.masanpham_shoes_tab);
            ten = itemView.findViewById(R.id.tensanpham_shoes_tab);
        }
    }
}
