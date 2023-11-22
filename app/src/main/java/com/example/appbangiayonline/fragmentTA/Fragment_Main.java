package com.example.appbangiayonline.fragmentTA;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.SliderAdapter;

public class Fragment_Main extends Fragment {
    ViewPager viewPager;
    SliderAdapter adapter;
    int index = 0;
    //----------------
    int[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4, R.drawable.slider5};

    //'''''''''''''''''

    int sum_time = 3000;
    int each_time = 1000;

    public Fragment_Main() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__main, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        adapter = new SliderAdapter(getContext(), images);
        viewPager.setAdapter(adapter);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index == images.length) {
                    index = 0;
                }
                viewPager.setCurrentItem(index++, true);
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        return view;
    }
}