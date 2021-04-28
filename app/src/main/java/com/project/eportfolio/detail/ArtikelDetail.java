package com.project.eportfolio.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.ArtikelActivity;
import com.project.eportfolio.R;
import com.project.eportfolio.adapter.adapterArtikel.AdapterArtikel;
import com.project.eportfolio.model.blog.Blog;
import com.project.eportfolio.model.blog.ModelBlog;
import com.project.eportfolio.utility.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtikelDetail extends AppCompatActivity {

    ModelBlog modelDataBlog;
    Blog artikel;

    ImageView img_detail_atrikel;
    TextView txt_detail_judulartikel, txt_detail_isiartikel, txt_detail_authorartikel, txt_detail_tanggalartikel, txt_detail_kategoriartikel;

    String idartikel;
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_detail_article);

        Intent intent = getIntent();
        idartikel = intent.getStringExtra("idartikel");

        first();

        img_detail_atrikel = findViewById(R.id.img_detail_atrikel);
        txt_detail_judulartikel = findViewById(R.id.txt_detail_judulartikel);
        txt_detail_isiartikel = findViewById(R.id.txt_detail_isiartikel);
        txt_detail_authorartikel = findViewById(R.id.txt_detail_authorartikel);
        txt_detail_tanggalartikel = findViewById(R.id.txt_detail_tanggalartikel);
        //txt_detail_kategoriartikel = findViewById(R.id.txt_detail_kategoriartikel);
    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getArtikel();
            }
        }).start();
    }

    public void getArtikel() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelBlog> dataBlog = apiInterface.getDataBlog( apikey, 1000);

        dataBlog.enqueue(new Callback<ModelBlog>() {
            @Override
            public void onResponse(Call<ModelBlog> call, Response<ModelBlog> response) {
                modelDataBlog = response.body();
                if (modelDataBlog!=null){
                    for (int i = 0; i < modelDataBlog.getTotal(); i++) {
                        try {
                            if (idartikel.equalsIgnoreCase(modelDataBlog.getData().getBlog().get(i).getId())) {
                                artikel = modelDataBlog.getData().getBlog().get(i);
                            }
                        } catch (Exception e){

                        }
                    }

                    if (artikel!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setDataArtikel();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelBlog> call, Throwable t) {

                Toast.makeText(ArtikelDetail.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
    public void setDataArtikel(){

        try{
            Picasso.get().load("https://eportofolio.id/uploads/blog/"+artikel.getImage().toString()).into(img_detail_atrikel);
        } catch (Exception e){
            e.printStackTrace();
        }

        txt_detail_judulartikel.setText(artikel.getTitle());
        txt_detail_isiartikel.setText(artikel.getContent());
        txt_detail_authorartikel.setText(artikel.getAuthor());
        txt_detail_tanggalartikel.setText(artikel.getCreatedAt());
        //txt_detail_kategoriartikel.setText("Kategori : "+artikel.getCategory());
    }
}