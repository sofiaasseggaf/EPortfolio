package com.project.eportfolio.student.portfolio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListUnjukKerja;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.student.InputStudent;
import com.project.eportfolio.student.ProfileStudent;
import com.project.eportfolio.utility.Constants;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioStudentUnjukKerja extends AppCompatActivity implements AdapterListUnjukKerja.ClickLIstenerUnjukKerja, View.OnClickListener {

    RecyclerView rvunjukkerja;
    ModelPortofolio dataModelPortfolio;
    ModelStrategi dataModelStrategi;
    List<TrPortofolio> listPortofolio = new ArrayList<>();
    List<TrPortofolio> listUnjukKerjaMurid = new ArrayList<>();
    List<MsStrategi> listUnjukKerja = new ArrayList<>();
    AdapterListUnjukKerja itemList;

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    LinearLayout btn_ll_a, btn_ll_k, btn_ll_p, btn_ll_uk;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_portfolio_unjuk_kerja);

        rvunjukkerja = findViewById(R.id.rvUnjukKerja);
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
                Intent a = new Intent(PortfolioStudentUnjukKerja.this, HomeStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentUnjukKerja.this, InputStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentUnjukKerja.this, ProfileStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentUnjukKerja.this, PortfolioStudentAchievement.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentUnjukKerja.this, PortfolioStudentKarya.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentUnjukKerja.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();
            }
        });

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
                        if (dataModelStrategi.getData().getMsStrategi().get(i).getKategoriid().equalsIgnoreCase("1")){
                            listUnjukKerja.add(dataModelStrategi.getData().getMsStrategi().get(i));
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
                        Toast.makeText(PortfolioStudentUnjukKerja.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(PortfolioStudentUnjukKerja.this, "Kamu Tidak Memiliki Portofolio Unjuk Kerja", Toast.LENGTH_SHORT).show();

                    } else {
                        for(int i=0; i<listPortofolio.size(); i++){
                            try {
                                if (listPortofolio.get(i).getIdkategori().equalsIgnoreCase("1")){
                                    listUnjukKerjaMurid.add(listPortofolio.get(i));
                                }

                            }catch (Exception e){

                            }
                        }
                    }

                    if (listUnjukKerjaMurid!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterListUnjukKerja(PortfolioStudentUnjukKerja.this, listUnjukKerjaMurid);
                                rvunjukkerja.setLayoutManager(new LinearLayoutManager(PortfolioStudentUnjukKerja.this));
                                rvunjukkerja.setAdapter(itemList);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(PortfolioStudentUnjukKerja.this, "Tidak Memiliki Unjuk Kerja", Toast.LENGTH_SHORT).show();


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
                        Toast.makeText(PortfolioStudentUnjukKerja.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });


    }



    @Override
    public void onBackPressed() {
        Intent a = new Intent(PortfolioStudentUnjukKerja.this, HomeStudent.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onClick(int position) {
        TrPortofolio selectedPortfolio = itemList.getSelectedPortfolio(position);
        Intent intent = new Intent(PortfolioStudentUnjukKerja.this, PortfolioDetail.class);
        intent.putExtra(Constants.REFERENCE.PORTFOLIO, (Parcelable) selectedPortfolio);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
}
