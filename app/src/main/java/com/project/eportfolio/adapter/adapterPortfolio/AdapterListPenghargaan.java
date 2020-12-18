package com.project.eportfolio.adapter.adapterPortfolio;

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
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListPenghargaan extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataItemList;

    public AdapterListPenghargaan(List<TrPortofolio> dataItemList){
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_portfolio_list_penghargaan, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).judulListPenghargaan.setText(dataItemList.get(position).getJudulKd());
        ((Penampung)holder).narasiListPenghargaan.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).tglListPenghargaan.setText(dataItemList.get(position).getTanggal());
        try{
            ImageView image = ((Penampung)holder).imgListPenghargaan;
            Picasso.get().load(dataItemList.get(position).getFoto().toString()).into(image);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView judulListPenghargaan, narasiListPenghargaan, tglListPenghargaan;
        public ImageView imgListPenghargaan;
        public Penampung(View itemView) {
            super(itemView);
            judulListPenghargaan = itemView.findViewById(R.id.judulListPenghargaan);
            narasiListPenghargaan = itemView.findViewById(R.id.narasiListPenghargaan);
            tglListPenghargaan = itemView.findViewById(R.id.tglListPenghargaan);
            imgListPenghargaan = itemView.findViewById(R.id.imgListPenghargaan);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulListPenghargaan.getText());
        }
    }
}
