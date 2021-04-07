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

public class InputTeacherC extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile, btn_simpandanlanjutkan;
    EditText txtNarasi, txtNilai;
    TextView txtPoint4, txtPoint3, txtPoint2, txtPoint1, txtkategori;
    LinearLayout ll_predikat_a, ll_predikat_b, ll_predikat_c, ll_predikat_d;
    RadioButton rbPoint4, rbPoint3, rbPoint2, rbPoint1;
    int nilai;
    String inputIdSiswa, inputIdMapel, inputIdKelas, inputNamaKelas;
    String inputIdStrategi, inputIdKategori, inputTxtJudul, inputTahunAjaran, inputSemester;

    String deskRubrik1, idRubrik1, deskRubrik2, idRubrik2, deskRubrik3, idRubrik3, deskRubrik4, idRubrik4, inputIdRubrik, inputDeskRubrik, inputPredikatMutu;

    ModelMasterRubrik dataMasterRubrik;
    List<MsRubrik> listRubrik = new ArrayList<>();

    TextView txtload;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAnimation();
        setContentView(R.layout.teacher_input_c);

        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btn_simpandanlanjutkan = findViewById(R.id.btn_simpandanlanjutkan);

        rbPoint1 = findViewById(R.id.rbPoint1);
        rbPoint2 = findViewById(R.id.rbPoint2);
        rbPoint3 = findViewById(R.id.rbPoint3);
        rbPoint4 = findViewById(R.id.rbPoint4);
        txtPoint4 = findViewById(R.id.txtPoint4);
        txtPoint3 = findViewById(R.id.txtPoint3);
        txtPoint2 = findViewById(R.id.txtPoint2);
        txtPoint1 = findViewById(R.id.txtPoint1);
        txtNarasi= findViewById(R.id.txtNarasi);
        //txtPredikat = findViewById(R.id.txtPredikat);
        txtNilai = findViewById(R.id.txtNilai);
        txtload = findViewById(R.id.textloading);

        ll_predikat_a = findViewById(R.id.ll_predikat_a);
        ll_predikat_b = findViewById(R.id.ll_predikat_b);
        ll_predikat_c = findViewById(R.id.ll_predikat_c);
        ll_predikat_d = findViewById(R.id.ll_predikat_d);

        Intent intent = getIntent();
        inputIdStrategi = intent.getStringExtra("inputIdStrategi");
        inputIdKategori = intent.getStringExtra("inputIdKategori");
        inputTxtJudul = intent.getStringExtra("inputTxtJudul");
        inputTahunAjaran = intent.getStringExtra("inputTahunAjaran");
        inputSemester = intent.getStringExtra("inputSemester");
        inputIdKelas = intent.getStringExtra("inputIdKelas");
        inputNamaKelas = intent.getStringExtra("inputNamaKelas");
        inputIdMapel = intent.getStringExtra("inputIdMapel");
        inputIdSiswa = intent.getStringExtra("inputIdSiswa");

        first();


        if(!txtNarasi.getText().toString().equalsIgnoreCase("") && !txtNilai.getText().toString().equalsIgnoreCase("")){
            btn_simpandanlanjutkan.setBackgroundResource(R.drawable.btn_simpandanlanjutkan_active);
        }

        txtNilai.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // Handle pressing "Enter" key here
                    if (!txtNilai.getText().toString().equalsIgnoreCase("")) {

                        nilai = Integer.parseInt(txtNilai.getText().toString());

                        /*if (nilai > 0 && nilai <=50) {
                            txtPredikat.setText("E");
                        } else if (nilai > 50 && nilai <= 60) {
                            txtPredikat.setText("D");
                        } else if (nilai > 60 && nilai <= 70) {
                            txtPredikat.setText("C");
                        } else if (nilai > 70 && nilai <= 85) {
                            txtPredikat.setText("B");
                        } else if (nilai > 85 && nilai <= 100) {
                            txtPredikat.setText("A");
                        } else if (nilai > 100) {
                            txtPredikat.setText("-");
                        }*/
                    } else {
                        handled = false;
                    }

                    handled = true;
                }

                return handled;
            }
        });

        rbPoint1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txtNarasi.setText(deskRubrik1);
                    inputIdRubrik = idRubrik1;
                    inputDeskRubrik = deskRubrik1;
                    inputPredikatMutu = "D";
                    ll_predikat_a.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_b.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_c.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_d.setBackgroundResource(R.drawable.rect_predikat_active);
                    rbPoint2.setChecked(false);
                    rbPoint3.setChecked(false);
                    rbPoint4.setChecked(false);

                }
            }
        });

        rbPoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbPoint2.isChecked()){
                    txtNarasi.setText(deskRubrik2);
                    inputIdRubrik = idRubrik2;
                    inputDeskRubrik = deskRubrik2;
                    inputPredikatMutu = "C";
                    ll_predikat_a.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_b.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_c.setBackgroundResource(R.drawable.rect_predikat_active);
                    ll_predikat_d.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    rbPoint1.setChecked(false);
                    rbPoint3.setChecked(false);
                    rbPoint4.setChecked(false);
//                    inputIdRubrik = idRubrik2;
//                    inputDeskRubrik = deskRubrik2;
                }
            }
        });

        rbPoint3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbPoint3.isChecked()){
                    txtNarasi.setText(deskRubrik3);
                    inputDeskRubrik = deskRubrik3;
                    inputIdRubrik = idRubrik3;
                    inputPredikatMutu = "B";
                    ll_predikat_a.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_b.setBackgroundResource(R.drawable.rect_predikat_active);
                    ll_predikat_c.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_d.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    rbPoint1.setChecked(false);
                    rbPoint2.setChecked(false);
                    rbPoint4.setChecked(false);
//                    inputIdRubrik = idRubrik3;
//                    inputDeskRubrik = deskRubrik3;
                }
            }
        });

        rbPoint4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbPoint4.isChecked()){
                    txtNarasi.setText(deskRubrik4);
                    inputIdRubrik = idRubrik4;
                    inputDeskRubrik = deskRubrik4;
                    inputPredikatMutu = "A";
                    ll_predikat_a.setBackgroundResource(R.drawable.rect_predikat_active);
                    ll_predikat_b.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_c.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    ll_predikat_d.setBackgroundResource(R.drawable.rect_predikat_unactive);
                    rbPoint1.setChecked(false);
                    rbPoint2.setChecked(false);
                    rbPoint3.setChecked(false);
//                    inputIdRubrik = idRubrik4;
//                    inputDeskRubrik = deskRubrik4;
                }
            }
        });


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
                if(!txtNarasi.getText().toString().equalsIgnoreCase("") && !txtNilai.getText().toString().equalsIgnoreCase("")){
                    Intent a = new Intent(InputTeacherC.this, InputTeacherD.class);
                    a.putExtra("inputIdKategori", inputIdKategori);
                    a.putExtra("inputIdStrategi", inputIdStrategi);
                    a.putExtra("inputTxtJudul", inputTxtJudul);
                    a.putExtra("inputTahunAjaran", inputTahunAjaran);
                    a.putExtra("inputSemester", inputSemester);
                    a.putExtra("inputIdKelas", inputIdKelas);
                    a.putExtra("inputNamaKelas", inputNamaKelas);
                    a.putExtra("inputIdMapel", inputIdMapel);
                    a.putExtra("inputIdSiswa", inputIdSiswa);
                    a.putExtra("inputIdRubrik", inputIdRubrik);
                    a.putExtra("inputDeskRubrik", inputDeskRubrik);
                    a.putExtra("inputNilai", txtNilai.getText().toString());
                    a.putExtra("inputPredikatMutu", inputPredikatMutu);
                    a.putExtra("inputTxtNarasi", txtNarasi.getText().toString());
                    startActivity(a);
                    finish();
                } else {
                    Toast.makeText(InputTeacherC.this, "Isi Narasi & Nilai !", Toast.LENGTH_SHORT).show();
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
                getRubrik();
            }
        }).start();
    }


    // =================== GET DATA ================


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
                            if (!inputIdStrategi.equalsIgnoreCase("") || inputIdStrategi!=null){
                                try{
                                    for(int i=0; i<dataMasterRubrik.getTotal(); i++){
                                        if (dataMasterRubrik.getData().getMsRubrik().get(i).getStrategiid().equalsIgnoreCase(inputIdStrategi)) {
                                            listRubrik.add(dataMasterRubrik.getData().getMsRubrik().get(i));
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
                                    }

                                } catch (Exception a){ }
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelMasterRubrik> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InputTeacherC.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }



    public void keProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherC.this, ProfileTeacher.class);
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
                        Intent a = new Intent(InputTeacherC.this, MasterTeacher.class);
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
                        Intent a = new Intent(InputTeacherC.this, HomeTeacher.class);
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
