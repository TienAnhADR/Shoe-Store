package com.example.appbangiayonline.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appbangiayonline.fragmentTA.FragmentSanPham;
import com.example.appbangiayonline.fragmentTA.FragmentThongTin;
import com.example.appbangiayonline.fragmentTA.Fragment_Main;

public class tab_Adapter extends FragmentStateAdapter {


    public tab_Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new Fragment_Main();
            }
            case 1: {
                return new FragmentSanPham();
            }
            case 2: {
                return new FragmentThongTin();
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
