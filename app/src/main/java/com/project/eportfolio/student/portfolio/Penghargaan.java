package com.project.eportfolio.student.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListOrganisasi;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListPenghargaan;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.student.PortfolioStudent;
import com.project.eportfolio.utility.PreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Penghargaan extends AppCompatActivity {

    RecyclerView rvpenghargaan;
    ModelPortofolio dataModelPortfolio;
    List<TrPortofolio> listPortofolio = new ArrayList<>();
    List<TrPortofolio> listPenghargaanMurid = new ArrayList<>();
    AdapterListPenghargaan itemList;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_portfolio_penghargaan);

        rvpenghargaan = findViewById(R.id.rvPenghargaan);
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

                    for (int i=0; i<dataModelPortfolio.getData().getTrPortofolio().size(); i++) {
                        if (dataModelPortfolio.getData().getTrPortofolio().get(i).getMuridid().toString()
                                .equalsIgnoreCase(PreferenceUtils.getIdSiswa(getApplicationContext()))) {
                            listPortofolio.add(dataModelPortfolio.getData().getTrPortofolio().get(i));
                            //LIST SEMUA PORTFOLIO SI SISWA YG LOGIN
                        }
                    }

                    for(int i=0; i<listPortofolio.size(); i++){
                        if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("Penghargaan")){
                            listPenghargaanMurid.add(listPortofolio.get(i));
                        }
                    }

                    if (listPenghargaanMurid!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterListPenghargaan(listPenghargaanMurid);
                                rvpenghargaan.setLayoutManager(new LinearLayoutManager(Penghargaan.this));
                                rvpenghargaan.setAdapter(itemList);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(Penghargaan.this, "Tidak Memiliki Penghargaan", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Penghargaan.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Penghargaan.this, PortfolioStudent.class);
        startActivity(a);
        finish();
    }
}
