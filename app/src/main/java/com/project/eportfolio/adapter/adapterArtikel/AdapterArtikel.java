package com.project.eportfolio.adapter.adapterArtikel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterSiswa;
import com.project.eportfolio.model.blog.Blog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterArtikel extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Blog> dataItemList;

    public AdapterArtikel(List<Blog> dataItemList) {
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_article, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).judulArtikel.setText(dataItemList.get(position).getTitle());
        ((Penampung)holder).isiArtikel.setText(dataItemList.get(position).getContent());
        try{
            ImageView image = ((Penampung)holder).fotoArtikel;
            Picasso.get().load(dataItemList.get(position).getImage()).into(image);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView judulArtikel, isiArtikel;
        public ImageView fotoArtikel;
        public Penampung(View itemView) {
            super(itemView);
            judulArtikel = itemView.findViewById(R.id.judulArtikel);
            fotoArtikel = itemView.findViewById(R.id.fotoArtikel);
            isiArtikel = itemView.findViewById(R.id.isiArtikel);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judulArtikel.getText());
        }
    }
}
