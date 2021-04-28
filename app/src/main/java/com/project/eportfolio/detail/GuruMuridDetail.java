package com.project.eportfolio.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterGuru;
import com.project.eportfolio.adapter.adapterMaster.AdapterMasterSiswa;
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.teacher.master.DataGuru;
import com.project.eportfolio.teacher.master.DataMurid;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruMuridDetail extends AppCompatActivity {

    ModelGuru modelDataGuru;
    MsGuru guru;
    ModelSiswa modelDataSiswa;
    MsMurid murid;

    ImageView imgDeskDataGuru;
    TextView namaDeskDataGuru, sekolahDeskDataGuru, alamatDeskDataGuru, tlpDeskDataGuru;

    String idguru, idmurid;
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_detailgurumurid);

        Intent intent = getIntent();
        idguru = intent.getStringExtra("idguru");
        idmurid = intent.getStringExtra("idmurid");

        first();

        imgDeskDataGuru = findViewById(R.id.imgDeskDataGuru);
        namaDeskDataGuru = findViewById(R.id.namaDeskDataGuru);
        sekolahDeskDataGuru = findViewById(R.id.sekolahDeskDataGuru);
        alamatDeskDataGuru = findViewById(R.id.alamatDeskDataGuru);
        tlpDeskDataGuru = findViewById(R.id.tlpDeskDataGuru);

    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getGuru();
            }
        }).start();
    }

    public void getGuru() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelGuru> dataSiswax = apiInterface.getdataGuru( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelGuru>() {
            @Override
            public void onResponse(Call<ModelGuru> call, Response<ModelGuru> response) {
                modelDataGuru = response.body();

                if (modelDataGuru!=null){
                    for (int i = 0; i < modelDataGuru.getTotal(); i++) {
                        try{
                            if (idguru.equalsIgnoreCase(modelDataGuru.getData().getMsGuru().get(i).getIdGuru())) {
                                guru = modelDataGuru.getData().getMsGuru().get(i);
                            }
                        } catch (Exception e){

                        }
                    }

                    if (guru==null){
                        getSiswa();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setDataGuru();
                            }
                        });
                    }

                }
            }
            @Override
            public void onFailure(Call<ModelGuru> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GuruMuridDetail.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getSiswa() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSiswa> dataSiswax = apiInterface.getDataSiswa( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelSiswa>() {
            @Override
            public void onResponse(Call<ModelSiswa> call, Response<ModelSiswa> response) {
                modelDataSiswa = response.body();
                if (modelDataSiswa!=null){
                    for (int i = 0; i < modelDataSiswa.getTotal(); i++) {
                        try {
                            if (idmurid.equalsIgnoreCase(modelDataSiswa.getData().getMsMurid().get(i).getId())) {
                                murid = modelDataSiswa.getData().getMsMurid().get(i);
                            }
                        } catch (Exception e){
                        }
                    }

                    if (murid!=null){
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
            public void onFailure(Call<ModelSiswa> call, Throwable t) {

                Toast.makeText(GuruMuridDetail.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setDataGuru(){

        try{
            Picasso.get().load(guru.getPhoto()).into(imgDeskDataGuru);
        } catch (Exception e){
            e.printStackTrace();
        }

        namaDeskDataGuru.setText(guru.getFirstname()+" "+guru.getMidname()+" "+guru.getLastname());
        sekolahDeskDataGuru.setText(PreferenceUtils.getSekolahNama(getApplicationContext()));
        alamatDeskDataGuru.setText(guru.getAddress());
        tlpDeskDataGuru.setText(guru.getPhone());
    }

    public void setDataSiswa(){

        try{
            Picasso.get().load(murid.getPhoto()).into(imgDeskDataGuru);
        } catch (Exception e){
            e.printStackTrace();
        }

        namaDeskDataGuru.setText(murid.getFirstname()+" "+murid.getMidname()+" "+murid.getLastname());
        sekolahDeskDataGuru.setText(PreferenceUtils.getSekolahNama(getApplicationContext()));
        alamatDeskDataGuru.setText(murid.getAddress());
        tlpDeskDataGuru.setText(murid.getPhone());
    }

}
