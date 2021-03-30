package com.project.eportfolio.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListUnjukKerja;
import com.project.eportfolio.databases.PortfolioDatabase;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.student.portfoliofragment.FragmentAchievement;
import com.project.eportfolio.student.portfoliofragment.FragmentKarya;
import com.project.eportfolio.student.portfoliofragment.FragmentProyek;
import com.project.eportfolio.student.portfoliofragment.FragmentUnjukKerja;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterViewPagerPortfolioStudent;
import com.project.eportfolio.utility.Constants;
import com.project.eportfolio.utility.NetworkCheck;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioStudent extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdapterViewPagerPortfolioStudent adapterViewPagerPortfolioStudent;

    private PortfolioDatabase mDatabase;
    private AdapterListUnjukKerja portfolioAdapter;
    private ProgressDialog mDialog;

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;

    ModelPortofolio dataModelPortfolio;
    ModelStrategi dataModelStrategi;
    List<MsStrategi> listStrategiKarya = new ArrayList<>();
    List<MsStrategi> listStrategiUnjukKerja = new ArrayList<>();
    List<MsStrategi> listStrategiProject = new ArrayList<>();
    List<MsStrategi> listStrategiAchievement = new ArrayList<>();
    List<TrPortofolio> listPortofolio = new ArrayList<>();
    List<TrPortofolio> listUnjukKerja = new ArrayList<>();
    List<TrPortofolio> listKarya = new ArrayList<>();
    List<TrPortofolio> listProject = new ArrayList<>();
    List<TrPortofolio> listAchievement = new ArrayList<>();

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    // DISINI GET SEMUA DATA PORTFOLIO, LALU DISIMPAN DI SQLITE, BARU TAMPILKAN DI MASING2 FRAGMENT

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAnimation();
        setContentView(R.layout.student_portfolio);

        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);

        tabLayout = findViewById(R.id.tabLayoutPortfolio);
        viewPager = findViewById(R.id.viewPagerPortfolio);
        adapterViewPagerPortfolioStudent = new AdapterViewPagerPortfolioStudent(getSupportFragmentManager());

        // tambahkan fragment disini

        adapterViewPagerPortfolioStudent.AddFragment(new FragmentUnjukKerja(), "Unjuk Kerja");
        adapterViewPagerPortfolioStudent.AddFragment(new FragmentKarya(), "Karya");
        adapterViewPagerPortfolioStudent.AddFragment(new FragmentProyek(), "Proyek");
        adapterViewPagerPortfolioStudent.AddFragment(new FragmentAchievement(), "Achievement");

        // tambahkan icon fragment disini

        tabLayout.getTabAt(0).setIcon(R.drawable.icon_unjuk_kerja);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_karya);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_project);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_achievment);

        // hapus shadow dari action bar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        viewPager.setAdapter(adapterViewPagerPortfolioStudent);
        tabLayout.setupWithViewPager(viewPager);

        if (NetworkCheck.isNetworkAvailable(getApplicationContext())){
            first();
        } else {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }


        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, InputStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, ProfileStudent.class);
                startActivity(a);
                finish();
            }
        });

    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);

        /*
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
*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                getStrategi();
            }
        }).start();
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
                            listStrategiKarya.add(dataModelStrategi.getData().getMsStrategi().get(i));
                        } else if (dataModelStrategi.getData().getMsStrategi().get(i).getKategoriid().equalsIgnoreCase("1")){
                            listStrategiUnjukKerja.add(dataModelStrategi.getData().getMsStrategi().get(i));
                        } else if (dataModelStrategi.getData().getMsStrategi().get(i).getKategoriid().equalsIgnoreCase("2")){
                            listStrategiProject.add(dataModelStrategi.getData().getMsStrategi().get(i));
                        }
                        /*else if (dataModelStrategi.getData().getMsStrategi().get(i).getKategoriid().equalsIgnoreCase("achievement")){
                            listStrategiAchievement.add(dataModelStrategi.getData().getMsStrategi().get(i));
                        }*/
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
                        Toast.makeText(PortfolioStudent.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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

                    for (int i=0; i<dataModelPortfolio.getTotal(); i++) {
                        try {
                            if (dataModelPortfolio.getData().getTrPortofolio().get(i).getMuridid().toString()
                                    .equalsIgnoreCase(PreferenceUtils.getIdSiswa(getApplicationContext()))) {
                                listPortofolio.add(dataModelPortfolio.getData().getTrPortofolio().get(i));
                                //LIST SEMUA PORTFOLIO SI SISWA YG LOGIN
                            }
                        } catch (Exception e){ }
                    }



                    if(listPortofolio==null){
                        Toast.makeText(PortfolioStudent.this, "Kamu Tidak Memiliki Portofolio", Toast.LENGTH_SHORT).show();
                    } else {

                        for (int i = 0; i < listPortofolio.size(); i++) {
                            TrPortofolio portofolio = listPortofolio.get(i);

                            SaveIntoDatabase task = new SaveIntoDatabase();
                            task.execute(portofolio);

                            portfolioAdapter.addPortfolio(portofolio);
                        }

                        /*

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("1")){
                                    listUnjukKerja.add(listPortofolio.get(i));
                                }
                            }catch (Exception e){ }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("2")){
                                    listProject.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }

                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("3")){
                                    listKarya.add(listPortofolio.get(i));
                                }
                            } catch (Exception e){

                            }
                        }
                    }

                    if (listUnjukKerja!=null){

                        // MASUKIN LIST UNJUK KERJA DI SQLITE


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterListUnjukKerja(listUnjukKerjaMurid);
                                rvunjukkerja.setLayoutManager(new LinearLayoutManager(PortfolioStudent.this));
                                rvunjukkerja.setAdapter(itemList);
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(PortfolioStudent.this, "Tidak Memiliki Unjuk Kerja", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }


                    if (listProject!=null){

                        // MASUKIN LIST PROJECT DI SQLITE

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterListProyek(listProyekMurid);
                                rvproyek.setLayoutManager(new LinearLayoutManager(PortfolioStudent.this));
                                rvproyek.setAdapter(itemList);
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(PortfolioStudent.this, "Tidak Memiliki Proyek", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                    if (listKarya!=null){

                        // MASUKIN LIST KARYA DI SQLITE

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterListKarya(listKaryaMurid);
                                rvkarya.setLayoutManager(new LinearLayoutManager(PortfolioStudent.this));
                                rvkarya.setAdapter(itemList);
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(PortfolioStudent.this, "Tidak Memiliki Karya", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


*/
                    }


                    /*
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
*/


                }
            }
            @Override
            public void onFailure(Call<ModelPortofolio> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(PortfolioStudent.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public boolean getNetworkAvailability() {
        return NetworkCheck.isNetworkAvailable(getApplicationContext());
    }


    public void onDeliverAllPortfolio(List<TrPortofolio> portofolio){

    }


    public void onDeliverPortfolio(TrPortofolio portofolio) {
        portfolioAdapter.addPortfolio(portofolio);
    }


    public void onHideDialog() {
        mDialog.dismiss();
    }

    public class SaveIntoDatabase extends AsyncTask<TrPortofolio, Void, Void> {


        private final String TAG = SaveIntoDatabase.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(TrPortofolio... params) {

            TrPortofolio portofolio = params[0];

            try {
                InputStream stream = new URL(Constants.HTTP.BASE_URL + portofolio.getFoto()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                portofolio.setFoto(bitmap);
                mDatabase.addPortfolio(portofolio);

            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }
    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(PortfolioStudent.this, HomeStudent.class);
        startActivity(a);
        finish();

    }
}