package com.example.appbangiayonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.fragmentTA.FragmentKhachHang;
import com.example.appbangiayonline.fragmentTA.FragmentNhanVien;
import com.example.appbangiayonline.fragmentTA.FragmentThongTin;
import com.example.appbangiayonline.fragmentTA.Fragment_Main;
import com.example.appbangiayonline.tab.Shoes_tab;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        NavigationView navigationView = findViewById(R.id.navigationV);
        drawerLayout = findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new FragmentKhachHang();
                int id = item.getItemId();
                if (R.id.mQLSanPham == id) {


                } else if (R.id.mQLNhanVien == id) {
                    fragment = new FragmentNhanVien();

                } else if (R.id.mQLKhachHang == id) {
                    fragment = new FragmentKhachHang();

                } else if (R.id.mQLHoaDon == id) {

                } else if (R.id.mThongKe == id) {

                } else if (R.id.mDoiMK == id) {


                } else if (R.id.mDangXuat == id) {
                    Intent intent = new Intent(MainActivity.this, DangNhap.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                change_Fragment(fragment, item.getTitle().toString());
                return false;
            }
        });

        //changeTab
        Button btn_home = findViewById(R.id.btn_home_fg);
        Button btn_shoe = findViewById(R.id.btn_shoes_fg);
        Button btn_user = findViewById(R.id.btn_user_fg);
        Button cart = findViewById(R.id.btn_cart_fg);
        cart.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Activity_GioHang.class);
            startActivity(intent);
        });
        btn_home.setOnClickListener(view -> {
            change_Fragment(new Fragment_Main(), "Trang chủ");
        });
        btn_shoe.setOnClickListener(view -> {
            change_Fragment(new Shoes_tab(), "Sản phẩm");

        });
        btn_user.setOnClickListener(view -> {
            change_Fragment(new FragmentThongTin(), "Thông tin khách hàng");
        });
    }

    void change_Fragment(Fragment fragment, String title) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flameLayout, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        toolbar.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}