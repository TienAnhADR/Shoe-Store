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
import android.widget.FrameLayout;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.fragmentTA.FragmentKhachHang;
import com.example.appbangiayonline.fragmentTA.FragmentNhanVien;
import com.google.android.material.navigation.NavigationView;

public class MainActivity_Fragment extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlkhach_hang_nhan_vien);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FrameLayout frameLayout = findViewById(R.id.fameLayout);
        NavigationView navigationView = findViewById(R.id.navigationV);
        drawerLayout = findViewById(R.id.drawerLayout);
//        TextView txtUserName = findViewById(R.id.txtUserNameNVGTON);
        setSupportActionBar(toolbar);
//        setSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new FragmentKhachHang();
                int id = item.getItemId();
                if(R.id.mQLSanPham==id){


                }else if(R.id.mQLNhanVien==id){
                    fragment = new FragmentNhanVien();

                }else if(R.id.mQLKhachHang==id){
                    fragment = new FragmentKhachHang();

                }else if(R.id.mQLHoaDon==id){


                }else if(R.id.mGioHang==id){


                }else if(R.id.mThongKe==id){

                }

                else if(R.id.mDoiMK==id){


                } else if (R.id.mDangXuat==id) {
                    Intent intent = new Intent(MainActivity_Fragment.this,DangNhap.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fameLayout,fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolbar.setTitle(item.getTitle());
                return false;
            }
        });

    }
}