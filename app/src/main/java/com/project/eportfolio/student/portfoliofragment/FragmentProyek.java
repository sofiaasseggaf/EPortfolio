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
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListProyek;
import com.project.eportfolio.model.portfolio.TrPortofolio;

import java.util.ArrayList;
import java.util.List;

public class FragmentProyek extends Fragment {

    RecyclerView rvProject;
    AdapterListProyek itemList;
    List<TrPortofolio> listProject = new ArrayList<>();
    View v;

    public FragmentProyek() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.student_portfolio_proyek, container, false);
        rvProject = v.findViewById(R.id.rvProyek);

        if (listProject!=null){
            itemList = new AdapterListProyek(listProject);
            rvProject.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvProject.setAdapter(itemList);
        } else {
            Toast.makeText(getContext(), "Tidak Memiliki Project", Toast.LENGTH_SHORT).show();}

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
