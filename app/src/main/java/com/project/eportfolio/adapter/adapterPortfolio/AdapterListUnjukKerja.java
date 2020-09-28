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

import java.util.List;

public class AdapterListUnjukKerja extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataItemList;

    public AdapterListUnjukKerja(List<TrPortofolio> dataItemList){
        this.dataItemList = dataItemList;
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
        ((Penampung)holder).mapelListUnjukKerja.setText(dataItemList.get(position).getMapelid());
        ((Penampung)holder).predikatListUnjukKerja.setText(dataItemList.get(position).getPredikat());
        ((Penampung)holder).narasiListUnjukKerja.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).judulListUnjukKerja.setText(dataItemList.get(position).getJudulKd());
        ((Penampung)holder).tglListUnjukKerja.setText(dataItemList.get(position).getTanggal());
        try {
            Bitmap bitmap= BitmapFactory.decodeFile(dataItemList.get(position).getFoto().toString());
            ((Penampung)holder).imgListUnjukKerja.setImageBitmap(bitmap);
        }  catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mapelListUnjukKerja, predikatListUnjukKerja, narasiListUnjukKerja, judulListUnjukKerja, tglListUnjukKerja;
        public ImageView imgListUnjukKerja;
        public Penampung(View itemView) {
            super(itemView);
            mapelListUnjukKerja = itemView.findViewById(R.id.mapelListUnjukKerja);
            predikatListUnjukKerja = itemView.findViewById(R.id.predikatListUnjukKerja);
            narasiListUnjukKerja = itemView.findViewById(R.id.narasiListUnjukKerja);
            judulListUnjukKerja = itemView.findViewById(R.id.judulListUnjukKerja);
            tglListUnjukKerja = itemView.findViewById(R.id.tglListUnjukKerja);
            imgListUnjukKerja = itemView.findViewById(R.id.imgListUnjukKerja);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulListUnjukKerja.getText());
        }
    }
}
