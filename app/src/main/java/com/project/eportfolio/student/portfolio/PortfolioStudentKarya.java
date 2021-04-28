package com.project.eportfolio.student.portfolio;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListKarya;
import com.project.eportfolio.detail.PortfolioDetail;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.student.InputStudent;
import com.project.eportfolio.student.ProfileStudent;
import com.project.eportfolio.utility.OnSwipeTouchListener;
import com.project.eportfolio.utility.PreferenceUtils;
import com.project.eportfolio.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioStudentKarya extends AppCompatActivity {

    RecyclerView rvKarya;
    ModelPortofolio dataModelPortfolio;
    ModelStrategi dataModelStrategi;
    ModelMataPelajaran modelDataMapel;
    List<MsMatapelajaran> listMapel = new ArrayList<>();
    List<TrPortofolio> listPortofolio = new ArrayList<>();
    List<TrPortofolio> listKaryaMurid = new ArrayList<>();
    List<MsStrategi> listKarya = new ArrayList<>();
    AdapterListKarya itemList;
    OnSwipeTouchListener onSwipeTouchListener;

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    LinearLayout btn_ll_a, btn_ll_k, btn_ll_p, btn_ll_uk;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_portfolio_karya);

        rvKarya = findViewById(R.id.rvKarya);
        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btn_ll_a = findViewById(R.id.btn_ll_a);
        btn_ll_k = findViewById(R.id.btn_ll_k);
        btn_ll_p = findViewById(R.id.btn_ll_p);
        btn_ll_uk = findViewById(R.id.btn_ll_uk);

        first();

        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentKarya.this, HomeStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentKarya.this, InputStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentKarya.this, ProfileStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentKarya.this, PortfolioStudentAchievement.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentKarya.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_uk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentKarya.this, PortfolioStudentUnjukKerja.class);
                startActivity(a);
                finish();
            }
        });

        onSwipeTouchListener = new OnSwipeTouchListener(PortfolioStudentKarya.this) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                Intent a = new Intent(PortfolioStudentKarya.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();
            }
            public void onSwipeLeft() {
                Intent a = new Intent(PortfolioStudentKarya.this, PortfolioStudentUnjukKerja.class);
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

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
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
                            listKarya.add(dataModelStrategi.getData().getMsStrategi().get(i));
                        }
                    }
                    getMapel();
                }
            }
            @Override
            public void onFailure(Call<ModelStrategi> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(PortfolioStudentKarya.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
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
                        getPortfolio();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelMataPelajaran> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PortfolioStudentKarya.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                        } catch (Exception e){

                        }
                    }

                    if(listPortofolio==null){
                        Toast.makeText(PortfolioStudentKarya.this, "Kamu Tidak Memiliki Portofolio Karya", Toast.LENGTH_SHORT).show();

                    } else {
                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("3")){
                                    listKaryaMurid.add(listPortofolio.get(i));
                                }

                            }catch (Exception e){

                            }
                        }
                    }

                    if (listKaryaMurid!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterListKarya(listKaryaMurid, listMapel );
                                rvKarya.setLayoutManager(new LinearLayoutManager(PortfolioStudentKarya.this));
                                rvKarya.setAdapter(itemList);
                                rvKarya.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvKarya,
                                        new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                //Toast.makeText(PortfolioStudentUnjukKerja.this, "this : "+listUnjukKerjaMurid.get(position).getId(), Toast.LENGTH_SHORT).show();
                                                Intent a = new Intent(PortfolioStudentKarya.this, PortfolioDetail.class);
                                                a.putExtra("idportfolio", listKaryaMurid.get(position).getId());
                                                startActivity(a);
                                            }

                                            @Override
                                            public void onLongItemClick(View view, int position) {

                                            }
                                        }));
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(PortfolioStudentKarya.this, "Tidak Memiliki Karya", Toast.LENGTH_SHORT).show();


                               /* try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Karya.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(Karya.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }*/

                            }
                        });
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
                        Toast.makeText(PortfolioStudentKarya.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });


    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(PortfolioStudentKarya.this, HomeStudent.class);
        startActivity(a);
        finish();
    }

}
