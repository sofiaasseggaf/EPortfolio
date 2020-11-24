package com.project.eportfolio.student;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
//import com.project.eportfolio.adapter.adapterPortfolio.AdapterSliderPortfolio;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterSliderPortfolio;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeStudent extends AppCompatActivity {

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    TextView txtUnjukKerja, txtProyek, txtKarya, txtOrganisasi, txtForumEdukasi, txtPenghargaan;

    ModelPortofolio dataModelPortfolio;
    List<TrPortofolio> listPortofolio = new ArrayList<>();
    List<TrPortofolio> listKaryaMurid = new ArrayList<>();
    List<TrPortofolio> listProyekMurid = new ArrayList<>();
    List<TrPortofolio> listUnjukKerjaMurid = new ArrayList<>();
    List<TrPortofolio> listPenghargaan = new ArrayList<>();
    List<TrPortofolio> listOrganisasi = new ArrayList<>();
    List<TrPortofolio> listForumEdukasi = new ArrayList<>();

    AdapterSliderPortfolio itemList;
    ViewPager viewPager;
    RecyclerView rvSliderPortfolioSiswa;

    String namasiswa;
    TextView namaSiswa, nisSiswa, txtMorePortfolioSiswa;
    ImageView fotoSiswa;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.student_home);

        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);

        namaSiswa = findViewById(R.id.namaSiswa);
        nisSiswa = findViewById(R.id.nisSiswa);
        fotoSiswa = findViewById(R.id.imgSiswa);
        txtMorePortfolioSiswa = findViewById(R.id.txtMorePortfolioSiswa);
        txtMorePortfolioSiswa.setPaintFlags(txtMorePortfolioSiswa.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        txtForumEdukasi = findViewById(R.id.txtForumEdukasi);
        txtKarya = findViewById(R.id.txtKarya);
        txtOrganisasi = findViewById(R.id.txtOrganisasi);
        txtPenghargaan = findViewById(R.id.txtPenghargaan);
        txtProyek = findViewById(R.id.txtProyek);
        txtUnjukKerja = findViewById(R.id.txtUnjukKerja);
        //rvSliderPortfolioSiswa = findViewById(R.id.rvSliderPortfolioSiswa);
        viewPager = findViewById(R.id.viewPager);

        first();

        btn_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudent.class);
                if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(HomeStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }
            }
        });

        txtMorePortfolioSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudent.class);
                if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(HomeStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, InputStudent.class);
                if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(HomeStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, ProfileStudent.class);
                if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(HomeStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }
            }
        });

    }

    private void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getPortfolio();
            }
        }).start();

        //getdataportfoliosiswa, setelah itu dapet semua baru ke main thread trus set data siswa
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
                                if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("Organisasi")){
                                    listOrganisasi.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("Penghargaan")){
                                    listPenghargaan.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("Forum Edukasi")){
                                    listForumEdukasi.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setDataSiswa();
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

    public void setDataSiswa(){

        namasiswa = PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext());
        namaSiswa.setText(namasiswa);
        nisSiswa.setText(PreferenceUtils.getNis(getApplicationContext()));
        if (!PreferenceUtils.getPhotoSiswa(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoSiswa(getApplicationContext())!=null){
            Picasso.get().load(PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    fotoSiswa.setImageBitmap(bitmap);
                }
                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Toast.makeText(HomeStudent.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }

        txtKarya.setText(listKaryaMurid.size() + "   Karya");
        txtUnjukKerja.setText(listUnjukKerjaMurid.size() + "   Unjuk Kerja");
        txtProyek.setText(listProyekMurid.size() + "   Proyek");
        txtOrganisasi.setText(listOrganisasi.size() + "   Organisasi");
        txtPenghargaan.setText(listPenghargaan.size() + "   Penghargaan");
        txtForumEdukasi.setText(listForumEdukasi.size() + "   Forum Edukasi");

        if (listPortofolio!=null){
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        for (int i=0; i<5; i++){
                            itemList = new AdapterSliderPortfolio(listPortofolio, getApplicationContext());
                           /* LinearLayoutManager layoutManager
                                    = new LinearLayoutManager(HomeStudent.this, LinearLayoutManager.HORIZONTAL, false);
                            rvSliderPortfolioSiswa.setLayoutManager(layoutManager);*/
                            viewPager.setAdapter(itemList);
                        }
                    }
                });
            } catch (Exception e){

            }

        }

    }

    //Your Slide animation
    public void setAnimation(){
        if(Build.VERSION.SDK_INT>20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(500);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
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
