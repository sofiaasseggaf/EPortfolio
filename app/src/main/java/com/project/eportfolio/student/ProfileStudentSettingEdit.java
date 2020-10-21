package com.project.eportfolio.student;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.APIService.AppUtil;
import com.project.eportfolio.R;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.ModelUpdateDataSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileStudentSettingEdit extends AppCompatActivity {

    LinearLayout btnSimpan;
    TextView btnUbahFotoProfile, namaSiswa, nisSiswa;
    EditText editFirstName, editMidName, editLastName, editTtl, editNis, editJk, editAlamat, editEmail, editTelp;
    ImageView imgSiswa;
    Bitmap f;
    ModelSiswa dataModelSiswa;
    MsMurid dataSiswa;
    String tempFotoPiagam;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile_setting_edit);

        namaSiswa = findViewById(R.id.namaSiswa);
        nisSiswa = findViewById(R.id.nisSiswa);
        btnUbahFotoProfile = findViewById(R.id.btnUbahFotoProfile);
        imgSiswa = findViewById(R.id.fotoSiswa);
        btnSimpan = findViewById(R.id.btnSimpan);

        editFirstName = findViewById(R.id.editFirstName);
        editMidName = findViewById(R.id.editMiddleName);
        editLastName = findViewById(R.id.editLastName);
        editTtl = findViewById(R.id.editTtl);
        editNis = findViewById(R.id.editNis);
        editJk = findViewById(R.id.editJk);
        editAlamat = findViewById(R.id.editAlamat);
        editEmail = findViewById(R.id.editEmail);
        editTelp = findViewById(R.id.editTelp);

        setDataProfile();

        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;

        final FilePickerDialog dialogPicker1 = new FilePickerDialog(ProfileStudentSettingEdit.this, properties);
        dialogPicker1.setTitle("Pilih File Foto");
        dialogPicker1.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                tempFotoPiagam = "file://" + files[0];
                Picasso.get().load(tempFotoPiagam).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        f = bitmap;
                        thread2();
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(ProfileStudentSettingEdit.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            }
        });

        dialogPicker1.dismiss();

        btnUbahFotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPicker1.show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editFirstName.getText().toString()!=null && editMidName.getText().toString()!=null  && editLastName.getText().toString()!=null  &&
                        editTtl.getText().toString()!=null && editJk.getText().toString()!=null && editAlamat.getText().toString()!=null &&
                        editEmail.getText().toString()!=null && editTelp.getText().toString()!=null) {
                    thread();
                } else {
                    Toast.makeText(ProfileStudentSettingEdit.this, "Lengkapi Data Terlebih Dahulu !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setDataProfile(){

        namaSiswa.setText(PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext()));
        nisSiswa.setText(PreferenceUtils.getNis(getApplicationContext()));

        if (!PreferenceUtils.getPhotoSiswa(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoSiswa(getApplicationContext())!= null){
            Picasso.get().load(PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imgSiswa.setImageBitmap(bitmap);
                }
                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Toast.makeText(ProfileStudentSettingEdit.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }

        editFirstName.setText(PreferenceUtils.getFirstName(getApplicationContext()));
        editMidName.setText(PreferenceUtils.getMidName(getApplicationContext()));
        editLastName.setText(PreferenceUtils.getLastName(getApplicationContext()));
        editTtl.setText(PreferenceUtils.getTtl(getApplicationContext()));
        editNis.setText(PreferenceUtils.getNis(getApplicationContext()));
        editJk.setText(PreferenceUtils.getJk(getApplicationContext()));
        editAlamat.setText(PreferenceUtils.getAddress(getApplicationContext()));
        editEmail.setText(PreferenceUtils.getEmail(getApplicationContext()));
        editTelp.setText(PreferenceUtils.getTlp(getApplicationContext()));

    }

    private void thread() {
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDataSiswaRequired();
            }
        }).start();
    }

    private void thread2(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDataSiswaFoto();
            }
        }).start();
    }

    private void updateDataSiswaRequired(){

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataSiswa> postAdd = apiInterface.updateDataSiswaRequired(

                PreferenceUtils.getIdSiswa(getApplicationContext()),
                PreferenceUtils.getUserId(getApplicationContext()),
                PreferenceUtils.getIdSekolahSiswa(getApplicationContext()),
                PreferenceUtils.getIdKelas(getApplicationContext()),
                editFirstName.getText().toString(),
                editMidName.getText().toString(),
                editLastName.getText().toString(),
                editNis.getText().toString(),
                editJk.getText().toString(),
                editTtl.getText().toString(),
                editAlamat.getText().toString(),
                editEmail.getText().toString(),
                editTelp.getText().toString()
        );

        postAdd.enqueue(new Callback<ModelUpdateDataSiswa>() {
            @Override
            public void onResponse(Call<ModelUpdateDataSiswa> call, Response<ModelUpdateDataSiswa> response) {

                try {
                    if (response.isSuccessful() ) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProfileStudentSettingEdit.this, "Update Berhasil", Toast.LENGTH_LONG).show();
                            }
                        });
                        getSiswa();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ModelUpdateDataSiswa> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProfileStudentSettingEdit.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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

                if (response.body()!=null){

                        for (int a = 0; a < dataModelSiswa.getTotal(); a++) {
                            try {
                                if (PreferenceUtils.getIdSiswa(getApplicationContext()).equalsIgnoreCase(dataModelSiswa.getData().getMsMurid().get(a).getId())) {
                                    dataSiswa = dataModelSiswa.getData().getMsMurid().get(a);
                                    saveDataMurid();
                                }
                            } catch (Exception e){
                            }
                        }

                }
            }
            @Override
            public void onFailure(Call<ModelSiswa> call, Throwable t) {

                //Toast.makeText(LoginActivity.this, "Terjadi gangguan koneksi (getSiswa)", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

        /*
        try {
            Response<ModelSiswa> response = dataSiswax.execute();
            dataModelSiswa = response.body();
            if (dataModelSiswa!=null){
                try {
                    for (int a = 0; a < dataModelSiswa.getData().getMsMurid().size(); a++) {
                        if (PreferenceUtils.getIdSiswa(getApplicationContext()).equalsIgnoreCase(dataModelSiswa.getData().getMsMurid().get(a).getId())) {

                            dataSiswa = dataModelSiswa.getData().getMsMurid().get(a);
                            saveDataMurid();
                        }
                    }
                }catch (Exception e){
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }

    public void saveDataMurid(){

        PreferenceUtils.saveFirstName(dataSiswa.getFirstname(), getApplicationContext());
        PreferenceUtils.saveMidName(dataSiswa.getMidname(), getApplicationContext());
        PreferenceUtils.saveLastName(dataSiswa.getLastname(), getApplicationContext());
        PreferenceUtils.saveTtl(dataSiswa.getTtl(), getApplicationContext());
        PreferenceUtils.saveNis(dataSiswa.getNis(), getApplicationContext());
        PreferenceUtils.saveJk(dataSiswa.getGender(), getApplicationContext());
        PreferenceUtils.saveAddress(dataSiswa.getAddress(), getApplicationContext());
        PreferenceUtils.saveEmail(dataSiswa.getEmail(), getApplicationContext());
        PreferenceUtils.saveTlp(dataSiswa.getPhone(), getApplicationContext());
        PreferenceUtils.savePhotoSiswa(dataSiswa.getPhoto(), getApplicationContext());


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(ProfileStudentSettingEdit.this, "Data Selesai di Input", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(ProfileStudentSettingEdit.this, ProfileStudent.class);
                startActivity(a);
                finish();
            }
        });
    }

    //send post data with image
    private void updateDataSiswaFoto(){

        File foto2 = createTempFile(f);
        byte[] bImg1 = AppUtil.FiletoByteArray(foto2);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
        MultipartBody.Part fotox = MultipartBody.Part.createFormData("photo", PreferenceUtils.getFirstName(getApplicationContext())+".jpg", requestFile1);

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataSiswa> postAdd = apiInterface.updateDataSiswaFoto(

                PreferenceUtils.getIdSiswa(getApplicationContext()),
                PreferenceUtils.getUserId(getApplicationContext()),
                PreferenceUtils.getIdSekolahSiswa(getApplicationContext()),
                PreferenceUtils.getIdKelas(getApplicationContext()),
                PreferenceUtils.getFirstName(getApplicationContext()),
                PreferenceUtils.getMidName(getApplicationContext()),
                PreferenceUtils.getLastName(getApplicationContext()),
                PreferenceUtils.getTtl(getApplicationContext()),
                PreferenceUtils.getNis(getApplicationContext()),
                PreferenceUtils.getJk(getApplicationContext()),
                PreferenceUtils.getAddress(getApplicationContext()),
                PreferenceUtils.getEmail(getApplicationContext()),
                PreferenceUtils.getTlp(getApplicationContext()),
                fotox
        );

        try {
            postAdd.execute();
            getSiswa();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //bitmap to file
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



    public void onBackPressed() {
        Intent a = new Intent(ProfileStudentSettingEdit.this, ProfileStudent.class);
        startActivity(a);
        finish();
    }

}
