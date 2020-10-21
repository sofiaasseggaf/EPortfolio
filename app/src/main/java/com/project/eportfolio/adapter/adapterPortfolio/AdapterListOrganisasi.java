package com.project.eportfolio.adapter.adapterPortfolio;

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
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListOrganisasi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataItemList;

    public AdapterListOrganisasi(List<TrPortofolio> dataItemList){
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_portfolio_list_organisasi, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).judulListOrganisasi.setText(dataItemList.get(position).getJudulKd());
        ((Penampung)holder).narasiListOrganisasi.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).tglListOrganisasi.setText(dataItemList.get(position).getTanggal());
        try{
            ImageView image = ((Penampung)holder).imgListOrganisasi;
            Picasso.get().load("https://eportofolio.id/uploads/tr_portofolio/"+dataItemList.get(position).getFoto().toString()).into(image);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView judulListOrganisasi, narasiListOrganisasi, tglListOrganisasi;
        public ImageView imgListOrganisasi;
        public Penampung(View itemView) {
            super(itemView);
            judulListOrganisasi = itemView.findViewById(R.id.judulListOrganisasi);
            narasiListOrganisasi = itemView.findViewById(R.id.narasiListOrganisasi);
            tglListOrganisasi = itemView.findViewById(R.id.tglListOrganisasi);
            imgListOrganisasi = itemView.findViewById(R.id.imgListOrganisasi);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulListOrganisasi.getText());
        }
    }
}
