package com.example.appbangiayonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appbangiayonline.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static class QuanLyHoaDonActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quan_ly_hoa_don);
        }
    }

    public static class ThongKeActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_thong_ke);
        }
    }
}