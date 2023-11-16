package com.example.appbangiayonline.tab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.SanPhamAdapter;
import com.example.appbangiayonline.dao.SanPhamDao;
import com.example.appbangiayonline.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Shoes_tab extends Fragment {
    SanPhamDao dao;
    ArrayList<SanPham> list;
    SanPhamAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shoes_tab, container, false);
        FloatingActionButton fl = view.findViewById(R.id.fl_shoes_tab);
        RecyclerView rc_sanpham = view.findViewById(R.id.rc_shoes_tab);
        rc_sanpham.setLayoutManager(new GridLayoutManager(getContext(), 2));
        list = new ArrayList<>();
        dao = new SanPhamDao(getContext());
        list = dao.getListSanPham();
        adapter = new SanPhamAdapter(getContext(), list);
        rc_sanpham.setAdapter(adapter);
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSanPham();
            }
        });
        return view;
    }
    private void ThemSanPham(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.them_sanpham, null);
        builder.setView(view);
        builder.setTitle("Ban co muon xoa khong ?");
        EditText txtten = view.findViewById(R.id.tensanpham_shoes_tab_them);
        builder.setPositiveButton("Them", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ten = txtten.getText().toString();
                if(!TextUtils.isEmpty(ten)){
                    SanPham sanPham = new SanPham();
                    sanPham.setTensanpham(ten);
                    boolean kt = dao.ThemSanPham(sanPham);
                    if(kt){
                        list.clear();
                        list.addAll(dao.getListSanPham());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Them san pham thanh cong", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Them san pham that bai", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Chua nhap ten", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}