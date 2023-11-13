package com.example.appbangiayonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appbangiayonline.R;

import org.w3c.dom.Text;

public class DangNhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        Button btn_login = findViewById(R.id.btn_dangnhap_dangnhap);
        TextView txt_signup = findViewById(R.id.txt_dangki_dangnhap);

        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(DangNhap.this, MainActivity.class);
            startActivity(intent);
        });
        txt_signup.setOnClickListener(view -> {
            Intent intent = new Intent(DangNhap.this, DangKi.class);
            startActivity(intent);
        });
        
    }
}