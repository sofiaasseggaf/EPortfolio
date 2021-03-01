package com.project.eportfolio;

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
import com.project.eportfolio.adapter.adapterArtikel.AdapterArtikel;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterPortfolioDuaModel;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterSiswa;
import com.project.eportfolio.model.blog.Blog;
import com.project.eportfolio.model.blog.ModelBlog;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.teacher.HomeTeacher;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.teacher.master.DataMurid;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtikelActivity extends AppCompatActivity {

    RecyclerView rvArtikel;
    AdapterArtikel itemList;
    ModelBlog modelDataBlog;
    List<Blog> listArtikel = new ArrayList<>();
    TextView txtload;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        rvArtikel = findViewById(R.id.rvArtikel);
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
                    txtload.setText("Loading Artikel .");
                }
                else if (count == 2)
                {
                    txtload.setText("Loading Artikel . .");
                }
                else if (count == 3)
                {
                    txtload.setText("Loading Artikel . . .");
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
                getArtikel();
            }
        }).start();
    }

    public void getArtikel() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelBlog> dataBlog = apiInterface.getDataBlog( apikey, 1000);

        dataBlog.enqueue(new Callback<ModelBlog>() {
            @Override
            public void onResponse(Call<ModelBlog> call, Response<ModelBlog> response) {
                modelDataBlog = response.body();
                if (modelDataBlog!=null){
                    for (int i = 0; i < modelDataBlog.getTotal(); i++) {
                        try {
                            listArtikel.add(modelDataBlog.getData().getBlog().get(i));
                        } catch (Exception e){
                        }
                    }

                    if (listArtikel!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterArtikel(listArtikel);
                                rvArtikel.setLayoutManager(new LinearLayoutManager(ArtikelActivity.this));
                                rvArtikel.setAdapter(itemList);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelBlog> call, Throwable t) {

                Toast.makeText(ArtikelActivity.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void onBackPressed() {
        Intent a = new Intent(ArtikelActivity.this, HomeTeacher.class);
        startActivity(a);
        finish();
    }

}