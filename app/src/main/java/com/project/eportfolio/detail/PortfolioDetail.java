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
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioDetail extends AppCompatActivity {

    ModelPortofolio modelDataPortfolio;
    TrPortofolio portfolio;
    ModelMataPelajaran modelDataMapel;
    MsMatapelajaran mapel;
    ModelStrategi dataModelStrategi;
    MsStrategi strategi;
    TextView txtload;

    ImageView imgDetailPortfolio;
    TextView judulDetailPortfolio, narasiDetailPortfolio, strategiDetailPortfolio, nilaiDetailPortfolio;
    TextView mapelDetailPortfolio, tglDetailPortfolio, predikatDetailPortfolio;

    String idportfolio;
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_portfoliodetail);

        Intent intent = getIntent();
        idportfolio = intent.getStringExtra("idportfolio");

        first();

        imgDetailPortfolio = findViewById(R.id.imgDetailPortfolio);
        judulDetailPortfolio = findViewById(R.id.judulDetailPortfolio);
        narasiDetailPortfolio = findViewById(R.id.narasiDetailPortfolio);
        strategiDetailPortfolio = findViewById(R.id.strategiDetailPortfolio);
        nilaiDetailPortfolio = findViewById(R.id.nilaiDetailPortfolio);
        mapelDetailPortfolio = findViewById(R.id.mapelDetailPortfolio);
        tglDetailPortfolio = findViewById(R.id.tglDetailPortfolio);
        predikatDetailPortfolio = findViewById(R.id.predikatDetailPortfolio);

    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getPortfolio();
            }
        }).start();
    }

    public void getPortfolio() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPortofolio> dataSiswax = apiInterface.getDataPortfolio(  apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelPortofolio>() {
            @Override
            public void onResponse(Call<ModelPortofolio> call, Response<ModelPortofolio> response) {
                modelDataPortfolio = response.body();
                if (modelDataPortfolio!=null){
                    for (int i = 0; i < modelDataPortfolio.getTotal(); i++) {
                        try {
                            if (idportfolio.equalsIgnoreCase(modelDataPortfolio.getData().getTrPortofolio().get(i).getId())) {
                                portfolio = modelDataPortfolio.getData().getTrPortofolio().get(i);
                            }
                        } catch (Exception e){

                        }
                    }

                    if (portfolio!=null){
                        getMapel();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelPortofolio> call, Throwable t) {

                Toast.makeText(PortfolioDetail.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                            if (portfolio.getMapelid().equalsIgnoreCase(modelDataMapel.getData().getMsMatapelajaran().get(i).getId())) {
                                mapel = modelDataMapel.getData().getMsMatapelajaran().get(i);
                            }
                        } catch (Exception e){

                        }
                    }

                    if (mapel!=null){
                        getStrategi();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelMataPelajaran> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PortfolioDetail.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getStrategi() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelStrategi> dataSiswax = apiInterface.getDataStrategi(  apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelStrategi>() {
            @Override
            public void onResponse(Call<ModelStrategi> call, Response<ModelStrategi> response) {
                dataModelStrategi = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < dataModelStrategi.getData().getMsStrategi().size(); i++) {
                        try {
                            if (portfolio.getStrategiid().equalsIgnoreCase(dataModelStrategi.getData().getMsStrategi().get(i).getIdStrategi())) {
                                strategi = dataModelStrategi.getData().getMsStrategi().get(i);
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                if (strategi!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            setDataPortfolio();
                        }
                    });
                }

            }
            @Override
            public void onFailure(Call<ModelStrategi> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PortfolioDetail.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void setDataPortfolio(){

        try{
            Picasso.get().load(portfolio.getFoto().toString()).into(imgDetailPortfolio);
        } catch (Exception e){
            e.printStackTrace();
        }

        judulDetailPortfolio.setText(portfolio.getJudulKd());
        narasiDetailPortfolio.setText(portfolio.getNarasi());
        strategiDetailPortfolio.setText(strategi.getNameStrategi());
        nilaiDetailPortfolio.setText(portfolio.getNilai());
        mapelDetailPortfolio.setText(mapel.getName());
        tglDetailPortfolio.setText(portfolio.getTanggal());
        predikatDetailPortfolio.setText(portfolio.getPredikat());
    }
}
