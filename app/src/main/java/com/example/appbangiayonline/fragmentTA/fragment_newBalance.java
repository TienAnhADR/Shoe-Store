package com.example.appbangiayonline.fragmentTA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.SanPhamAdapter;
import com.example.appbangiayonline.dao.SanPhamDao;
import com.example.appbangiayonline.model.SanPham;

import java.util.ArrayList;

public class fragment_newBalance extends Fragment {
    SanPhamDao dao;
    SanPhamAdapter adapter;
    ArrayList<SanPham> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newbalance, container, false);
        RecyclerView rc = view.findViewById(R.id.rc_newBalance);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
        dao = new SanPhamDao(getContext());
        list = new ArrayList<>();
        list = dao.getListSanPham();
        adapter = new SanPhamAdapter(getContext(), list);
        rc.setAdapter(adapter);
        String hang = "New balance";
        hang.trim();//khoảng trắng
        ArrayList<SanPham> listnew = new ArrayList<>();
        for (SanPham sanPham : list){
            if(sanPham != null && hang.equalsIgnoreCase(sanPham.getHang())){
                listnew.add(sanPham);
            }
        }
        adapter.setData((ArrayList<SanPham>) listnew);
        adapter.notifyDataSetChanged();
        return view;
    }
}


