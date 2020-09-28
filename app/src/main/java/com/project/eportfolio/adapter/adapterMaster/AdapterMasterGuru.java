package com.project.eportfolio.adapter.adapterMaster;

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
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListForumEdukasi;
import com.project.eportfolio.model.grade.MsGrade;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.model.siswa.MsMurid;
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
        ImageView imgList = ((Penampung)holder).imgList;
       /* try {
            Bitmap bitmap= BitmapFactory.decodeFile(dataItemList.get(position).getPhoto().toString());
            ((Penampung)holder).imgList.setImageBitmap(bitmap);
        }  catch (Exception e){
        }*/
        Picasso.get().load(dataItemList.get(position).getPhoto()).into(imgList);
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
