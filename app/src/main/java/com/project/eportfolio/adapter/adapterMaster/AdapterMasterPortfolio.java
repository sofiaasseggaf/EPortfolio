package com.project.eportfolio.adapter.adapterMaster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.R;
import com.project.eportfolio.model.portfolio.TrPortofolio;

import java.util.List;

public class AdapterMasterPortfolio extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataItemList;

    public AdapterMasterPortfolio(List<TrPortofolio> dataItemList){
        this.dataItemList = dataItemList;
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
        ((Penampung)holder).namaMasterPortfolio.setText(dataItemList.get(position).getMuridid().toString());
        ((Penampung)holder).mapelMasterPortfolio.setText(dataItemList.get(position).getMapelid());
        ((Penampung)holder).strategiMasterPortfolio.setText(dataItemList.get(position).getStrategiid());
        ((Penampung)holder).predikatMasterPortfolio.setText("Predikat : " + dataItemList.get(position).getPredikat());
        ((Penampung)holder).narasiMasterPortfolio.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).tglMasterPortfolio.setText(dataItemList.get(position).getTanggal());
        ((Penampung)holder).judulMasterPortfolio.setText(dataItemList.get(position).getJudulKd());
        try {
            Bitmap bitmap= BitmapFactory.decodeFile(dataItemList.get(position).getFoto().toString());
            ((Penampung)holder).imgMasterPortfolio.setImageBitmap(bitmap);
        }  catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView namaMasterPortfolio, mapelMasterPortfolio, strategiMasterPortfolio, predikatMasterPortfolio, narasiMasterPortfolio, judulMasterPortfolio, tglMasterPortfolio;
        public ImageView imgMasterPortfolio;
        public Penampung(View itemView) {
            super(itemView);
            namaMasterPortfolio = itemView.findViewById(R.id.namaMasterPortfolio);
            mapelMasterPortfolio = itemView.findViewById(R.id.mapelMasterPortfolio);
            strategiMasterPortfolio = itemView.findViewById(R.id.strategiMasterPortfolio);
            predikatMasterPortfolio = itemView.findViewById(R.id.predikatMasterPortfolio);
            narasiMasterPortfolio = itemView.findViewById(R.id.narasiMasterPortfolio);
            judulMasterPortfolio = itemView.findViewById(R.id.judulMasterPortfolio);
            tglMasterPortfolio = itemView.findViewById(R.id.tglMasterPortfolio);
            imgMasterPortfolio = itemView.findViewById(R.id.imgMasterPortfolio);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulMasterPortfolio.getText());
        }
    }
}
