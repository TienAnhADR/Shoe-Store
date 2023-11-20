package com.example.appbangiayonline.fragmentTA;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.activity.ManHinh_CTSanPham;
import com.example.appbangiayonline.adapter.SanPhamAdapter;
import com.example.appbangiayonline.dao.SanPhamDao;
import com.example.appbangiayonline.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentSanPham extends Fragment {

    SanPhamDao dao;
    ArrayList<SanPham> list;
    SanPhamAdapter adapter;

    FloatingActionButton fl ;
    RecyclerView rc_sanpham  ;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_san_pham, container, false);
         fl = view1.findViewById(R.id.fl_shoes_tab2);
         rc_sanpham = view1.findViewById(R.id.rc_shoes_tab2);
          rc_sanpham.setLayoutManager(new GridLayoutManager(getContext(), 1));
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

        return view1;
    }

    private void ThemSanPham() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.them_sanpham, null);
        builder.setView(dialogView);
        builder.setTitle("Thêm Sản Phẩm");

        EditText txtten = dialogView.findViewById(R.id.tensanpham_shoes_tab_them);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ten = txtten.getText().toString();
                if (!TextUtils.isEmpty(ten)) {
                    SanPham sanPham = new SanPham();
                    sanPham.setTensanpham(ten);
                    boolean kt = dao.ThemSanPham(sanPham);
                    if (kt) {
                        list.clear();
                        list.addAll(dao.getListSanPham());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Chưa nhập tên", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void update_Sp() {
//._.
    }

    //click item edit
    public void click_item(int i) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(requireActivity(), ManHinh_CTSanPham.class);
        bundle.putInt("vitri", i);
        intent.putExtras(bundle);
        requireActivity().startActivity(intent);
    }
}
