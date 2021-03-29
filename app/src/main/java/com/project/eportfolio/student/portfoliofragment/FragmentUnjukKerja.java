package com.project.eportfolio.student.portfoliofragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListUnjukKerja;
import com.project.eportfolio.model.portfolio.TrPortofolio;

import java.util.ArrayList;
import java.util.List;

public class FragmentUnjukKerja extends Fragment {

    RecyclerView rvunjukkerja;
    AdapterListUnjukKerja itemList;
    List<TrPortofolio> listUnjukKerja = new ArrayList<>();
    View v;

    public FragmentUnjukKerja() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.student_portfolio_unjuk_kerja, container, false);
        rvunjukkerja = v.findViewById(R.id.rvUnjukKerja);

        if (listUnjukKerja!=null){
            itemList = new AdapterListUnjukKerja(listUnjukKerja);
            rvunjukkerja.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvunjukkerja.setAdapter(itemList);
        } else {
                Toast.makeText(getContext(), "Tidak Memiliki Unjuk Kerja", Toast.LENGTH_SHORT).show();}

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
