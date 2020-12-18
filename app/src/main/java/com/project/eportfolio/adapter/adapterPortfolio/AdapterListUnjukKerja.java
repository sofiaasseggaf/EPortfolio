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
        ((Penampung)holder).kelasListUnjukKerja.setText(dataItemList.get(position).getKelas());
        ((Penampung)holder).semesterListUnjukKerja.setText("SEMESTER"+dataItemList.get(position).getSemester());
        ((Penampung)holder).predikatListUnjukKerja.setText(dataItemList.get(position).getPredikat());
        ((Penampung)holder).narasiListUnjukKerja.setText(dataItemList.get(position).getNarasi());
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

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mapelListUnjukKerja, kelasListUnjukKerja, semesterListUnjukKerja,
                predikatListUnjukKerja, narasiListUnjukKerja, judulListUnjukKerja, tglListUnjukKerja;
        public ImageView imgListUnjukKerja;
        public Penampung(View itemView) {
            super(itemView);
            mapelListUnjukKerja = itemView.findViewById(R.id.mapelListUnjukKerja);
            predikatListUnjukKerja = itemView.findViewById(R.id.predikatListUnjukKerja);
            kelasListUnjukKerja = itemView.findViewById(R.id.kelasListUnjukKerja);
            semesterListUnjukKerja = itemView.findViewById(R.id.semesterListUnjukKerja);
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
