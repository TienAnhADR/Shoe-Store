package com.example.appbangiayonline.fragmentTA;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbangiayonline.R;
import com.example.appbangiayonline.adapter.HoaDonAdapter;
import com.example.appbangiayonline.dao.HoaDonDao;
import com.example.appbangiayonline.model.HoaDon;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentThongTin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThongTin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentThongTin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentThongTin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentThongTin newInstance(String param1, String param2) {
        FragmentThongTin fragment = new FragmentThongTin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    RecyclerView recyclerView;
    HoaDonAdapter adapter;
    HoaDonDao dao;
    ArrayList<HoaDon> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin, container, false);
        recyclerView = view.findViewById(R.id.rchoadon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        list = new ArrayList<>();
        dao = new HoaDonDao(getContext());
        list = dao.getDSHoaDon();
        adapter = new HoaDonAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        return view ;
    }
}