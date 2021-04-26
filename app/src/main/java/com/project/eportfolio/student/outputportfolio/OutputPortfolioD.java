package com.project.eportfolio.student.outputportfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterPortfolioDuaModel;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterOutputPortfolio;
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.student.portfolio.PortfolioStudentProject;
import com.project.eportfolio.teacher.master.DataPortfolioDuaModel;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutputPortfolioD extends AppCompatActivity {

    RecyclerView rvOutputPortfolioD;
    AdapterOutputPortfolio itemList;

    ModelPortofolio modelDataPortfolio;
    List<TrPortofolio> listPortfolio = new ArrayList<>();
    ModelGuru modelDataGuru;
    List<MsGuru> listGuru= new ArrayList<>();
    ModelMataPelajaran modelDataMapel;
    List<MsMatapelajaran> listMapel = new ArrayList<>();
    ModelStrategi dataModelStrategi;
    List<MsStrategi> listStrategi = new ArrayList<>();


    String apikey = "7826470ABBA476706DB24D47C6A6ED64";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_output_portfolio_d);

        rvOutputPortfolioD = findViewById(R.id.rvOutputPortfolioD);
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
                    for (int i = 0; i < modelDataPortfolio.getTotal(); i++) {
                        try {
                            if (modelDataPortfolio.getData().getTrPortofolio().get(i).getMuridid().toString()
                                    .equalsIgnoreCase(PreferenceUtils.getIdSiswa(getApplicationContext()))) {
                                listPortfolio.add(modelDataPortfolio.getData().getTrPortofolio().get(i));
                            }
                        } catch (Exception e){

                        }
                    }

                    if (listPortfolio!=null){
                        getMapel();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelPortofolio> call, Throwable t) {

                Toast.makeText(OutputPortfolioD.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
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
                            if (PreferenceUtils.getIdSekolahSiswa(getApplicationContext())
                                    .equalsIgnoreCase(modelDataMapel.getData().getMsMatapelajaran().get(i).getSekolahid())) {
                                listMapel.add(modelDataMapel.getData().getMsMatapelajaran().get(i));
                            }
                        } catch (Exception e){

                        }
                    }

                    if (listMapel!=null){
                        getGuru();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelMataPelajaran> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OutputPortfolioD.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getGuru() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelGuru> dataSiswax = apiInterface.getdataGuru(  apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelGuru>() {
            @Override
            public void onResponse(Call<ModelGuru> call, Response<ModelGuru> response) {

                modelDataGuru = response.body();

                if (modelDataGuru!=null){
                    for (int i = 0; i < modelDataGuru.getTotal(); i++) {
                        try {
                            if (PreferenceUtils.getIdSekolahSiswa(getApplicationContext())
                                    .equalsIgnoreCase(modelDataGuru.getData().getMsGuru().get(i).getSekolahid())) {
                                listGuru.add(modelDataGuru.getData().getMsGuru().get(i));
                            }
                        } catch (Exception e){

                        }
                    }

                    if (listMapel!=null){
                        getStrategi();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelGuru> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OutputPortfolioD.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getStrategi() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelStrategi> dataSiswax = apiInterface.getDataStrategi(  apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelStrategi>() {
            @Override
            public void onResponse(Call<ModelStrategi> call, Response<ModelStrategi> response) {
                dataModelStrategi = response.body();
                if (response.body()!=null){
                    listStrategi.clear();
                    for (int i = 0; i < dataModelStrategi.getData().getMsStrategi().size(); i++) {
                        listStrategi.add(dataModelStrategi.getData().getMsStrategi().get(i));
                    }
                }

                if (listStrategi!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            itemList = new AdapterOutputPortfolio(listPortfolio, listGuru, listMapel, listStrategi);
                            rvOutputPortfolioD.setLayoutManager(new LinearLayoutManager(OutputPortfolioD.this));
                            rvOutputPortfolioD.setAdapter(itemList);
                        }
                    });
                }

            }
            @Override
            public void onFailure(Call<ModelStrategi> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OutputPortfolioD.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }


    public void onBackPressed() {
        Intent a = new Intent(OutputPortfolioD.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}