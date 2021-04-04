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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_portfolio_list_achievement, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).judulListAchievement.setText(dataItemList.get(position).getJudulKd());
        ((Penampung)holder).tempatListAchievement.setText(dataItemList.get(position).getTempat());
        ((Penampung)holder).tglListAchievement.setText(dataItemList.get(position).getTanggal());
        try{
            ImageView image = ((Penampung)holder).imgListAchievement;
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
        public TextView judulListAchievement, tempatListAchievement, tglListAchievement;
        public ImageView imgListAchievement;
        public Penampung(View itemView) {
            super(itemView);
            judulListAchievement = itemView.findViewById(R.id.judulListAchievement);
            tempatListAchievement = itemView.findViewById(R.id.tempatListAchievement);
            tglListAchievement = itemView.findViewById(R.id.tglListAchievement);
            imgListAchievement = itemView.findViewById(R.id.imgListAchievement);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulListAchievement.getText());
        }
    }
}
