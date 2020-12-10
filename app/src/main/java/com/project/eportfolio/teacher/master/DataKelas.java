package com.project.eportfolio.teacher.master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterKelas;
import com.project.eportfolio.model.kelas.ModelKelas;
import com.project.eportfolio.model.kelas.MsKela;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelas extends AppCompatActivity {

    RecyclerView rvDataKelas;
    AdapterMasterKelas itemList;
    ModelKelas modelDataKelas;
    List<MsKela> listkelas = new ArrayList<>();

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_master_datakelas);

        rvDataKelas = findViewById(R.id.rvDataKelas);
        first();

    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getKelas();
            }
        }).start();
    }

    public void getKelas() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKelas> dataSiswax = apiInterface.getdataKelas(  apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelKelas>() {
            @Override
            public void onResponse(Call<ModelKelas> call, Response<ModelKelas> response) {

                modelDataKelas = response.body();

                if (modelDataKelas!=null){
                    for (int i = 0; i < modelDataKelas.getTotal(); i++) {
                        try {
                            if (PreferenceUtils.getIdSekolahGuru(getApplicationContext())
                                    .equalsIgnoreCase(modelDataKelas.getData().getMsKelas().get(i).getSekolahid())) {
                                listkelas.add(modelDataKelas.getData().getMsKelas().get(i));
                            }
                        } catch (Exception e){

                        }
                    }

                    if (listkelas!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterMasterKelas(listkelas);
                                rvDataKelas.setLayoutManager(new LinearLayoutManager(DataKelas.this));
                                rvDataKelas.setAdapter(itemList);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelKelas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DataKelas.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }


    public void onBackPressed() {
        Intent a = new Intent(DataKelas.this, MasterTeacher.class);
        startActivity(a);
        finish();
    }
}
