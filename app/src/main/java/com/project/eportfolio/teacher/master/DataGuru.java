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
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterGuru;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterSiswa;
import com.project.eportfolio.model.grade.ModelGrade;
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataGuru extends AppCompatActivity {

    RecyclerView rvDataGuru;
    AdapterMasterGuru itemList;
    ModelGuru modelDataGuru;
    List<MsGuru> listguru = new ArrayList<>();

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_master_dataguru);

        rvDataGuru = findViewById(R.id.rvDataGuru);
        first();
    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getGuru();
            }
        }).start();
    }

    public void getGuru() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelGuru> dataSiswax = apiInterface.getdataGuru( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelGuru>() {
            @Override
            public void onResponse(Call<ModelGuru> call, Response<ModelGuru> response) {
                modelDataGuru = response.body();

                if (modelDataGuru!=null){
                    for (int i = 0; i < modelDataGuru.getData().getMsGuru().size(); i++) {
                        if (PreferenceUtils.getIdSekolahGuru(getApplicationContext())
                                .equalsIgnoreCase(modelDataGuru.getData().getMsGuru().get(i).getSekolahid())) {
                            listguru.add(modelDataGuru.getData().getMsGuru().get(i));
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            itemList = new AdapterMasterGuru(listguru);
                            rvDataGuru.setLayoutManager(new LinearLayoutManager(DataGuru.this));
                            rvDataGuru.setAdapter(itemList);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelGuru> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DataGuru.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void onBackPressed() {
        Intent a = new Intent(DataGuru.this, MasterTeacher.class);
        startActivity(a);
        finish();
    }
}