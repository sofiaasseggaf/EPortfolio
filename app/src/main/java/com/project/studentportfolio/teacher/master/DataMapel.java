package com.project.studentportfolio.teacher.master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.studentportfolio.APIService.APIClient;
import com.project.studentportfolio.APIService.APIInterfacesRest;
import com.project.studentportfolio.R;
import com.project.studentportfolio.adapter.adapterMaster.AdapterMasterMapel;
import com.project.studentportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.studentportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.studentportfolio.teacher.MasterTeacher;
import com.project.studentportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMapel extends AppCompatActivity {

    RecyclerView rvDataMapel;
    AdapterMasterMapel itemList;
    ModelMataPelajaran modelDataMapel;
    List<MsMatapelajaran> listMapel = new ArrayList<>();

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_master_datamapel);

        rvDataMapel = findViewById(R.id.rvDataMapel);
        first();

    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getMapel();
            }
        }).start();
    }

    public void getMapel() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelMataPelajaran> dataSiswax = apiInterface.getDataMasterMapel(  apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelMataPelajaran>() {
            @Override
            public void onResponse(Call<ModelMataPelajaran> call, Response<ModelMataPelajaran> response) {

                modelDataMapel = response.body();

                if (modelDataMapel!=null){
                    for (int i = 0; i < modelDataMapel.getTotal(); i++) {
                        try {
                            if (PreferenceUtils.getIdGuru(getApplicationContext())
                                    .equalsIgnoreCase(modelDataMapel.getData().getMsMatapelajaran().get(i).getGuruid())) {
                                listMapel.add(modelDataMapel.getData().getMsMatapelajaran().get(i));
                            }
                        } catch (Exception e){

                        }
                    }

                    if (listMapel!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterMasterMapel(listMapel);
                                rvDataMapel.setLayoutManager(new LinearLayoutManager(DataMapel.this));
                                rvDataMapel.setAdapter(itemList);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelMataPelajaran> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DataMapel.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }


    public void onBackPressed() {
        Intent a = new Intent(DataMapel.this, MasterTeacher.class);
        startActivity(a);
        finish();
    }
}
