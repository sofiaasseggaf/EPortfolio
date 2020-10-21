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
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterPortfolio;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterSiswa;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPortfolio extends AppCompatActivity {

    RecyclerView rvDataPortfolio;
    AdapterMasterPortfolio itemList;
    ModelPortofolio modelDataPortfolio;
    List<TrPortofolio> listPortfolio = new ArrayList<>();

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_master_dataportfolio);

        rvDataPortfolio = findViewById(R.id.rvDataPortfolio);
        first();

    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getPortfolio();
            }
        }).start();
    }


    public void getPortfolio() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPortofolio> dataSiswax = apiInterface.getDataPortfolio(  apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelPortofolio>() {
            @Override
            public void onResponse(Call<ModelPortofolio> call, Response<ModelPortofolio> response) {
                modelDataPortfolio = response.body();
                if (modelDataPortfolio!=null){
                    for (int i = 0; i < modelDataPortfolio.getData().getTrPortofolio().size(); i++) {
                        if (PreferenceUtils.getIdGuru(getApplicationContext())
                                .equalsIgnoreCase(modelDataPortfolio.getData().getTrPortofolio().get(i).getGuruid())) {
                            listPortfolio.add(modelDataPortfolio.getData().getTrPortofolio().get(i));
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            itemList = new AdapterMasterPortfolio(listPortfolio);
                            rvDataPortfolio.setLayoutManager(new LinearLayoutManager(DataPortfolio.this));
                            rvDataPortfolio.setAdapter(itemList);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelPortofolio> call, Throwable t) {

                Toast.makeText(DataPortfolio.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void onBackPressed() {
        Intent a = new Intent(DataPortfolio.this, MasterTeacher.class);
        startActivity(a);
        finish();
    }
}