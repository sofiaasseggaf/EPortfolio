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
        //((Penampung)holder).kelasListKarya.setText(dataItemList.get(position).getKelas());
        //((Penampung)holder).semesterListKarya.setText("SEMESTER"+dataItemList.get(position).getSemester());
        ((Penampung)holder).predikatListKarya.setText(dataItemList.get(position).getPredikat());
        //((Penampung)holder).narasiListKarya.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).tglListKarya.setText(dataItemList.get(position).getTanggal());
        ((Penampung)holder).judulListKarya.setText(dataItemList.get(position).getJudulKd());
        try{
            ImageView image = ((Penampung)holder).imgListKarya;
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
        public TextView mapelListKarya, predikatListKarya, judulListKarya, tglListKarya;
        public ImageView imgListKarya;
        public Penampung(View itemView) {
            super(itemView);
            mapelListKarya = itemView.findViewById(R.id.mapelListKarya);
            //kelasListKarya = itemView.findViewById(R.id.kelasListKarya);
            //semesterListKarya = itemView.findViewById(R.id.semesterListKarya);
            predikatListKarya = itemView.findViewById(R.id.predikatListKarya);
            //narasiListKarya = itemView.findViewById(R.id.narasiListKarya);
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
