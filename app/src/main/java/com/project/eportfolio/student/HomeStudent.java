package com.project.eportfolio.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.ArtikelActivity;
import com.project.eportfolio.R;
//import com.project.eportfolio.adapter.adapterPortfolio.AdapterSliderPortfolio;
import com.project.eportfolio.adapter.adapterArtikel.AdapterSliderArtikel;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterSliderPortfolio;
import com.project.eportfolio.model.blog.Blog;
import com.project.eportfolio.model.blog.ModelBlog;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.student.outputportfolio.OutputPortfolioA;
import com.project.eportfolio.student.outputportfolio.OutputPortfolioD;
import com.project.eportfolio.student.portfolio.PortfolioStudentAchievement;
import com.project.eportfolio.student.portfolio.PortfolioStudentKarya;
import com.project.eportfolio.student.portfolio.PortfolioStudentProject;
import com.project.eportfolio.student.portfolio.PortfolioStudentUnjukKerja;
import com.project.eportfolio.teacher.HomeTeacher;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;

public class HomeStudent extends AppCompatActivity {

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile, btn_banner;
    LinearLayout btn_beranda2, btn_portfolio2, btn_input2, btn_profile2;
    TextView txtUnjukKerja, txtProyek, txtKarya, txtOrganisasi, txtForumEdukasi, txtPenghargaan, txtload;
    ImageButton btn_home_karya, btn_home_unjukkerja, btn_home_project, btn_home_achievement;
    ImageButton btn_home_mapel, btn_home_guru, btn_home_artikel, btn_home_kalender;

    ModelPortofolio dataModelPortfolio;
    List<TrPortofolio> listPortofolio = new ArrayList<>();
    List<TrPortofolio> listKaryaMurid = new ArrayList<>();
    List<TrPortofolio> listProyekMurid = new ArrayList<>();
    List<TrPortofolio> listUnjukKerjaMurid = new ArrayList<>();
    List<TrPortofolio> listPenghargaan = new ArrayList<>();
    List<TrPortofolio> listOrganisasi = new ArrayList<>();
    List<TrPortofolio> listForumEdukasi = new ArrayList<>();

    ModelBlog dataModelBlog;
    List<Blog> listBlog = new ArrayList<>();
    AdapterSliderArtikel itemListArtikel;
    ViewPager viewPagerArticleStudent;

    AdapterSliderPortfolio itemList;
    ViewPager viewPager;
    RecyclerView rvSliderPortfolioSiswa;

    String namasiswa;
    TextView namaSiswa,  txtMoreArticleSiswa;
    //TextView nisSiswa;
    ImageView fotoSiswa;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAnimation();
        setContentView(R.layout.student_home);

        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btn_beranda2 = findViewById(R.id.btn_home2);
        btn_portfolio2 = findViewById(R.id.btn_portfolio2);
        btn_input2 = findViewById(R.id.btn_input2);
        btn_profile2 = findViewById(R.id.btn_profile2);
        btn_banner = findViewById(R.id.btn_banner);

        btn_home_karya = findViewById(R.id.btn_home_karya);
        btn_home_unjukkerja = findViewById(R.id.btn_home_unjukkerja);
        btn_home_project = findViewById(R.id.btn_home_project);
        btn_home_achievement = findViewById(R.id.btn_home_achievement);
        btn_home_mapel = findViewById(R.id.btn_home_mapel);
        btn_home_guru = findViewById(R.id.btn_home_guru);
        btn_home_artikel = findViewById(R.id.btn_home_artikel);
        btn_home_kalender = findViewById(R.id.btn_home_kalender);

        namaSiswa = findViewById(R.id.namaSiswa);
        //nisSiswa = findViewById(R.id.nisSiswa);
        fotoSiswa = findViewById(R.id.imgSiswa);

        txtMoreArticleSiswa = findViewById(R.id.txtMoreArticleSiswa);
        txtMoreArticleSiswa.setPaintFlags(txtMoreArticleSiswa.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        viewPagerArticleStudent = findViewById(R.id.viewPagerArticleStudent);
        txtload = findViewById(R.id.textloading);

/*

        txtForumEdukasi = findViewById(R.id.txtForumEdukasi);
        txtKarya = findViewById(R.id.txtKarya);
        txtOrganisasi = findViewById(R.id.txtOrganisasi);
        txtPenghargaan = findViewById(R.id.txtPenghargaan);
        txtProyek = findViewById(R.id.txtProyek);
        txtUnjukKerja = findViewById(R.id.txtUnjukKerja);

*/

        //rvSliderPortfolioSiswa = findViewById(R.id.rvSliderPortfolioSiswa);
        //viewPager = findViewById(R.id.viewPager);
        txtload = findViewById(R.id.textloading);

        setDataSiswa();
        first();
        //first();

        txtMoreArticleSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HomeStudent.this, ArtikelActivity.class);
                startActivity(a);
                finish();
            }
        });

        btn_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();
            }
        });

        btn_portfolio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();
            }
        });

/*
        txtMorePortfolioSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudent.class);
                startActivity(a);
                finish();
                *//*if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(HomeStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }*//*
            }
        });

      */

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, InputStudent.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, ProfileStudent.class);
                startActivity(a);
                finish();

            }
        });

        btn_input2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, InputStudent.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
                finish();
            }
        });

        btn_profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, ProfileStudent.class);
                startActivity(a);
                finish();

            }
        });

        btn_home_karya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudentKarya.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_unjukkerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudentUnjukKerja.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudentAchievement.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_mapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, MapelStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, GuruStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, ArtikelActivity.class);
                startActivity(a);
                finish();
            }
        });

        btn_home_kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeStudent.this, "Trial Version", Toast.LENGTH_SHORT).show();
            }
        });

        btn_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, OutputPortfolioA.class);
                startActivity(a);
                finish();
            }
        });

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
                            if (String.valueOf(dataModelPortfolio.getData().getTrPortofolio().get(i).getMuridid())
                                    .equalsIgnoreCase(PreferenceUtils.getIdSiswa(getApplicationContext()))) {
                                listPortofolio.add(dataModelPortfolio.getData().getTrPortofolio().get(i));
                                //LIST SEMUA PORTFOLIO SI SISWA YG LOGIN
                            }
                        } catch (Exception e){

                        }
                    }

                    if(listPortofolio==null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(HomeStudent.this, "Kamu Belum Memiliki Portofolio", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("3")){
                                    listKaryaMurid.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("1")){
                                    listUnjukKerjaMurid.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("2")){
                                    listProyekMurid.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("4")){
                                    listOrganisasi.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("5")){
                                    listPenghargaan.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("6")){
                                    listForumEdukasi.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                //setDataPortfolio();
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
                        Toast.makeText(HomeStudent.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
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
                    txtload.setText("Loading Article .");
                }
                else if (count == 2)
                {
                    txtload.setText("Loading Article . .");
                }
                else if (count == 3)
                {
                    txtload.setText("Loading Article . . .");
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

    public void setDataSiswa(){

        namasiswa = PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext());
        namaSiswa.setText(namasiswa);
        //nisSiswa.setText("NIS : "+PreferenceUtils.getNis(getApplicationContext()));
        try{
        if (!PreferenceUtils.getPhotoSiswa(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoSiswa(getApplicationContext())!=null){

            Picasso.get().load(PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    fotoSiswa.setImageBitmap(bitmap);
                }
                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    //Toast.makeText(HomeStudent.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });

            /*
                Picasso.get().load("https://eportofolio.id/uploads/ms_murid/"+PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        fotoSiswa.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        //Toast.makeText(HomeStudent.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
                */
        }
        } catch (Exception e){

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
                        Toast.makeText(HomeStudent.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
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
                            viewPagerArticleStudent.setAdapter(itemListArtikel);
                        }
                    }
                });
            } catch (Exception e){

            }

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda Mau Menutup Aplikasi")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        HomeStudent.super.onBackPressed();
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
