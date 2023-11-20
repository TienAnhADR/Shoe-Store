package com.example.appbangiayonline.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.activity.ManHinh_CTSanPham;
import com.example.appbangiayonline.dao.SanPhamDao;
import com.example.appbangiayonline.fragmentTA.FragmentSanPham;
import com.example.appbangiayonline.model.SanPham;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.Viewholder> {
    private Context context;
    private FragmentSanPham fragment;
    private final ArrayList<SanPham> list;
    SanPham sp1;
    //    SanPham sp;
    SanPhamDao daosp;

    public SanPhamAdapter(Context context, FragmentSanPham fragment, ArrayList<SanPham> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SanPhamAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sp1 = list.get(position);

        holder.ma.setText("Ma san pham: " + sp1.getMasanpham());
        holder.ten.setText("Ten san pham: " + sp1.getTensanpham());
        holder.trangthai.setText("Trang thai: " + sp1.getTrangthai());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("admin", Context.MODE_PRIVATE);
                int check = sharedPreferences.getInt("setting", 2);
                if (check == 2) {
                    if (sp1.getTrangthai().equalsIgnoreCase("Còn hàng")) {
                        //truyen du lieu
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(context, ManHinh_CTSanPham.class);
                        bundle.putString("tensanpham", sp1.getTensanpham());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        holder.itemView.setClickable(true);
                        Toast.makeText(context, "Sản phẩm đang ở trạng thái còn hàng", Toast.LENGTH_SHORT).show();

                    } else {

                    }
                }else{
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, ManHinh_CTSanPham.class);
                    bundle.putString("tensanpham", sp1.getTensanpham());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    holder.itemView.setClickable(true);
                    Toast.makeText(context, "Sản phẩm đang ở trạng thái còn hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSP(sp1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView ma, ten, trangthai;
        ImageView update;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.masanpham_shoes_tab);
            ten = itemView.findViewById(R.id.tensanpham_shoes_tab);
            trangthai = itemView.findViewById(R.id.trangthai_shoes_tab);
            update = itemView.findViewById(R.id.update_sanpham);
        }
    }
    private void updateSP(SanPham sp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.capnhat_sanpham, null);
        builder.setView(view);
        EditText txtten = view.findViewById(R.id.tensanpham_capnhat);
        EditText txttrangthai = view.findViewById(R.id.trangthai_capnhat);
        txtten.setText(sp.getTensanpham());

        builder.setNegativeButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        txttrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] chon = new String[]{"Còn hàng", "Ngừng bán"};
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Chon trang thai");
                builder1.setItems(chon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String luaChon = chon[i];
                        txttrangthai.setText(luaChon);
                    }
                });
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
            }
        });

        builder.setPositiveButton("Cap nhat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sp.setTensanpham(txtten.getText().toString());
                sp.setTrangthai(txttrangthai.getText().toString());

                daosp = new SanPhamDao(context);
                boolean kt = daosp.SuaSanPham(sp);
                if (kt) {
                    notifyItemChanged(list.indexOf(sp));

                    Toast.makeText(context, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cap nhat that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
