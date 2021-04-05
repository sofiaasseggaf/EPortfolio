package com.project.eportfolio.adapter.adapterMaster;

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
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMasterPortfolioDuaModel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataListTrPortfolio;
    private List<MsMurid> dataListMsMurid;
    private List<MsMatapelajaran> dataListMapel;
    private List<MsStrategi> dataListStrategi;

    public AdapterMasterPortfolioDuaModel(List<TrPortofolio> dataListTrPortfolio, List<MsMurid> dataListMsMurid,
                                          List<MsMatapelajaran> dataListMapel, List<MsStrategi> dataListStrategi){
        this.dataListTrPortfolio = dataListTrPortfolio;
        this.dataListMsMurid = dataListMsMurid;
        this.dataListMapel = dataListMapel;
        this.dataListStrategi = dataListStrategi;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_master_dataportfolio, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        for (int i=0; i<dataListMsMurid.size(); i++){
            try{
                if (dataListTrPortfolio.get(position).getMuridid().toString().equalsIgnoreCase(dataListMsMurid.get(i).getId())){
                    ((Penampung)holder).namaMasterPortfolio.setText("Oleh " + dataListMsMurid.get(i).getFirstname()+" "+dataListMsMurid.get(i).getLastname());
                    break;
                }
                else {
                    ((Penampung)holder).namaMasterPortfolio.setText("ID Siswa : "+dataListTrPortfolio.get(position).getMuridid().toString());
                }
            } catch (Exception a){ }
        }


        for (int i=0; i<dataListMapel.size(); i++){
            try {
                if (dataListTrPortfolio.get(position).getMapelid().equalsIgnoreCase(dataListMapel.get(i).getId())){
                    ((Penampung)holder).mapelMasterPortfolio.setText(dataListMapel.get(i).getName());
                    break;
                }
                else {
                    ((Penampung)holder).mapelMasterPortfolio.setText("ID Mata Pelajaran : "+dataListTrPortfolio.get(position).getMapelid().toString());
                }
            } catch (Exception a){ }

        }

/*
        for (int i=0; i<dataListStrategi.size(); i++){
            try{
                if (dataListTrPortfolio.get(position).getStrategiid().equalsIgnoreCase(dataListStrategi.get(i).getIdStrategi())){
                    ((Penampung)holder).strategiMasterPortfolio.setText(dataListStrategi.get(i).getNameStrategi());
                }
            } catch (Exception a){

            }

        }
        */

        ((Penampung)holder).predikatMasterPortfolio.setText(dataListTrPortfolio.get(position).getPredikatMutu());
        //((Penampung)holder).narasiMasterPortfolio.setText(dataListTrPortfolio.get(position).getNarasi());
        //((Penampung)holder).tglMasterPortfolio.setText(dataListTrPortfolio.get(position).getTanggal());
        ((Penampung)holder).judulMasterPortfolio.setText(dataListTrPortfolio.get(position).getJudulKd());

        try{
            ImageView image = ((Penampung)holder).imgMasterPortfolio;
            Picasso.get().load(dataListTrPortfolio.get(position).getFoto().toString()).into(image);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataListTrPortfolio == null ? 0 : dataListTrPortfolio.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView namaMasterPortfolio, mapelMasterPortfolio, strategiMasterPortfolio, predikatMasterPortfolio, narasiMasterPortfolio, judulMasterPortfolio, tglMasterPortfolio;
        public ImageView imgMasterPortfolio;
        public Penampung(View itemView) {
            super(itemView);
            namaMasterPortfolio = itemView.findViewById(R.id.namaMasterPortfolio);
            mapelMasterPortfolio = itemView.findViewById(R.id.mapelMasterPortfolio);
            //strategiMasterPortfolio = itemView.findViewById(R.id.strategiMasterPortfolio);
            predikatMasterPortfolio = itemView.findViewById(R.id.predikatMasterPortfolio);
            //narasiMasterPortfolio = itemView.findViewById(R.id.narasiMasterPortfolio);
            judulMasterPortfolio = itemView.findViewById(R.id.judulMasterPortfolio);
            //tglMasterPortfolio = itemView.findViewById(R.id.tglMasterPortfolio);
            imgMasterPortfolio = itemView.findViewById(R.id.imgMasterPortfolio);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulMasterPortfolio.getText());
        }
    }
}
