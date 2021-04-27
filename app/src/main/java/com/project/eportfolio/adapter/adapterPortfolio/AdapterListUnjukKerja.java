package com.project.eportfolio.adapter.adapterPortfolio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.R;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.student.portfolio.PortfolioDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterListUnjukKerja extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataItemList;
    private List<MsMatapelajaran> dataListMapel;

    public AdapterListUnjukKerja(List<TrPortofolio> dataItemList,
                                 List<MsMatapelajaran> dataListMapel){
        this.dataItemList = dataItemList;
        this.dataListMapel = dataListMapel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_portfolio_list_unjuk_kerja, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        for (int i=0; i<dataListMapel.size(); i++){
            try {
                if (dataItemList.get(position).getMapelid().equalsIgnoreCase(dataListMapel.get(i).getId())){
                    ((Penampung)holder).mapelListUnjukKerja.setText(dataListMapel.get(i).getName());
                    break;
                }
                else {
                    ((Penampung)holder).mapelListUnjukKerja.setText("ID Mata Pelajaran : "+dataItemList.get(position).getMapelid());
                }
            } catch (Exception a){

            }

        }
        //((Penampung)holder).kelasListUnjukKerja.setText(dataItemList.get(position).getKelas());
        //((Penampung)holder).semesterListUnjukKerja.setText("SEMESTER"+dataItemList.get(position).getSemester());
        ((Penampung)holder).predikatListUnjukKerja.setText(dataItemList.get(position).getPredikat());
        //((Penampung)holder).narasiListUnjukKerja.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).judulListUnjukKerja.setText(dataItemList.get(position).getJudulKd());
        ((Penampung)holder).tglListUnjukKerja.setText(dataItemList.get(position).getTanggal());
        try{
            ImageView image = ((Penampung)holder).imgListUnjukKerja;
            Picasso.get().load(dataItemList.get(position).getFoto().toString()).into(image);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    public class Penampung extends RecyclerView.ViewHolder {
        public TextView mapelListUnjukKerja, predikatListUnjukKerja, judulListUnjukKerja, tglListUnjukKerja;
        public ImageView imgListUnjukKerja;

        public Penampung(View itemView) {
            super(itemView);
            mapelListUnjukKerja = itemView.findViewById(R.id.mapelListUnjukKerja);
            predikatListUnjukKerja = itemView.findViewById(R.id.predikatListUnjukKerja);
            //kelasListUnjukKerja = itemView.findViewById(R.id.kelasListUnjukKerja);
            //semesterListUnjukKerja = itemView.findViewById(R.id.semesterListUnjukKerja);
            //narasiListUnjukKerja = itemView.findViewById(R.id.narasiListUnjukKerja);
            judulListUnjukKerja = itemView.findViewById(R.id.judulListUnjukKerja);
            tglListUnjukKerja = itemView.findViewById(R.id.tglListUnjukKerja);
            imgListUnjukKerja = itemView.findViewById(R.id.imgListUnjukKerja);

        }

    }
   }
