package com.project.studentportfolio.adapter.adapterMaster;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.studentportfolio.R;
import com.project.studentportfolio.model.guru.MsGuru;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMasterGuru extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MsGuru> dataItemList;

    public AdapterMasterGuru(List<MsGuru> dataItemList){
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_master_dataguru, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).namaList.setText(dataItemList.get(position).getFirstname()+" "+dataItemList.get(position).getLastname());
        try{
            ImageView image = ((Penampung)holder).imgList;
            Picasso.get().load("https://eportofolio.id/uploads/ms_guru/"+dataItemList.get(position).getPhoto()).into(image);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView namaList;
        public ImageView imgList;
        public Penampung(View itemView) {
            super(itemView);
            namaList = itemView.findViewById(R.id.namaMasterGuru);
            imgList = itemView.findViewById(R.id.imgMasterGuru);
            }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + namaList.getText());
        }
    }
}
