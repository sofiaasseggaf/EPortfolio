package com.project.eportfolio.teacher;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
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
import com.project.eportfolio.model.portfolio.ModelPostPortofolio;
import com.project.eportfolio.model.rubrik.ModelMasterRubrik;
import com.project.eportfolio.model.rubrik.MsRubrik;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.utility.FileCompressor;
import com.project.eportfolio.utility.GRadioGroup;
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

public class InputTeacher extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile;
    Button btnInputPortfolio, btnOpenCamera;
    Spinner sp_mapel, sp_strategi, sp_kelas, sp_siswa, sp_kategori, sp_semester, sp_tahun_ajaran;
    EditText txtJudul, txtNarasi, txtNilai;
    TextView txtPoint4, txtPoint3, txtPoint2, txtPoint1, txtPredikat;
    RadioButton rbPoint4, rbPoint3, rbPoint2, rbPoint1;
    ImageView imgPortofolio;

    File photoFile, mPhotoFile;
    FileCompressor mCompressor;
    String mFileName;
    MultipartBody.Part fotox;

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

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.teacher_input);

        ButterKnife.bind(this);
        mCompressor = new FileCompressor(this);

//        GRadioGroup gr = new GRadioGroup(rbPoint1, rbPoint2, rbPoint3, rbPoint4);

        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btnInputPortfolio = findViewById(R.id.btnInputPortfolio);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);

        sp_kelas = findViewById(R.id.sp_kelas);
        sp_siswa = findViewById(R.id.sp_siswa);
        sp_mapel = findViewById(R.id.sp_mapel);
        sp_tahun_ajaran = findViewById(R.id.sp_tahun_ajaran);
        sp_semester = findViewById(R.id.sp_semester);

        sp_kategori = findViewById(R.id.sp_kategori);
        sp_strategi = findViewById(R.id.sp_strategi);
        txtJudul = findViewById(R.id.txtJudulKd);
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

        rbPoint1.setEnabled(false);
        rbPoint2.setEnabled(false);
        rbPoint3.setEnabled(false);
        rbPoint4.setEnabled(false);
        txtNilai.setEnabled(false);

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
                try{
                    if (Build.VERSION.SDK_INT>=23){
                        requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        if (ContextCompat.checkSelfPermission(InputTeacher.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            if  (ContextCompat.checkSelfPermission(InputTeacher.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                if (ContextCompat.checkSelfPermission(InputTeacher.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    selectImage();
                                }
                            }
                        } else {
                            Toast.makeText(InputTeacher.this, "camera gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                }
            }
        });

        btnInputPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InputTeacher.this, "Trial Version", Toast.LENGTH_SHORT).show();
                /*
                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendDataPortfolio();
                    }
                }).start();
                */

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


    // ============ CAMERA AND GALLERY THINGS ================

    private void selectImage() {
        final CharSequence[] items = {
                "Take Photo", "Choose from Library",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(InputTeacher.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Choose from Library")) {
                requestStoragePermission(false);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void requestStoragePermission(boolean isCamera) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(
                        error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT)
                                .show())
                .onSameThread()
                .check();
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try{
                photoFile = createImageFile();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            if (photoFile!=null){
                //pathToFile = photoFile.getAbsolutePath();
                mPhotoFile = photoFile;
                Uri photoUri = FileProvider.getUriForFile(InputTeacher.this, "com.project.eportfolio.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage(
                "This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @throws IOException
     */

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(InputTeacher.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgPortofolio);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(InputTeacher.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgPortofolio);
            }
        }
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
                   getStrategi();
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
                    /*for (int i = 0; i < dataModelKategoriStrategi.getData().getMsKategoristrategi().size(); i++) {
                        listKategoriStrategi.add(dataModelKategoriStrategi.getData().getMsKategoristrategi().get(i));
                    }*/
                    getRubrik();
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
                           // setSpinnerKelas();
                           // setSpinnerMapel();
                           // setSpinnerStrategi();
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

    private void setSpinnerStrategi(){

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
                    if (listStrategi.get(a).getNameStrategi().equalsIgnoreCase(sp_kategori.getSelectedItem().toString())){
                        inputIdKategori = listStrategi.get(a).getKategoriid();
                        for(int i=0; i<dataModelKategoriStrategi.getData().getMsKategoristrategi().size(); i++){
                            if (dataModelKategoriStrategi.getData().getMsKategoristrategi().get(i).getIdKategori().equalsIgnoreCase(inputIdKategori)) {
                                listKategoriStrategi.add(dataModelKategoriStrategi.getData().getMsKategoristrategi().get(i));
                            }
                        }
                    }
                }


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

                            /*rbPoint1.setOnClickListener(new View.OnClickListener() {
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
                            });*/
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

    private void sendDataPortfolio(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = formatter.format(new Date());

        if (mPhotoFile!=null){
            byte[] bImg1 = AppUtil.FiletoByteArray(mPhotoFile);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
            fotox = MultipartBody.Part.createFormData("foto", mFileName + ".jpg", requestFile1);
        }

        if (Integer.parseInt(txtNilai.getText().toString())>50&&Integer.parseInt(txtNilai.getText().toString())<=60){
            txtPredikat.setText("D");
        } else if (Integer.parseInt(txtNilai.getText().toString())>60&&Integer.parseInt(txtNilai.getText().toString())<=70){
            txtPredikat.setText("C");
        } else if (Integer.parseInt(txtNilai.getText().toString())>70&&Integer.parseInt(txtNilai.getText().toString())<=85) {
            txtPredikat.setText("B");
        } else if (Integer.parseInt(txtNilai.getText().toString())>85&&Integer.parseInt(txtNilai.getText().toString())<=100) {
            txtPredikat.setText("A");
        } else {
            txtPredikat.setText("E");
        }

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
                sp_tahun_ajaran.getSelectedItem().toString(),
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
                        txtNilai.setText("");
                        txtPredikat.setText("");
                        imgPortofolio.setImageBitmap(null);
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
                        if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }
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
                        if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }
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

    //Your Slide animation
    public void setAnimation(){
        if(Build.VERSION.SDK_INT>20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(500);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
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
                        if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }
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
