package com.example.appbangiayonline.fragmentTA;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.SanPhamAdapter;
import com.example.appbangiayonline.dao.ThongKeBanChayDao;
import com.example.appbangiayonline.model.SanPham;

import java.util.ArrayList;

public class Fragment_ThongKeBanChay extends Fragment {

    SanPhamAdapter adapter;
    ThongKeBanChayDao tkdao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongkebanchay, container, false);
        RecyclerView rc = view.findViewById(R.id.rc_banchay);
        rc.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        tkdao = new ThongKeBanChayDao(getContext());
        ArrayList<SanPham> top10 = tkdao.getTop10();
        if (!top10.isEmpty()) {
            adapter = new SanPhamAdapter(getContext(), top10);
            rc.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "Null rồi", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
