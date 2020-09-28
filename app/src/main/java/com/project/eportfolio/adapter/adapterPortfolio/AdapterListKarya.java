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

public class AdapterListKarya extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataItemList;

    public AdapterListKarya(List<TrPortofolio> dataItemList){
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_portfolio_list_karya, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).mapelListKarya.setText(dataItemList.get(position).getMapelid());
        ((Penampung)holder).predikatListKarya.setText("Predikat : " + dataItemList.get(position).getPredikat());
        ((Penampung)holder).narasiListKarya.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).tglListKarya.setText(dataItemList.get(position).getTanggal());
        ((Penampung)holder).judulListKarya.setText(dataItemList.get(position).getJudulKd());
        try {
            Bitmap bitmap= BitmapFactory.decodeFile(dataItemList.get(position).getFoto().toString());
            ((Penampung)holder).imgListKarya.setImageBitmap(bitmap);
        }  catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mapelListKarya, predikatListKarya, narasiListKarya, judulListKarya, tglListKarya;
        public ImageView imgListKarya;
        public Penampung(View itemView) {
            super(itemView);
            mapelListKarya = itemView.findViewById(R.id.mapelListKarya);
            predikatListKarya = itemView.findViewById(R.id.predikatListKarya);
            narasiListKarya = itemView.findViewById(R.id.narasiListKarya);
            judulListKarya = itemView.findViewById(R.id.judulListKarya);
            tglListKarya = itemView.findViewById(R.id.tglListKarya);
            imgListKarya = itemView.findViewById(R.id.imgListKarya);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulListKarya.getText());
        }
    }
}
