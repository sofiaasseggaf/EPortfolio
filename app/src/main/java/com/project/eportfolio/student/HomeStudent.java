package com.project.eportfolio.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
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
    ModelStrategi dataModelStrategi;
    List<MsStrategi> listKarya = new ArrayList<>();
    List<MsStrategi> listProyek = new ArrayList<>();
    List<MsStrategi> listUnjukKerja = new ArrayList<>();

    List<TrPortofolio> listKaryaMurid = new ArrayList<>();
    List<TrPortofolio> listProyekMurid = new ArrayList<>();
    List<TrPortofolio> listUnjukKerjaMurid = new ArrayList<>();
    List<TrPortofolio> listPenghargaan = new ArrayList<>();
    List<TrPortofolio> listOrganisasi = new ArrayList<>();
    List<TrPortofolio> listForumEdukasi = new ArrayList<>();


    String namasiswa;
    TextView namaSiswa, nisSiswa;
    ImageView fotoSiswa;
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_home);

        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);

        namaSiswa = findViewById(R.id.namaSiswa);
        nisSiswa = findViewById(R.id.nisSiswa);
        fotoSiswa = findViewById(R.id.imgSiswa);

        txtForumEdukasi = findViewById(R.id.txtForumEdukasi);
        txtKarya = findViewById(R.id.txtKarya);
        txtOrganisasi = findViewById(R.id.txtOrganisasi);
        txtPenghargaan = findViewById(R.id.txtPenghargaan);
        txtProyek = findViewById(R.id.txtProyek);
        txtUnjukKerja = findViewById(R.id.txtUnjukKerja);

        first();

        btn_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, PortfolioStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeStudent.this, InputStudent.class);
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

    }

    private void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getStrategi();
            }
        }).start();

        //getdataportfoliosiswa, setelah itu dapet semua baru ke main thread trus set data siswa
    }

    public void getStrategi() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelStrategi> dataSiswax = apiInterface.getDataStrategi( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelStrategi>() {
            @Override
            public void onResponse(Call<ModelStrategi> call, Response<ModelStrategi> response) {

                dataModelStrategi = response.body();

                if (response.body()!=null) {
                    for(int i=0; i<dataModelStrategi.getData().getMsStrategi().size(); i++){
                        if (dataModelStrategi.getData().getMsStrategi().get(i).getKategoriid().equalsIgnoreCase("3")){
                            listKarya.add(dataModelStrategi.getData().getMsStrategi().get(i));
                        }
                    }

                    for(int i=0; i<dataModelStrategi.getData().getMsStrategi().size(); i++){
                        if (dataModelStrategi.getData().getMsStrategi().get(i).getKategoriid().equalsIgnoreCase("1")){
                            listUnjukKerja.add(dataModelStrategi.getData().getMsStrategi().get(i));
                        }
                    }

                    for(int i=0; i<dataModelStrategi.getData().getMsStrategi().size(); i++){
                        if (dataModelStrategi.getData().getMsStrategi().get(i).getKategoriid().equalsIgnoreCase("2")){
                            listProyek.add(dataModelStrategi.getData().getMsStrategi().get(i));
                        }
                    }

                    getPortfolio();
                }
            }
            @Override
            public void onFailure(Call<ModelStrategi> call, Throwable t) {
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

    public void getPortfolio() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPortofolio> dataSiswax = apiInterface.getDataPortfolio(  apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelPortofolio>() {
            @Override
            public void onResponse(Call<ModelPortofolio> call, Response<ModelPortofolio> response) {

                dataModelPortfolio = response.body();

                if (response.body()!=null){

                    for (int i=0; i<dataModelPortfolio.getData().getTrPortofolio().size(); i++) {
                        if (String.valueOf(dataModelPortfolio.getData().getTrPortofolio().get(i).getMuridid())
                                .equalsIgnoreCase(PreferenceUtils.getIdSiswa(getApplicationContext()))) {
                            listPortofolio.add(dataModelPortfolio.getData().getTrPortofolio().get(i));
                            //LIST SEMUA PORTFOLIO SI SISWA YG LOGIN
                        }
                    }

                    for(int i=0; i<listPortofolio.size(); i++){
                        if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase(listKarya.get(i).getIdStrategi())){
                            listKaryaMurid.add(listPortofolio.get(i));
                        }
                    }

                    for(int i=0; i<listPortofolio.size(); i++){
                        if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase(listUnjukKerja.get(i).getIdStrategi())){
                            listUnjukKerjaMurid.add(listPortofolio.get(i));
                        }
                    }

                    for(int i=0; i<listPortofolio.size(); i++){
                        if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase(listProyek.get(i).getIdStrategi())){
                            listProyekMurid.add(listPortofolio.get(i));
                        }
                    }

                    for(int i=0; i<listPortofolio.size(); i++){
                        if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("Organisasi")){
                            listOrganisasi.add(listPortofolio.get(i));
                        }
                    }

                    for(int i=0; i<listPortofolio.size(); i++){
                        if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("Penghargaan")){
                            listPenghargaan.add(listPortofolio.get(i));
                        }
                    }

                    for(int i=0; i<listPortofolio.size(); i++){
                        if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("Forum Edukasi")){
                            listForumEdukasi.add(listPortofolio.get(i));
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            setDataSiswa();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(HomeStudent.this, "Siswa Tidak Memiliki Portofolio", Toast.LENGTH_SHORT).show();
                        }
                    });
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

        txtKarya.setText(listKaryaMurid.size() + " Karya");
        txtUnjukKerja.setText(listUnjukKerjaMurid.size() + " Unjuk Kerja");
        txtProyek.setText(listProyekMurid.size() + " Proyek");
        txtOrganisasi.setText(listOrganisasi.size() + " Organisasi");
        txtPenghargaan.setText(listPenghargaan.size() + " Penghargaan");
        txtForumEdukasi.setText(listForumEdukasi.size() + " Forum Edukasi");

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
