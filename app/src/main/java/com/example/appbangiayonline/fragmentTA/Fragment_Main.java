package com.example.appbangiayonline.fragmentTA;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appbangiayonline.R;

public class Fragment_Main extends Fragment {

    public Fragment_Main() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__main, container, false);

        TextView txt_name = view.findViewById(R.id.txt_name_mainscreen);
        TextView txt_xemThem = view.findViewById(R.id.txt_xemthem_mainscreen);
        SharedPreferences sharedPreferences_ = requireActivity().getSharedPreferences("taikhoan", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("admin", Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("setting", 0) == 2) {
            txt_name.setText("Xin chào, " + sharedPreferences_.getString("name_kh", "" + "!"));
        } else {
            txt_name.setText("Xin chào admin!");
        }
        txt_xemThem.setOnClickListener(view1 -> {

        });
        return view;
    }
}