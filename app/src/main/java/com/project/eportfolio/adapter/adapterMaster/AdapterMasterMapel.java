package com.project.eportfolio.adapter.adapterMaster;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.R;
import com.project.eportfolio.model.kelas.MsKela;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;

import java.util.List;

public class AdapterMasterMapel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MsMatapelajaran> dataItemList;

    public AdapterMasterMapel(List<MsMatapelajaran> dataItemList){
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_master_datamapel, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).namaList.setText(dataItemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView namaList;
        public Penampung(View itemView) {
            super(itemView);
            namaList = itemView.findViewById(R.id.namaMasterMapel);
            }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + namaList.getText());
        }
    }
}
