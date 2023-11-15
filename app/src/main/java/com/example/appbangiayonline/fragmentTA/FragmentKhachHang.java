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
import com.example.appbangiayonline.adapter.KhachHangAdapter;
import com.example.appbangiayonline.dao.NhanVien_KhachHang_Dao;
import com.example.appbangiayonline.model.KhachHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentKhachHang extends Fragment {
    RecyclerView recyclerView;
    NhanVien_KhachHang_Dao dao;
    ArrayList<KhachHang> list;

    FloatingActionButton floadAdd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nhan_vien_ta,container,false);
        recyclerView = view.findViewById(R.id.recylerV_NhanVien);
        floadAdd = view.findViewById(R.id.fload_btn_Add_NhanVien);
        setAdapter();
        return view;
    }
    public void setAdapter(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        dao= new NhanVien_KhachHang_Dao(getContext());
        list = dao.getList_KH();
        KhachHangAdapter adapter = new KhachHangAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
    }
}
