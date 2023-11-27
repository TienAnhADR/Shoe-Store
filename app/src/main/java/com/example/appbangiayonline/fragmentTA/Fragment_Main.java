package com.example.appbangiayonline.fragmentTA;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.SanPhamAdapter;
import com.example.appbangiayonline.dao.ThongKeBanChayDao;
import com.example.appbangiayonline.model.SanPham;

import java.util.ArrayList;

public class Fragment_Main extends Fragment {
    SanPhamAdapter adapter;
    ThongKeBanChayDao tkdao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__main, container, false);

//        layout mac định banchay
        RecyclerView rc = view.findViewById(R.id.rc_macdinh);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
        tkdao = new ThongKeBanChayDao(getContext());
        ArrayList<SanPham> top10 = tkdao.getTop10();
        if (!top10.isEmpty()) {
            adapter = new SanPhamAdapter(getContext(), top10);
            rc.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "Null rồi", Toast.LENGTH_SHORT).show();
        }


        Button btn_banchay = view.findViewById(R.id.xuhuong);
        Button btn_nike = view.findViewById(R.id.nike);
        Button btn_newbalance = view.findViewById(R.id.newbalance);

        btn_banchay.setOnClickListener(buttonView  -> {
            change_Fragment(new Fragment_ThongKeBanChay());
        });
        btn_nike.setOnClickListener(buttonView  -> {
            change_Fragment(new fragment_Nike());

        });
        btn_newbalance.setOnClickListener(buttonView  -> {
            change_Fragment(new fragment_newBalance());
        });

        return view;
    }
    void change_Fragment(Fragment fragment) {
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_main, fragment).commit();
    }
}