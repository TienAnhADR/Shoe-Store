package com.example.appbangiayonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.dao.DangNhap_DangKi_Dao;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class DangNhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        Button btn_login = findViewById(R.id.btn_dangnhap_dangnhap);
        TextView txt_signup = findViewById(R.id.txt_dangki_dangnhap);
        DangNhap_DangKi_Dao dao = new DangNhap_DangKi_Dao(this);

        TextInputEditText input_taikhoan = findViewById(R.id.input_taikhoan_dangnhap);
        TextInputEditText input_matkhau = findViewById(R.id.input_matkhau_dangnhap);

        btn_login.setOnClickListener(view -> {
            String taikhoan = input_taikhoan.getText().toString().trim();
            String matkhau = input_matkhau.getText().toString().trim();
            int check = dao.dang_nhap(taikhoan, matkhau);

            if (check == 1 || check == 2 || check == 0) {
                Intent intent = new Intent(DangNhap.this, MainActivity.class);
                intent.putExtra("thongtin", check);
                startActivity(intent);
            }
        });
        txt_signup.setOnClickListener(view -> {
            Intent intent = new Intent(DangNhap.this, DangKi.class);
            startActivity(intent);
        });

    }
}