package com.example.appbangiayonline.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.adapter_giohang;
import com.example.appbangiayonline.dao.Giohang_Dao;
import com.example.appbangiayonline.model.GioHang;

import java.util.ArrayList;

public class Activity_GioHang extends AppCompatActivity {
    Giohang_Dao dao;
    RecyclerView recyclerView;
    adapter_giohang adap;
    ArrayList<Integer> listchk = new ArrayList<>();
    ArrayList<GioHang> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__gio_hang);
        dao = new Giohang_Dao(this);
        recyclerView = findViewById(R.id.rcv_giohang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageButton back = findViewById(R.id.img_btn_back_giohang);
        back.setOnClickListener(view -> {
            finish();
        });
        reload();

        TextView edit = findViewById(R.id.edit_sp_giohang);
        edit.setOnClickListener(view -> {
            Toast.makeText(this, listchk.toString(), Toast.LENGTH_SHORT).show();
            listchk.clear();
            reload();
        });
    }

    void reload() {
        list = dao.getList();
        adap = new adapter_giohang(list, this);
        recyclerView.setAdapter(adap);
    }


    public void add_chck(int i) {
        listchk.add(i);
        Toast.makeText(this, listchk.toString(), Toast.LENGTH_SHORT).show();
    }


    public void rm_chck(int i) {
        listchk.remove(listchk.indexOf(i));
        Toast.makeText(this, listchk.toString(), Toast.LENGTH_SHORT).show();
    }
}
