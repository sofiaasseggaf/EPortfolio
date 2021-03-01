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
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterSiswa;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.R;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMurid extends AppCompatActivity {

    RecyclerView rvDataMurid;
    AdapterMasterSiswa itemList;
    ModelSiswa modelDataSiswa;
    List<MsMurid> listsiswa= new ArrayList<>();
    TextView txtload;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_master_datamurid);
        rvDataMurid = findViewById(R.id.rvDataMurid);
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
                    txtload.setText("Loading Data Murid .");
                }
                else if (count == 2)
                {
                    txtload.setText("Loading Data Murid . .");
                }
                else if (count == 3)
                {
                    txtload.setText("Loading Data Murid . . .");
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
                getSiswa();
            }
        }).start();
    }

    public void getSiswa() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSiswa> dataSiswax = apiInterface.getDataSiswa( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelSiswa>() {
            @Override
            public void onResponse(Call<ModelSiswa> call, Response<ModelSiswa> response) {
                modelDataSiswa = response.body();
                if (modelDataSiswa!=null){
                    for (int i = 0; i < modelDataSiswa.getTotal(); i++) {
                        try {
                            if (PreferenceUtils.getIdSekolahGuru(getApplicationContext())
                                    .equalsIgnoreCase(modelDataSiswa.getData().getMsMurid().get(i).getSekolahid())) {
                                listsiswa.add(modelDataSiswa.getData().getMsMurid().get(i));
                            }
                        } catch (Exception e){
                        }
                    }

                    if (listsiswa!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterMasterSiswa(listsiswa);
                                rvDataMurid.setLayoutManager(new LinearLayoutManager(DataMurid.this));
                                rvDataMurid.setAdapter(itemList);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelSiswa> call, Throwable t) {

                Toast.makeText(DataMurid.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void onBackPressed() {
        Intent a = new Intent(DataMurid.this, MasterTeacher.class);
        startActivity(a);
        finish();
    }
}
