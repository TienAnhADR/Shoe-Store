package com.example.appbangiayonline.fragmentTA;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.HoaDonAdapter;
import com.example.appbangiayonline.dao.HoaDonDao;
import com.example.appbangiayonline.model.HoaDon;

import java.util.ArrayList;

public class FragmentThongTin extends Fragment {
    public FragmentThongTin() {

    }

    RecyclerView recyclerView;
    HoaDonAdapter adapter;
    HoaDonDao dao;
    ArrayList<HoaDon> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin, container, false);
        recyclerView = view.findViewById(R.id.rchoadon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        list = new ArrayList<>();
        dao = new HoaDonDao(getContext());
        list = dao.getDSHoaDon();
        adapter = new HoaDonAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}