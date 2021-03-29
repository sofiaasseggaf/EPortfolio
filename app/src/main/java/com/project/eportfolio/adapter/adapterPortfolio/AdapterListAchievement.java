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

public class AdapterListAchievement extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrPortofolio> dataItemList;

    public AdapterListAchievement(List<TrPortofolio> dataItemList){
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_portfolio_list_forum_edukasi, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).judulListForumEdukasi.setText(dataItemList.get(position).getJudulKd());
        ((Penampung)holder).narasiListForumEdukasi.setText(dataItemList.get(position).getNarasi());
        ((Penampung)holder).tglListForumEdukasi.setText(dataItemList.get(position).getTanggal());
        try{
            ImageView image = ((Penampung)holder).imgListForumEdukasi;
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
        public TextView judulListForumEdukasi, narasiListForumEdukasi, tglListForumEdukasi;
        public ImageView imgListForumEdukasi;
        public Penampung(View itemView) {
            super(itemView);
            judulListForumEdukasi = itemView.findViewById(R.id.judulListForumEdukasi);
            narasiListForumEdukasi = itemView.findViewById(R.id.narasiListForumEdukasi);
            tglListForumEdukasi = itemView.findViewById(R.id.tglListForumEdukasi);
            imgListForumEdukasi = itemView.findViewById(R.id.imgListForumEdukasi);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulListForumEdukasi.getText());
        }
    }
}
