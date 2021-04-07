package com.project.eportfolio.teacher.input;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.APIService.AppUtil;
import com.project.eportfolio.R;
import com.project.eportfolio.model.kategoristrategi.ModelKategoriStrategi;
import com.project.eportfolio.model.kategoristrategi.MsKategoristrategi;
import com.project.eportfolio.model.kelas.ModelKelas;
import com.project.eportfolio.model.kelas.MsKela;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPostPortfolio;
import com.project.eportfolio.model.rubrik.ModelMasterRubrik;
import com.project.eportfolio.model.rubrik.MsRubrik;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.teacher.HomeTeacher;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.teacher.ProfileTeacher;
import com.project.eportfolio.utility.FileCompressor;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputTeacherA extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile, btn_simpandanlanjutkan;
    ImageButton btn_input_pp, btn_input_ds, btn_input_r, btn_input_n;
    Spinner sp_strategi;
    EditText txtJudul;
    TextView txtkategori;
    int nilai;
    String kat, inputNamaKelas;
    String inputIdKategori, inputIdStrategi;

    ModelKategoriStrategi dataModelKategoriStrategi;
    List<MsKategoristrategi> listKategoriStrategi = new ArrayList<>();

    ModelStrategi dataModelStrategi;
    List<MsStrategi> listStrategi = new ArrayList<>();

    TextView txtload;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAnimation();
        setContentView(R.layout.teacher_input_a);

        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btn_simpandanlanjutkan = findViewById(R.id.btn_simpandanlanjutkan);

        /*
        btn_input_pp = findViewById(R.id.btn_input_pp);
        btn_input_ds = findViewById(R.id.btn_input_ds);
        btn_input_r = findViewById(R.id.btn_input_r);
        btn_input_n = findViewById(R.id.btn_input_n);
*/

        txtkategori = findViewById(R.id.txtkategori);
        sp_strategi = findViewById(R.id.sp_strategi);
        txtJudul = findViewById(R.id.txtJudulKd);
        txtload = findViewById(R.id.textloading);

        first();

        if (listStrategi!=null){
            try {
                sp_strategi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (listStrategi!=null){
                            for (int a=0; a<listStrategi.size(); a++){
                                if (listStrategi.get(a).getNameStrategi().equalsIgnoreCase(String.valueOf(listStrategi.get(position).getNameStrategi()))) {
                                    inputIdKategori = listStrategi.get(a).getKategoriid();
                                    inputIdStrategi = listStrategi.get(a).getIdStrategi();
                                    break;
                                }
                            }
                        }

                        if (!inputIdKategori.equalsIgnoreCase("") || inputIdKategori!=null){
                            try {
                                for(int i=0; i<dataModelKategoriStrategi.getTotal(); i++){
                                    if (dataModelKategoriStrategi.getData().getMsKategoristrategi().get(i).getIdKategori().equalsIgnoreCase(inputIdKategori)) {
                                        kat = dataModelKategoriStrategi.getData().getMsKategoristrategi().get(i).getNameKategori();
                                        // listKategoriStrategi.add(dataModelKategoriStrategi.getData().getMsKategoristrategi().get(i));
                                        break;
                                    }
                                }
                            } catch (Exception a){ }
                        }

                        txtkategori.setText(kat);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (Exception a){ }

        }

        if(!txtJudul.getText().toString().equalsIgnoreCase("")){
            btn_simpandanlanjutkan.setBackgroundResource(R.drawable.btn_simpandanlanjutkan_active);
        }

        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keMaster();
            }
        });
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keProfile();
            }
        });
        btn_simpandanlanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtJudul.getText().toString().equalsIgnoreCase("")){
                    Intent a = new Intent(InputTeacherA.this, InputTeacherB.class);
                    a.putExtra("inputIdKategori", inputIdKategori);
                    a.putExtra("inputIdStrategi", inputIdStrategi);
                    a.putExtra("inputTxtJudul", txtJudul.getText().toString());
                    startActivity(a);
                    finish();
                } else {
                    Toast.makeText(InputTeacherA.this, "Isi Judul KD !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            int count = 0;

            @Override
            public void run() {
                count++;

                if (count == 1)
                {
                    txtload.setText("Loading .");
                }
                else if (count == 2)
                {
                    txtload.setText("Loading . .");
                }
                else if (count == 3)
                {
                    txtload.setText("Loading . . .");
                }

                if (count == 3)
                    count = 0;

                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getStrategi();
            }
        }).start();
    }


    // =================== GET DATA ================


    public void getStrategi() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelStrategi> dataSiswax = apiInterface.getDataStrategi(  apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelStrategi>() {
            @Override
            public void onResponse(Call<ModelStrategi> call, Response<ModelStrategi> response) {
                dataModelStrategi = response.body();
                if (response.body()!=null){
                    listStrategi.clear();
                    for (int i = 0; i < dataModelStrategi.getData().getMsStrategi().size(); i++) {
                        listStrategi.add(dataModelStrategi.getData().getMsStrategi().get(i));
                    }
                    getKategoriStrategi();
                }
            }
            @Override
            public void onFailure(Call<ModelStrategi> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacherA.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getKategoriStrategi(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKategoriStrategi> dataSiswax = apiInterface.getDataKategoriStrategi( apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelKategoriStrategi>() {
            @Override
            public void onResponse(Call<ModelKategoriStrategi> call, Response<ModelKategoriStrategi> response) {
                dataModelKategoriStrategi = response.body();
                if (response.body()!=null){
                    listKategoriStrategi.clear();
                    /*for (int i = 0; i < dataModelKategoriStrategi.getData().getMsKategoristrategi().size(); i++) {
                        listKategoriStrategi.add(dataModelKategoriStrategi.getData().getMsKategoristrategi().get(i));
                    }*/
                    setSpinnerStrategi();
                }
            }
            @Override
            public void onFailure(Call<ModelKategoriStrategi> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacherA.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void setSpinnerStrategi(){

        ArrayAdapter<MsStrategi> adapter_strategi = new ArrayAdapter<MsStrategi>(
                InputTeacherA.this,
                android.R.layout.simple_spinner_dropdown_item,
                listStrategi)
        {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView)v).setText(String.valueOf(listStrategi.get(position).getNameStrategi()));
                return v;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView)v).setText(String.valueOf(listStrategi.get(position).getNameStrategi()));
                return v;
            }
        };

        adapter_strategi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_strategi.setAdapter(adapter_strategi);

    }

    public void keProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherA.this, ProfileTeacher.class);
                        startActivity(a);
                        finish();
                        /*if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }*/
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void keMaster() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherA.this, MasterTeacher.class);
                        startActivity(a);
                        finish();
                       /* if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }*/
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherA.this, HomeTeacher.class);
                        startActivity(a);
                        finish();
                        /*if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }*/
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
