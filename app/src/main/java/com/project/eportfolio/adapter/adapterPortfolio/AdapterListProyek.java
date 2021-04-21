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
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListProyek extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataItemList;
    private List<MsMatapelajaran> dataListMapel;

    public AdapterListProyek(List<TrPortofolio> dataItemList, List<MsMatapelajaran> dataListMapel){
        this.dataItemList = dataItemList;
        this.dataListMapel = dataListMapel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_portfolio_list_proyek, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        for (int i=0; i<dataListMapel.size(); i++){
            try {
                if (dataItemList.get(position).getMapelid().equalsIgnoreCase(dataListMapel.get(i).getId())){
                    ((Penampung)holder).mapelListProyek.setText(dataListMapel.get(i).getName());
                    break;
                }
                else {
                    ((Penampung)holder).mapelListProyek.setText("ID Mata Pelajaran : "+dataItemList.get(position).getMapelid());
                }
            } catch (Exception a){

            }

        }
        //((Penampung)holder).kelasListProyek.setText(dataItemList.get(position).getKelas());
        //((Penampung)holder).semesterListProyek.setText("SEMESTER"+dataItemList.get(position).getSemester());
        ((Penampung)holder).predikatListproyek.setText(dataItemList.get(position).getPredikat());
        //((Penampung)holder).narasiListProyek.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).tglListProyek.setText(dataItemList.get(position).getTanggal());
        ((Penampung)holder).judulListProyek.setText(dataItemList.get(position).getJudulKd());
        try{
            ImageView image = ((Penampung)holder).imgListProyek;
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
        public TextView mapelListProyek, predikatListproyek, judulListProyek, tglListProyek;
        public ImageView imgListProyek;
        public Penampung(View itemView) {
            super(itemView);
            mapelListProyek = itemView.findViewById(R.id.mapelListProyek);
            //kelasListProyek = itemView.findViewById(R.id.kelasListProyek);
            //semesterListProyek = itemView.findViewById(R.id.semesterListProyek);
            predikatListproyek = itemView.findViewById(R.id.predikatListProyek);
            //narasiListProyek = itemView.findViewById(R.id.narasiListProyek);
            judulListProyek = itemView.findViewById(R.id.judulListProyek);
            tglListProyek = itemView.findViewById(R.id.tglListProyek);
            imgListProyek = itemView.findViewById(R.id.imgListProyek);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulListProyek.getText());
        }
    }
}

