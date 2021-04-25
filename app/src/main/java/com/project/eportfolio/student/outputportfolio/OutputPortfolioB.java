package com.project.eportfolio.student.outputportfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterSliderPortfolio;
import com.project.eportfolio.model.achievement.Achievment;
import com.project.eportfolio.model.achievement.ModelAchievement;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.utility.OnSwipeTouchListener;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutputPortfolioB extends AppCompatActivity {

    ImageView imgOutput;
    TextView namaOutput, sekolahOutput, kelasOutput;
    String namasiswa;
    TextView outputOrganisasi, outputPenghargaan, outputP, outputK, outputUK;
    OnSwipeTouchListener onSwipeTouchListener;

    ModelPortofolio dataModelPortfolio;
    ModelAchievement dataModelAchievement;
    List<Achievment> listAchievement = new ArrayList<>();
    List<TrPortofolio> listPortofolio = new ArrayList<>();
    List<TrPortofolio> listKaryaMurid = new ArrayList<>();
    List<TrPortofolio> listProyekMurid = new ArrayList<>();
    List<TrPortofolio> listUnjukKerjaMurid = new ArrayList<>();
    List<Achievment> listPenghargaan = new ArrayList<>();
    List<Achievment> listOrganisasi = new ArrayList<>();
    //List<TrPortofolio> listForumEdukasi = new ArrayList<>();
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_output_portfolio_b);

        imgOutput = findViewById(R.id.imgOutput);
        namaOutput = findViewById(R.id.namaOutput);
        kelasOutput = findViewById(R.id.kelasOutput);
        sekolahOutput = findViewById(R.id.sekolahOutput);

        outputK = findViewById(R.id.outputK);
        outputUK = findViewById(R.id.outputUK);
        outputP = findViewById(R.id.outputP);
        outputPenghargaan = findViewById(R.id.outputPenghargaan);
        outputOrganisasi = findViewById(R.id.outputOrganisasi);

        first();
        setDataSiswa();

        onSwipeTouchListener = new OnSwipeTouchListener(OutputPortfolioB.this) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                Intent a = new Intent(OutputPortfolioB.this, OutputPortfolioA.class);
                startActivity(a);
                finish();
            }
            public void onSwipeLeft() {
                Intent a = new Intent(OutputPortfolioB.this, OutputPortfolioC.class);
                startActivity(a);
                finish();
            }
            public void onSwipeBottom() {

            }
        };



    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        getAchievement();
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
                                Toast.makeText(OutputPortfolioB.this, "Kamu Belum Memiliki Portofolio", Toast.LENGTH_SHORT).show();
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

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setDataPortfolio();
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
                        Toast.makeText(OutputPortfolioB.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getAchievement() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAchievement> dataSiswax = apiInterface.getDataAchievement(  apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelAchievement>() {
            @Override
            public void onResponse(Call<ModelAchievement> call, Response<ModelAchievement> response) {

                dataModelAchievement = response.body();

                if (response.body()!=null){

                    for (int i=0; i<dataModelAchievement.getTotal(); i++) {
                        try {
                            if (String.valueOf(dataModelAchievement.getData().getAchievment().get(i).getMuridid())
                                    .equalsIgnoreCase(PreferenceUtils.getIdSiswa(getApplicationContext()))) {
                                listAchievement.add(dataModelAchievement.getData().getAchievment().get(i));
                                //LIST SEMUA ACHIEVEMENT SI SISWA YG LOGIN
                            }
                        } catch (Exception e){

                        }
                    }

                    if(listAchievement==null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(OutputPortfolioB.this, "Kamu Belum Memiliki Portofolio", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        for(int i=0; i<listAchievement.size(); i++){
                            try {
                                if (listAchievement.get(i).getKategoripo().equalsIgnoreCase("1")){
                                    listPenghargaan.add(listAchievement.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listAchievement.size(); i++){
                            try {
                                if (listAchievement.get(i).getKategoripo().equalsIgnoreCase("2")){
                                    listOrganisasi.add(listOrganisasi.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        /*for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getStrategiid().equalsIgnoreCase("6")){
                                    listForumEdukasi.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }*/

                        getPortfolio();

                    }
                }

            }
            @Override
            public void onFailure(Call<ModelAchievement> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(OutputPortfolioB.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void setDataPortfolio(){

        outputK.setText(""+listKaryaMurid.size());
        outputUK.setText(""+listUnjukKerjaMurid.size());
        outputP.setText(""+listProyekMurid.size());
        outputOrganisasi.setText(""+listOrganisasi.size());
        outputPenghargaan.setText(""+listPenghargaan.size());
        //txtForumEdukasi.setText(listForumEdukasi.size() + "   Forum Edukasi");

    }

    public void setDataSiswa(){
        namasiswa = PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext());
        namaOutput.setText(namasiswa);
        sekolahOutput.setText(PreferenceUtils.getSekolahNama(getApplicationContext()));
        kelasOutput.setText(PreferenceUtils.getKelasNama(getApplicationContext()));
        try{
            if (!PreferenceUtils.getPhotoSiswa(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoSiswa(getApplicationContext())!=null){
                Picasso.get().load(PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imgOutput.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        //Toast.makeText(HomeStudent.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            }
        } catch (Exception e){ }
    }


    public void onBackPressed() {
        Intent a = new Intent(OutputPortfolioB.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}