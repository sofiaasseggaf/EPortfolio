package com.project.eportfolio.teacher.master;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterPortfolioDuaModel;
import com.project.eportfolio.detail.PortfolioDetail;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.student.portfolio.PortfolioStudentUnjukKerja;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.utility.PreferenceUtils;
import com.project.eportfolio.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPortfolioDuaModel extends AppCompatActivity {

    RecyclerView rvDataPortfolio;
    AdapterMasterPortfolioDuaModel itemList;

    ModelPortofolio modelDataPortfolio;
    List<TrPortofolio> listPortfolio = new ArrayList<>();
    ModelSiswa modelDataSiswa;
    List<MsMurid> listsiswa= new ArrayList<>();
    ModelMataPelajaran modelDataMapel;
    List<MsMatapelajaran> listMapel = new ArrayList<>();
    ModelStrategi dataModelStrategi;
    List<MsStrategi> listStrategi = new ArrayList<>();
    TextView txtload;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";
    String field;
    int filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_master_dataportfolio);

        rvDataPortfolio = findViewById(R.id.rvDataPortfolio);
        txtload = findViewById(R.id.textloading);
        filter = Integer.parseInt(PreferenceUtils.getUserId(getApplicationContext()));
        field = "guruid";

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
                    txtload.setText("Loading Data Portfolio .");
                }
                else if (count == 2)
                {
                    txtload.setText("Loading Data Portfolio . .");
                }
                else if (count == 3)
                {
                    txtload.setText("Loading Data Portfolio . . .");
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
                getPortfolio();
            }
        }).start();
    }

    public void getPortfolio() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPortofolio> dataSiswax = apiInterface.getDataPortfolio2(  apikey, 1000, filter, field);

        dataSiswax.enqueue(new Callback<ModelPortofolio>() {
            @Override
            public void onResponse(Call<ModelPortofolio> call, Response<ModelPortofolio> response) {
                modelDataPortfolio = response.body();
                if (modelDataPortfolio!=null){
                    for (int i = 0; i < modelDataPortfolio.getTotal(); i++) {
                        try {
                            String id = PreferenceUtils.getUserId(getApplicationContext());
                            if (id.equalsIgnoreCase(modelDataPortfolio.getData().getTrPortofolio().get(i).getGuruid())) {
                                listPortfolio.add(modelDataPortfolio.getData().getTrPortofolio().get(i));
                            }
                        } catch (Exception e){

                        }
                    }

                    if (listPortfolio!=null){
                        getSiswa();
                    }
/*

                    if (listPortfolio!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterMasterPortfolio(listPortfolio);
                                rvDataPortfolio.setLayoutManager(new LinearLayoutManager(DataPortfolioDuaModel.this));
                                rvDataPortfolio.setAdapter(itemList);
                            }
                        });
                    }

*/

                }
            }
            @Override
            public void onFailure(Call<ModelPortofolio> call, Throwable t) {

                Toast.makeText(DataPortfolioDuaModel.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
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
                        getMapel();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelSiswa> call, Throwable t) {

                Toast.makeText(DataPortfolioDuaModel.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                            if (PreferenceUtils.getIdGuru(getApplicationContext())
                                    .equalsIgnoreCase(modelDataMapel.getData().getMsMatapelajaran().get(i).getGuruid())) {
                                listMapel.add(modelDataMapel.getData().getMsMatapelajaran().get(i));
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
            public void onFailure(Call<ModelMataPelajaran> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DataPortfolioDuaModel.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                            itemList = new AdapterMasterPortfolioDuaModel(listPortfolio, listsiswa, listMapel, listStrategi);
                            rvDataPortfolio.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                            rvDataPortfolio.setAdapter(itemList);
                            rvDataPortfolio.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvDataPortfolio,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            //Toast.makeText(PortfolioStudentUnjukKerja.this, "this : "+listUnjukKerjaMurid.get(position).getId(), Toast.LENGTH_SHORT).show();
                                            Intent a = new Intent(DataPortfolioDuaModel.this, PortfolioDetail.class);
                                            a.putExtra("idportfolio", listPortfolio.get(position).getId());
                                            startActivity(a);
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {

                                        }
                                    }));
                        }
                    });
                }

            }
            @Override
            public void onFailure(Call<ModelStrategi> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DataPortfolioDuaModel.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }



    public void onBackPressed() {
        Intent a = new Intent(DataPortfolioDuaModel.this, MasterTeacher.class);
        startActivity(a);
        finish();
    }
}
