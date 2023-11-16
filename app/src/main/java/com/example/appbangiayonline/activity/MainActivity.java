package com.example.appbangiayonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TableLayout;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.fragmentTA.FragmentKhachHang;
import com.example.appbangiayonline.fragmentTA.FragmentNhanVien;
import com.example.appbangiayonline.tab.tab_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        NavigationView navigationView = findViewById(R.id.navigationV);
        drawerLayout = findViewById(R.id.drawerLayout);
        // TextView txtUserName = findViewById(R.id.txtUserNameNVGTON);
        setSupportActionBar(toolbar);
        //  setSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager2 pager2 = findViewById(R.id.pager2);

        tab_Adapter tab_adapter = new tab_Adapter(getSupportFragmentManager(), getLifecycle());
        pager2.setAdapter(tab_adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

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


                } else if (R.id.mGioHang == id) {


                } else if (R.id.mThongKe == id) {

                } else if (R.id.mDoiMK == id) {


                } else if (R.id.mDangXuat == id) {
                    Intent intent = new Intent(MainActivity.this, DangNhap.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.pager2, fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolbar.setTitle(item.getTitle());
                return false;
            }
        });
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