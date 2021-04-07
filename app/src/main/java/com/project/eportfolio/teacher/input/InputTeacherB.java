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

public class InputTeacherB extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile, btn_simpandanlanjutkan;
    Spinner sp_mapel, sp_kelas, sp_siswa, sp_semester, sp_tahun_ajaran; //sp_kategori
    String inputIdSiswa, inputIdMapel, inputIdKelas, inputNamaKelas;
    String inputIdStrategi, inputIdKategori, inputTxtJudul;

    ModelKelas dataModelKelas;
    List<MsKela> listkelas = new ArrayList<>();

    ModelMataPelajaran dataMasterMapel;
    List<MsMatapelajaran> listmapel = new ArrayList<>();

    ModelSiswa dataModelSiswa;
    List<MsMurid> listSiswa = new ArrayList<>();
    List<MsMurid> listSiswa2 = new ArrayList<>();

    TextView txtload;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAnimation();
        setContentView(R.layout.teacher_input_b);

        Intent intent = getIntent();
        inputIdStrategi = intent.getStringExtra("inputIdStrategi");
        inputIdKategori = intent.getStringExtra("inputIdKategori");
        inputTxtJudul = intent.getStringExtra("inputTxtJudul");


        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btn_simpandanlanjutkan = findViewById(R.id.btn_simpandanlanjutkan);

        sp_kelas = findViewById(R.id.sp_kelas);
        sp_siswa = findViewById(R.id.sp_siswa);
        sp_mapel = findViewById(R.id.sp_mapel);
        sp_tahun_ajaran = findViewById(R.id.sp_tahun_ajaran);
        sp_semester = findViewById(R.id.sp_semester);
        txtload = findViewById(R.id.textloading);

        first();

        if (listkelas!=null){
            try {
                sp_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        listSiswa2.clear();
                        if (listkelas!=null){
                            for (int a=0; a<listkelas.size(); a++){
                                if (listkelas.get(a).getName().equalsIgnoreCase(String.valueOf(listkelas.get(position).getName()))){
                                    inputIdKelas = listkelas.get(a).getId();
                                    inputNamaKelas = listkelas.get(a).getName();
                                    if (!inputIdKelas.equalsIgnoreCase("") || inputIdKelas!=null){
                                        try{
                                            for(int i=0; i<listSiswa.size(); i++){
                                                if (listSiswa.get(i).getKelasid().equalsIgnoreCase(inputIdKelas)) {
                                                    listSiswa2.add(listSiswa.get(i));
                                                }
                                            }
                                        } catch (Exception i){ }
                                    }
                                    break;
                                }
                            }
                        }

                        if (listSiswa2!=null){
                            ArrayAdapter<MsMurid> adapterSiswax = new ArrayAdapter<MsMurid>(InputTeacherB.this, android.R.layout.simple_spinner_dropdown_item,listSiswa2)
                            {
                                @Override
                                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                    View v = super.getDropDownView(position, convertView, parent);
                                    ((TextView)v).setText(listSiswa2.get(position).getFirstname()+"  "+listSiswa2.get(position).getLastname());
                                    return v;
                                }

                                @NonNull
                                @Override
                                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                    View v = super.getDropDownView(position, convertView, parent);
                                    ((TextView)v).setText(listSiswa2.get(position).getFirstname()+"  "+listSiswa2.get(position).getLastname());
                                    return v;
                                }
                            };
                            adapterSiswax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_siswa.setAdapter(adapterSiswax);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (Exception a){ }

        }

        if (listSiswa2!=null){
            sp_siswa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (listSiswa2!=null){
                        for (int a=0; a<listSiswa.size(); a++){
                            String siswa = listSiswa2.get(a).getFirstname()+" "+listSiswa2.get(a).getLastname();
                            if (siswa.equalsIgnoreCase(String.valueOf(listSiswa2.get(position).getFirstname()+" "+listSiswa2.get(position).getLastname()))){
                                inputIdSiswa = listSiswa2.get(a).getId();
                                break;
                            }
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        if (listmapel!=null){
            sp_mapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (listmapel!=null){
                        for (int a=0; a<listmapel.size(); a++){
                            if (listmapel.get(a).getName().equalsIgnoreCase(listmapel.get(position).getName())){
                                inputIdMapel = listmapel.get(a).getId();
                                break;
                            }
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        if(inputIdSiswa!=null){
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
                if(inputIdKelas!=null){
                    Intent a = new Intent(InputTeacherB.this, InputTeacherC.class);
                    a.putExtra("inputIdKategori", inputIdKategori);
                    a.putExtra("inputIdStrategi", inputIdStrategi);
                    a.putExtra("inputTxtJudul", inputTxtJudul);
                    //a.putExtra("inputTahunAjaran", sp_tahun_ajaran.getSelectedItem().toString());
                    a.putExtra("inputTahunAjaran", "2021");
                    a.putExtra("inputSemester", sp_semester.getSelectedItem().toString());
                    a.putExtra("inputIdKelas", inputIdKelas);
                    a.putExtra("inputNamaKelas", inputNamaKelas);
                    a.putExtra("inputIdMapel", inputIdMapel);
                    a.putExtra("inputIdSiswa", inputIdSiswa);
                    startActivity(a);
                    finish();
                } else {
                    Toast.makeText(InputTeacherB.this, "Pilih Kelas & Siswa !", Toast.LENGTH_SHORT).show();
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
                getKelas();
            }
        }).start();
    }


    // =================== GET DATA ================


    public void getKelas()  {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKelas> dataSiswax = apiInterface.getdataKelas( apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelKelas>() {
            @Override
            public void onResponse(Call<ModelKelas> call, Response<ModelKelas> response) {
                dataModelKelas = response.body();
                if (response.body()!=null){
                    listkelas.clear();
                    for (int i = 0; i < dataModelKelas.getTotal(); i++) {
                        if (PreferenceUtils.getIdSekolahGuru(getApplicationContext()).equalsIgnoreCase(dataModelKelas.getData().getMsKelas().get(i).getSekolahid())) {
                            listkelas.add(dataModelKelas.getData().getMsKelas().get(i));
                        }
                    }
                    getSiswa();
                }
            }
            @Override
            public void onFailure(Call<ModelKelas> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacherB.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getSiswa() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSiswa> dataSiswax = apiInterface.getDataSiswa(apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelSiswa>() {
            @Override
            public void onResponse(Call<ModelSiswa> call, Response<ModelSiswa> response) {
                dataModelSiswa = response.body();
                if(response.body()!=null){
                    listSiswa.clear();
                    for (int i=0; i<dataModelSiswa.getTotal(); i++){
                        try {
                            if (dataModelSiswa.getData().getMsMurid().get(i).getSekolahid().equalsIgnoreCase(PreferenceUtils.getSekolahId(getApplicationContext()))){
                                listSiswa.add(dataModelSiswa.getData().getMsMurid().get(i));
                            }
                        } catch (Exception a){ }
                    }

                    getMapel();
                }
            }
            @Override
            public void onFailure(Call<ModelSiswa> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacherB.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                dataMasterMapel = response.body();
                if (response.body()!=null){
                    listmapel.clear();
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    setSpinnerKelas();
                }
            }
            @Override
            public void onFailure(Call<ModelMataPelajaran> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacherB.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void setSpinnerKelas(){

        ArrayAdapter<MsKela> adapter_kelas = new ArrayAdapter<MsKela>(
                InputTeacherB.this,
                android.R.layout.simple_spinner_dropdown_item,
                listkelas)
        {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView)v).setText(String.valueOf(listkelas.get(position).getName()));
                return v;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView)v).setText(String.valueOf(listkelas.get(position).getName()));
                return v;
            }
        };
        adapter_kelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_kelas.setAdapter(adapter_kelas);

        setSpinnerMapel();

    }

    private void setSpinnerMapel(){

        for (int i = 0; i < dataMasterMapel.getData().getMsMatapelajaran().size(); i++) {
            if (PreferenceUtils.getIdGuru(getApplicationContext())
                    .equalsIgnoreCase(dataMasterMapel.getData().getMsMatapelajaran().get(i).getGuruid())) {
                listmapel.add(dataMasterMapel.getData().getMsMatapelajaran().get(i));
            }
        }

        ArrayAdapter<MsMatapelajaran> adapter_mapel = new ArrayAdapter<MsMatapelajaran>(
                InputTeacherB.this,
                android.R.layout.simple_spinner_dropdown_item,
                listmapel)
        {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView)v).setText(String.valueOf(listmapel.get(position).getName()));
                return v;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView)v).setText(String.valueOf(listmapel.get(position).getName()));
                return v;
            }
        };
        adapter_mapel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_mapel.setAdapter(adapter_mapel);

    }

    public void keProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherB.this, ProfileTeacher.class);
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
                        Intent a = new Intent(InputTeacherB.this, MasterTeacher.class);
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
                        Intent a = new Intent(InputTeacherB.this, HomeTeacher.class);
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
