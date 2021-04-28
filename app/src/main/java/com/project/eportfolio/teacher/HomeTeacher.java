package com.project.eportfolio.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.ArtikelActivity;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterArtikel.AdapterSliderArtikel;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterSliderPortfolio;
import com.project.eportfolio.model.blog.Blog;
import com.project.eportfolio.model.blog.ModelBlog;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.teacher.input.InputTeacherA;
import com.project.eportfolio.teacher.master.DataGuru;
import com.project.eportfolio.teacher.master.DataKelas;
import com.project.eportfolio.teacher.master.DataMapel;
import com.project.eportfolio.teacher.master.DataMurid;
import com.project.eportfolio.teacher.master.DataPortfolioDuaModel;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeTeacher extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile;
    ImageButton btn_home_kelas, btn_home_mapel, btn_home_guru, btn_home_siswa;
    ImageButton btn_home_portfolio, btn_home_master, btn_home_artikel, btn_home_kalender;

    String namaguru;
    TextView namaGuru, txtloadartikel, txtMoreArtikelGuru;
    ImageView fotoGuru;

    ModelBlog dataModelBlog;
    List<Blog> listBlog = new ArrayList<>();
    AdapterSliderArtikel itemListArtikel;
    ViewPager viewPagerArticleTeacher;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAnimation();
        setContentView(R.layout.teacher_home);

        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);

        btn_home_kelas = findViewById(R.id.btn_home_kelas);
        btn_home_mapel = findViewById(R.id.btn_home_mapel);
        btn_home_guru = findViewById(R.id.btn_home_guru);
        btn_home_siswa = findViewById(R.id.btn_home_siswa);
        btn_home_portfolio = findViewById(R.id.btn_home_portfolio);
        btn_home_master = findViewById(R.id.btn_home_master);
        btn_home_artikel = findViewById(R.id.btn_home_artikel);
        btn_home_kalender = findViewById(R.id.btn_home_kalender);

        namaGuru = findViewById(R.id.namaGuru);
        fotoGuru = findViewById(R.id.imgGuru);
        txtMoreArtikelGuru = findViewById(R.id.txtMoreArticleGuru);
        txtMoreArtikelGuru.setPaintFlags(txtMoreArtikelGuru.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        viewPagerArticleTeacher = findViewById(R.id.viewPagerArticleTeacher);
        txtloadartikel = findViewById(R.id.textloading);

        setDataGuru();
        first();

        btn_master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, MasterTeacher.class);
                startActivity(a);
                finish();
            }
        });

        /*txtMorePortfolioGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, DataPortfolioDuaModel.class);
                startActivity(a);
                finish();
                *//*if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(HomeTeacher.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }*//*
            }
        });*/

        txtMoreArtikelGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HomeTeacher.this, ArtikelActivity.class);
                startActivity(a);
                finish();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, InputTeacherA.class);
                startActivity(a);
                finish();
                /*if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(HomeTeacher.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }*/
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, ProfileTeacher.class);
                startActivity(a);
                finish();
               /* if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(HomeTeacher.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }*/
            }
        });

        btn_home_kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, DataKelas.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_mapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, DataMapel.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, DataGuru.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, DataMurid.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, DataPortfolioDuaModel.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, MasterTeacher.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeTeacher.this, ArtikelActivity.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeTeacher.this, "Trial Version", Toast.LENGTH_SHORT).show();
            }
        });

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
                    txtloadartikel.setText("Loading Article .");
                }
                else if (count == 2)
                {
                    txtloadartikel.setText("Loading Article . .");
                }
                else if (count == 3)
                {
                    txtloadartikel.setText("Loading Article . . .");
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
                getArticle();
            }
        }).start();
    }

    public void setDataGuru(){
        namaguru = PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext());
        namaGuru.setText(namaguru);
        //nipGuru.setText(PreferenceUtils.getNip(getApplicationContext()));
        try{
            Picasso.get().load(PreferenceUtils.getPhotoGuru(getApplicationContext())).into(fotoGuru);
//            Picasso.get().load("https://eportofolio.id/uploads/ms_guru/"+PreferenceUtils.getPhotoGuru(getApplicationContext())).into(fotoGuru);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setDataArtikel(){
        if (listBlog!=null){
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0; i<5; i++){
                            itemListArtikel = new AdapterSliderArtikel(listBlog, getApplicationContext());
                           /* LinearLayoutManager layoutManager
                                    = new LinearLayoutManager(HomeTeacher.this, LinearLayoutManager.HORIZONTAL, false);
                            rvSliderPortfolioGuru.setLayoutManager(layoutManager);*/
                            viewPagerArticleTeacher.setAdapter(itemListArtikel);
                        }
                    }
                });
            } catch (Exception e){

            }

        }
    }

    public void getArticle() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelBlog> dataSiswax = apiInterface.getDataBlog(  apikey, 10000);

        dataSiswax.enqueue(new Callback<ModelBlog>() {
            @Override
            public void onResponse(Call<ModelBlog> call, Response<ModelBlog> response) {

                dataModelBlog = response.body();

                if (response.body()!=null) {
                    for (int i = 0; i < dataModelBlog.getTotal(); i++) {
                        listBlog.add(dataModelBlog.getData().getBlog().get(i));
                    }
                }

                if (listBlog!=null){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            setDataArtikel();
                        }
                    });

                }
            }
            @Override
            public void onFailure(Call<ModelBlog> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(HomeTeacher.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda Mau Menutup Aplikasi")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        HomeTeacher.super.onBackPressed();
                        finish();
                        finishAffinity();
                    }
                })

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }
}
