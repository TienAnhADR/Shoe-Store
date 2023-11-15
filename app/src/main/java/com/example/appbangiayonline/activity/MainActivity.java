package com.example.appbangiayonline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.tab.Shoes_tab;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //menu bottom
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = new Shoes_tab();
            } else if (item.getItemId() == R.id.shoes) {
                selectedFragment = new Shoes_tab();
            }
            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
                return true;
            } else {
                return false;
            }
        });
        // Đặt fragment mặc định được hiển thị khi activity bắt đầu
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
    //menu bottom
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}