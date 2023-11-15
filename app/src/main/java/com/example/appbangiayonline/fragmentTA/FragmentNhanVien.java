package com.example.appbangiayonline.fragmentTA;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.activity.DangKi;
import com.example.appbangiayonline.activity.DangNhap;
import com.example.appbangiayonline.adapter.KhachHangAdapter;
import com.example.appbangiayonline.adapter.NhanVienAdapter;
import com.example.appbangiayonline.dao.NhanVien_KhachHang_Dao;
import com.example.appbangiayonline.model.KhachHang;
import com.example.appbangiayonline.model.NhanVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentNhanVien extends Fragment {
    RecyclerView recyclerView;
    NhanVien_KhachHang_Dao dao;
    ArrayList<NhanVien> list;

    FloatingActionButton floadAdd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nhan_vien_ta,container,false);
        recyclerView = view.findViewById(R.id.recylerV_NhanVien);
        floadAdd = view.findViewById(R.id.fload_btn_Add_NhanVien);
        setAdapter();
        floadAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThem();
            }
        });
        return view;
    }
    public void setAdapter(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        dao= new NhanVien_KhachHang_Dao(getContext());
        list = dao.getList_NV();
        NhanVienAdapter adapter = new NhanVienAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
    }
    int check =0;
    public void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_nhan_vien, null);
        builder.setView(view);
        EditText edtTen,edtUserName,edtPass,edtSDT,edtEmail;
        edtTen = view.findViewById(R.id.edtTenNhanVien_new);
        edtUserName =view.findViewById(R.id.edtTen_User_NhanVien_new);
        edtPass = view.findViewById(R.id.edtPassNhanVien_new);
        edtSDT = view.findViewById(R.id.edtSDT_NV_new);
        edtEmail = view.findViewById(R.id.edtEmail_NV_new);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hoten,taikhoan,matkhau,email,sdt;
                hoten = edtTen.getText().toString();
                taikhoan = edtUserName.getText().toString();
                matkhau = edtPass.getText().toString();
                email = edtEmail.getText().toString();
                sdt = edtSDT.getText().toString();
                if(edtTen.getText().toString().equals("")||edtSDT.getText().toString().equals("")||
                        edtUserName.getText().toString().equals("")||edtEmail.getText().toString().equals("")
                || edtPass.getText().toString().equals("")){

                    Toast.makeText(getContext(), "Không để trống dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
//                    String hoten, String taikhoan, String matkhau, String email, String sdt
                    int check = dao.newNhanVien(hoten, taikhoan, matkhau, email, sdt);
                    if (check == 0) {
                        Toast.makeText(getContext(), "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                    if (check == 1) {
                        Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        setAdapter();
                    }
                }

            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
