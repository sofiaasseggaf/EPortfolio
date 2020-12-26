package com.project.eportfolio.student.portfolio;

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
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListOrganisasi;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.student.PortfolioStudent;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Organisasi extends AppCompatActivity {

    RecyclerView rvOrganisasi;
    ModelPortofolio dataModelPortfolio;
    List<TrPortofolio> listPortofolio = new ArrayList<>();
    List<TrPortofolio> listOrganisasiMurid = new ArrayList<>();
    AdapterListOrganisasi itemList;
    TextView txtload;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_portfolio_organisasi);

        rvOrganisasi = findViewById(R.id.rvOrganisasi);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            int count = 0;

            @Override
            public void run() {
                count++;

                if (count == 1)
                {
                    txtload.setText("Loading Portfolio .");
                }
                else if (count == 2)
                {
                    txtload.setText("Loading Portfolio . .");
                }
                else if (count == 3)
                {
                    txtload.setText("Loading Portfolio . . .");
                }

                if (count == 3)
                    count = 0;

                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        txtload = findViewById(R.id.textloading);
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

                dataModelPortfolio = response.body();

                if (response.body()!=null){

                    for (int i=0; i<dataModelPortfolio.getTotal(); i++) {

                        try {
                            if (dataModelPortfolio.getData().getTrPortofolio().get(i).getMuridid().toString()
                                    .equalsIgnoreCase(PreferenceUtils.getIdSiswa(getApplicationContext()))) {
                                listPortofolio.add(dataModelPortfolio.getData().getTrPortofolio().get(i));
                                //LIST SEMUA PORTFOLIO SI SISWA YG LOGIN
                            }
                        } catch (Exception e){

                        }
                    }

                    if(listPortofolio==null) {
                        Toast.makeText(Organisasi.this, "Kamu Tidak Memiliki Portofolio Organisasi", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("Organisasi")){
                                    listOrganisasiMurid.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                    }


                    if (listOrganisasiMurid!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterListOrganisasi(listOrganisasiMurid);
                                rvOrganisasi.setLayoutManager(new LinearLayoutManager(Organisasi.this));
                                rvOrganisasi.setAdapter(itemList);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(Organisasi.this, "Tidak Memiliki Organisasi", Toast.LENGTH_SHORT).show();
                                /*try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Karya.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(Karya.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }*/

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelPortofolio> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(Organisasi.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Organisasi.this, PortfolioStudent.class);
        startActivity(a);
        finish();
    }
}
