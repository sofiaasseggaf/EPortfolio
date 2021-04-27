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
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListAchievement;
import com.project.eportfolio.adapter.adapterPortfolio.AdapterListKarya;
import com.project.eportfolio.model.achievement.Achievment;
import com.project.eportfolio.model.achievement.ModelAchievement;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.student.InputStudent;
import com.project.eportfolio.student.ProfileStudent;
import com.project.eportfolio.student.outputportfolio.OutputPortfolioA;
import com.project.eportfolio.student.outputportfolio.OutputPortfolioB;
import com.project.eportfolio.utility.OnSwipeTouchListener;
import com.project.eportfolio.utility.PreferenceUtils;
import com.project.eportfolio.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioStudentAchievement extends AppCompatActivity {

    RecyclerView rvAchievement;
    ModelAchievement dataModelAchievement;
    List<Achievment> listAchievement = new ArrayList<>();
    AdapterListAchievement itemList;
    OnSwipeTouchListener onSwipeTouchListener;

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    LinearLayout btn_ll_a, btn_ll_k, btn_ll_p, btn_ll_uk;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_portfolio_achievement);

        rvAchievement = findViewById(R.id.rvAchievement);
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
                Intent a = new Intent(PortfolioStudentAchievement.this, HomeStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, InputStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, ProfileStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, PortfolioStudentKarya.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_uk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, PortfolioStudentUnjukKerja.class);
                startActivity(a);
                finish();
            }
        });

        onSwipeTouchListener = new OnSwipeTouchListener(PortfolioStudentAchievement.this) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                Intent a = new Intent(PortfolioStudentAchievement.this, PortfolioStudentUnjukKerja.class);
                startActivity(a);
                finish();
            }
            public void onSwipeLeft() {

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
                getAchievement();
            }
        }).start();
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
                                Toast.makeText(PortfolioStudentAchievement.this, "Kamu Belum Memiliki Achievement", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                itemList = new AdapterListAchievement(listAchievement);
                                rvAchievement.setLayoutManager(new LinearLayoutManager(PortfolioStudentAchievement.this));
                                rvAchievement.setAdapter(itemList);
                                rvAchievement.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvAchievement,
                                        new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                //Toast.makeText(PortfolioStudentUnjukKerja.this, "this : "+listUnjukKerjaMurid.get(position).getId(), Toast.LENGTH_SHORT).show();
                                                Intent a = new Intent(PortfolioStudentAchievement.this, PortfolioDetail.class);
                                                a.putExtra("idportfolioA", listAchievement.get(position).getId());
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

            }
            @Override
            public void onFailure(Call<ModelAchievement> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(PortfolioStudentAchievement.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(PortfolioStudentAchievement.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}
