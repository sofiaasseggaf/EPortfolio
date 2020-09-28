package com.project.eportfolio.teacher;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

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
import com.project.eportfolio.model.portfolio.ModelPostPortofolio;
import com.project.eportfolio.model.rubrik.ModelMasterRubrik;
import com.project.eportfolio.model.rubrik.MsRubrik;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class InputTeacher extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile;
    Button btnInputPortfolio, btnOpenCamera;
    Spinner sp_mapel, sp_strategi, sp_kelas, sp_siswa, sp_kategori, sp_semester;
    EditText txtJudul, txtTahunAjaran, txtNarasi, txtPredikat, txtNilai;
    TextView txtPoint4, txtPoint3, txtPoint2, txtPoint1;
    RadioButton rbPoint4, rbPoint3, rbPoint2, rbPoint1;
    ImageView imgPortofolio;
    String pathToFile;
    Bitmap foto;
    File photoFile;
    String deskRubrik1, idRubrik1, deskRubrik2, idRubrik2, deskRubrik3, idRubrik3, deskRubrik4, idRubrik4;
    String inputIdSiswa, inputIdMapel, inputIdKelas, inputIdKategori, inputIdStrategi, inputIdRubrik, inputDeskRubrik;


    ModelKelas dataModelKelas;
    List<MsKela> listkelas = new ArrayList<>();

    ModelMataPelajaran dataMasterMapel;
    List<MsMatapelajaran> listmapel = new ArrayList<>();

    ModelKategoriStrategi dataModelKategoriStrategi;
    List<MsKategoristrategi> listKategoriStrategi = new ArrayList<>();

    ModelStrategi dataModelStrategi;
    List<MsStrategi> listStrategi = new ArrayList<>();

    ModelSiswa dataModelSiswa;
    List<MsMurid> listSiswa = new ArrayList<>();

    ModelMasterRubrik dataMasterRubrik;
    List<MsRubrik> listRubrik = new ArrayList<>();


    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_input);

        if (Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }


        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btnInputPortfolio = findViewById(R.id.btnInputPortfolio);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);

        sp_kelas = findViewById(R.id.sp_kelas);
        sp_siswa = findViewById(R.id.sp_siswa);
        sp_mapel = findViewById(R.id.sp_mapel);
        txtTahunAjaran = findViewById(R.id.txtTahunAjaran);
        sp_semester = findViewById(R.id.sp_semester);
        sp_kategori = findViewById(R.id.sp_kategori);
        sp_strategi = findViewById(R.id.sp_strategi);
        txtJudul = findViewById(R.id.txtJudul);
        rbPoint1 = findViewById(R.id.rbPoint1);
        rbPoint2 = findViewById(R.id.rbPoint2);
        rbPoint3 = findViewById(R.id.rbPoint3);
        rbPoint4 = findViewById(R.id.rbPoint4);
        txtPoint4 = findViewById(R.id.txtPoint4);
        txtPoint3 = findViewById(R.id.txtPoint3);
        txtPoint2 = findViewById(R.id.txtPoint2);
        txtPoint1 = findViewById(R.id.txtPoint1);
        txtNarasi= findViewById(R.id.txtNarasi);
        txtPredikat = findViewById(R.id.txtPredikat);
        txtNilai = findViewById(R.id.txtNilai);
        imgPortofolio = findViewById(R.id.imgPortofolio);


        first();

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
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        btnInputPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendDataPortfolio();
                    }
                }).start();
            }
        });

    }

    public void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getKelas();
            }
        }).start();
    }

    public void getKelas()  {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKelas> dataSiswax = apiInterface.getdataKelas( apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelKelas>() {
            @Override
            public void onResponse(Call<ModelKelas> call, Response<ModelKelas> response) {
                dataModelKelas = response.body();
                if (response.body()!=null){
                    listkelas.clear();
                    for (int i = 0; i < dataModelKelas.getData().getMsKelas().size(); i++) {
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
                        Toast.makeText(InputTeacher.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                    getMapel();
                }
            }
            @Override
            public void onFailure(Call<ModelSiswa> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacher.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                    getKategoriStrategi();
                }
            }
            @Override
            public void onFailure(Call<ModelMataPelajaran> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacher.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                    for (int i = 0; i < dataModelKategoriStrategi.getData().getMsKategoristrategi().size(); i++) {
                        listKategoriStrategi.add(dataModelKategoriStrategi.getData().getMsKategoristrategi().get(i));
                    }
                    getStrategi();
                }
            }
            @Override
            public void onFailure(Call<ModelKategoriStrategi> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacher.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                    listStrategi.clear();
                    getRubrik();
                }
            }
            @Override
            public void onFailure(Call<ModelStrategi> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacher.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getRubrik() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelMasterRubrik> dataSiswax = apiInterface.getDataMasterRubrik(  apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelMasterRubrik>() {
            @Override
            public void onResponse(Call<ModelMasterRubrik> call, Response<ModelMasterRubrik> response) {
                dataMasterRubrik = response.body();
                if (response.body()!=null){
                    listRubrik.clear();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            setSpinnerKelas();
                            setSpinnerMapel();
                            setSpinnerKategori();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelMasterRubrik> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacher.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void setSpinnerKelas(){

        ArrayAdapter<MsKela> adapter_kelas = new ArrayAdapter<MsKela>(
                InputTeacher.this,
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

        sp_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int a=0; a<listkelas.size(); a++){
                    if (listkelas.get(a).getName().equalsIgnoreCase(sp_kelas.getSelectedItem().toString())){
                        inputIdKelas = listkelas.get(a).getId();
                        for(int i=0; i<dataModelSiswa.getData().getMsMurid().size(); i++){
                            if (dataModelSiswa.getData().getMsMurid().get(i).getKelasid().equalsIgnoreCase(inputIdKelas)) {
                                listSiswa.add(dataModelSiswa.getData().getMsMurid().get(i));
                            }
                        }
                    }
                }


                ArrayAdapter<MsMurid> adapterSiswax = new ArrayAdapter<MsMurid>(InputTeacher.this, android.R.layout.simple_spinner_dropdown_item,listSiswa)
                {
                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View v = super.getDropDownView(position, convertView, parent);
                        ((TextView)v).setText(listSiswa.get(position).getFirstname()+"  "+listSiswa.get(position).getLastname());
                        return v;
                    }

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View v = super.getDropDownView(position, convertView, parent);
                        ((TextView)v).setText(listSiswa.get(position).getFirstname()+"  "+listSiswa.get(position).getLastname());
                        return v;
                    }
                };
                adapterSiswax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_siswa.setAdapter(adapterSiswax);

                sp_siswa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for (int a=0; a<listSiswa.size(); a++){
                            String siswa = listSiswa.get(a).getFirstname()+" "+listSiswa.get(a).getLastname();
                            if (siswa.equalsIgnoreCase(sp_siswa.getSelectedItem().toString())){
                                inputIdSiswa = listSiswa.get(a).getId();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerMapel(){

        for (int i = 0; i < dataMasterMapel.getData().getMsMatapelajaran().size(); i++) {
            if (PreferenceUtils.getIdGuru(getApplicationContext())
                    .equalsIgnoreCase(dataMasterMapel.getData().getMsMatapelajaran().get(i).getGuruid())) {
                listmapel.add(dataMasterMapel.getData().getMsMatapelajaran().get(i));
            }
        }

        ArrayAdapter<MsMatapelajaran> adapter_mapel = new ArrayAdapter<MsMatapelajaran>(
                InputTeacher.this,
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

        sp_mapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int a=0; a<listmapel.size(); a++){
                    if (listmapel.get(a).getName().equalsIgnoreCase(sp_mapel.getSelectedItem().toString())){
                        inputIdSiswa = listSiswa.get(a).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerKategori(){

        ArrayAdapter<MsKategoristrategi> adapter_kategori = new ArrayAdapter<MsKategoristrategi>(
                InputTeacher.this,
                android.R.layout.simple_spinner_dropdown_item,
                listKategoriStrategi)
        {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView)v).setText(String.valueOf(listKategoriStrategi.get(position).getNameKategori()));
                return v;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView)v).setText(String.valueOf(listKategoriStrategi.get(position).getNameKategori()));
                return v;
            }
        };
        adapter_kategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_kategori.setAdapter(adapter_kategori);

        sp_kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int a=0; a<listKategoriStrategi.size(); a++){
                    if (listKategoriStrategi.get(a).getNameKategori().equalsIgnoreCase(sp_kategori.getSelectedItem().toString())){
                        inputIdKategori = listKategoriStrategi.get(a).getIdKategori();
                        for(int i=0; i<dataModelStrategi.getData().getMsStrategi().size(); i++){
                            if (dataModelStrategi.getData().getMsStrategi().get(i).getKategoriid().equalsIgnoreCase(inputIdKategori)) {
                                listStrategi.add(dataModelStrategi.getData().getMsStrategi().get(i));
                            }
                        }
                    }
                }


                ArrayAdapter<MsStrategi> adapter_strategi = new ArrayAdapter<MsStrategi>(
                        InputTeacher.this,
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

                sp_strategi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for (int a=0; a<listStrategi.size(); a++){
                            if (listStrategi.get(a).getNameStrategi().equalsIgnoreCase(sp_strategi.getSelectedItem().toString())){
                                inputIdStrategi = listStrategi.get(a).getIdStrategi();
                                for(int i=0; i<dataMasterRubrik.getData().getMsRubrik().size(); i++){
                                    if (dataMasterRubrik.getData().getMsRubrik().get(i).getStrategiid().equalsIgnoreCase(inputIdStrategi)) {
                                        listRubrik.add(dataMasterRubrik.getData().getMsRubrik().get(i));
                                    }
                                }
                            }
                        }

                        if (listRubrik!=null){

                            for (int x=0; x<listRubrik.size(); x++){
                                if (listRubrik.get(x).getNameRubrik().equalsIgnoreCase("POIN 1")){
                                    idRubrik1 = listRubrik.get(x).getIdRubrik();
                                    deskRubrik1 = listRubrik.get(x).getDescRubrik();
                                    txtPoint1.setText(deskRubrik1);
                                } else if (listRubrik.get(x).getNameRubrik().equalsIgnoreCase("POIN 2")){
                                    idRubrik2 = listRubrik.get(x).getIdRubrik();
                                    deskRubrik2 = listRubrik.get(x).getDescRubrik();
                                    txtPoint2.setText(deskRubrik2);
                                } else if (listRubrik.get(x).getNameRubrik().equalsIgnoreCase("POIN 3")){
                                    idRubrik3 = listRubrik.get(x).getIdRubrik();
                                    deskRubrik3 = listRubrik.get(x).getDescRubrik();
                                    txtPoint3.setText(deskRubrik3);
                                } else if (listRubrik.get(x).getNameRubrik().equalsIgnoreCase("POIN 4")){
                                    idRubrik4 = listRubrik.get(x).getIdRubrik();
                                    deskRubrik4 = listRubrik.get(x).getDescRubrik();
                                    txtPoint4.setText(deskRubrik4);
                                }
                            }

                            rbPoint1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (rbPoint1.isChecked()){
                                        rbPoint2.setChecked(false);
                                        rbPoint3.setChecked(false);
                                        rbPoint4.setChecked(false);
                                        inputIdRubrik = idRubrik1;
                                        inputDeskRubrik = deskRubrik1;
                                    }
                                }
                            });

                            rbPoint2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (rbPoint2.isChecked()){
                                        rbPoint1.setChecked(false);
                                        rbPoint3.setChecked(false);
                                        rbPoint4.setChecked(false);
                                        inputIdRubrik = idRubrik2;
                                        inputDeskRubrik = deskRubrik2;
                                    }
                                }
                            });

                            rbPoint3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (rbPoint3.isChecked()){
                                        rbPoint1.setChecked(false);
                                        rbPoint2.setChecked(false);
                                        rbPoint4.setChecked(false);
                                        inputIdRubrik = idRubrik3;
                                        inputDeskRubrik = deskRubrik3;
                                    }
                                }
                            });

                            rbPoint4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (rbPoint4.isChecked()){
                                        rbPoint1.setChecked(false);
                                        rbPoint2.setChecked(false);
                                        rbPoint3.setChecked(false);
                                        inputIdRubrik = idRubrik4;
                                        inputDeskRubrik = deskRubrik4;
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = createPhotoFile();
            if (photoFile!=null){
                pathToFile= photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(InputTeacher.this, "com.project.eportfolio.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, 2);
            }
        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image= null;
        try {
            image= File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e){
            Log.d("mylog", "Except  :" + e.toString());
        }
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Bitmap bitmap= BitmapFactory.decodeFile(pathToFile);
                imgPortofolio.setImageBitmap(bitmap);
                foto = bitmap;
            }
        }
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void sendDataPortfolio(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = formatter.format(new Date());

        File fotoo = createTempFile(foto);
        byte[] bImg1 = AppUtil.FiletoByteArray(fotoo);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
        MultipartBody.Part fotox = MultipartBody.Part.createFormData("foto", fotoo.getName() + ".jpg", requestFile1);

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelPostPortofolio> data = apiInterface.sendDataPortfolioGuru(
                inputIdSiswa,
                PreferenceUtils.getIdGuru(getApplicationContext()),
                inputIdMapel,
                inputIdKategori,
                inputIdStrategi,
                inputIdRubrik,
                txtJudul.getText().toString(),
                inputDeskRubrik,
                now,
                "Indonesia",
                txtNilai.getText().toString(),
                txtPredikat.getText().toString(),
                txtNarasi.getText().toString(),
                fotox,
                inputIdKelas,
                txtTahunAjaran.getText().toString(),
                sp_semester.getSelectedItem().toString(),
                PreferenceUtils.getFirstName(getApplicationContext()),
                now,
                PreferenceUtils.getFirstName(getApplicationContext()),
                now,
                txtPredikat.getText().toString(),
                inputIdKelas
        );

        try {
            data.execute();
            if (data!=null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                        Toast.makeText(InputTeacher.this, "Portfolio Berhasil di Input !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void keProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacher.this, ProfileTeacher.class);
                        startActivity(a);
                        finish();
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
                        Intent a = new Intent(InputTeacher.this, MasterTeacher.class);
                        startActivity(a);
                        finish();
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
                        Intent a = new Intent(InputTeacher.this, HomeTeacher.class);
                        startActivity(a);
                        finish();
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
