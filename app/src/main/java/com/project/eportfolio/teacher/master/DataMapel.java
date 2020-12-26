package com.project.eportfolio.teacher.master;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterMapel;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.utility.PreferenceUtils;

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
    TextView txtload;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_master_datamapel);

        rvDataMapel = findViewById(R.id.rvDataMapel);
        txtload = findViewById(R.id.textloading);
        first();

    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            int count = 0;

            @Override
            public void run() {
                count++;

                if (count == 1)
                {
                    txtload.setText("Loading Data Mata Pelajaran .");
                }
                else if (count == 2)
                {
                    txtload.setText("Loading Data Mata Pelajaran . .");
                }
                else if (count == 3)
                {
                    txtload.setText("Loading Data Mata Pelajaran . . .");
                }

                if (count == 3)
                    count = 0;

                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
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
