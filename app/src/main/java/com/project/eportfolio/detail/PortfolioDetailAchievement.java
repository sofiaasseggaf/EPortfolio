package com.project.eportfolio.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.model.achievement.Achievment;
import com.project.eportfolio.model.achievement.ModelAchievement;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioDetailAchievement extends AppCompatActivity {

    ModelAchievement modelDataAchievement;
    Achievment achievment;

    ImageView imgDetailPortfolioA;
    TextView judulDetailPortfolioA, narasiDetailPortfolioA;
    TextView tglDetailPortfolioA;

    String idportfolioA;
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_portfoliodetailachievement);

        Intent intent = getIntent();
        idportfolioA = intent.getStringExtra("idportfolioA");

        first();

        imgDetailPortfolioA = findViewById(R.id.imgDetailPortfolioA);
        judulDetailPortfolioA = findViewById(R.id.judulDetailPortfolioA);
        narasiDetailPortfolioA = findViewById(R.id.narasiDetailPortfolioA);
        tglDetailPortfolioA = findViewById(R.id.tglDetailPortfolioA);

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

                modelDataAchievement = response.body();

                    if (modelDataAchievement!=null){
                        for (int i = 0; i < modelDataAchievement.getTotal(); i++) {
                            try {
                                if (idportfolioA.equalsIgnoreCase(modelDataAchievement.getData().getAchievment().get(i).getId())) {
                                    achievment = modelDataAchievement.getData().getAchievment().get(i);
                                }
                            } catch (Exception e){

                            }
                        }

                        if (achievment!=null){
                            setDataAchievement();
                        }
                    }




            }
            @Override
            public void onFailure(Call<ModelAchievement> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(PortfolioDetailAchievement.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void setDataAchievement(){

        try{
            Picasso.get().load(achievment.getFoto().toString()).into(imgDetailPortfolioA);
        } catch (Exception e){
            e.printStackTrace();
        }

        judulDetailPortfolioA.setText(achievment.getJudul());
        narasiDetailPortfolioA.setText(achievment.getNarasi());
        tglDetailPortfolioA.setText(achievment.getTanggal());
    }
}
