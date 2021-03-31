package com.project.eportfolio.student.portfoliofragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.project.eportfolio.databases.PortfolioDatabase;
import com.project.eportfolio.databases.PortfolioFetchListener;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.utility.Constants;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;

import java.util.ArrayList;
import java.util.List;

public class FragmentUnjukKerja extends Fragment implements AdapterListUnjukKerja.ClickLIstenerUnjukKerja, PortfolioFetchListener {

    RecyclerView rvunjukkerja;
    AdapterListUnjukKerja itemList;
    PortfolioDatabase mDatabase;
    List<TrPortofolio> listPortfolio = new ArrayList<>();
    Fetch fetch;
    View v;

    public FragmentUnjukKerja() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.student_portfolio_unjuk_kerja, container, false);
        rvunjukkerja = v.findViewById(R.id.rvUnjukKerja);

        //itemList.reset();

        if (listPortfolio!=null){

           /* for(int i=0; i<listPortfolio.size(); i++){
                try {
                    if (listPortfolio.get(i).getIdkategori().equalsIgnoreCase("1")){
                        listUnjukKerja.add(listPortofolio.get(i));
                    }
                }catch (Exception e){ }
            }*/

            itemList = new AdapterListUnjukKerja(this, listPortfolio);
            rvunjukkerja.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvunjukkerja.setAdapter(itemList);

            //mDatabase.fetchPortfolio(this);

        } else {
            Toast.makeText(getContext(), "Tidak Memiliki Unjuk Kerja", Toast.LENGTH_SHORT).show();
        }



        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(getContext())
                .setDownloadConcurrentLimit(3)
                .build();
        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onClick(int position) {
        TrPortofolio selectedPortfolio = itemList.getSelectedUnjukKerja(position);
        Intent intent = new Intent(getActivity(), PortfolioDetail.class);
        intent.putExtra(Constants.REFERENCE.PORTFOLIO, (Parcelable) selectedPortfolio);
        startActivity(intent);
    }

    @Override
    public void onDeliverAllPortfolio(List<TrPortofolio> portofolio) {

    }

    @Override
    public void onDeliverPortfolio(TrPortofolio portofolio) {
        itemList.addPortfolio(portofolio);
    }

    @Override
    public void onHideDialog() {

    }
}
