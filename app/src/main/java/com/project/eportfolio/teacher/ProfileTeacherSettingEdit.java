package com.project.eportfolio.teacher;

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
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.ModelUpdateDataGuru;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.model.siswa.ModelUpdateDataSiswa;
import com.project.eportfolio.student.ProfileStudentSettingEdit;
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

public class ProfileTeacherSettingEdit extends AppCompatActivity {

    LinearLayout btnSimpan;
    TextView btnUbahFotoProfile, namaGuru, nipGuru;
    EditText editFirstName, editMidName, editLastName, editNik, editNip, editJk, editEmail, editAlamat, editTelp;
    ImageView imgGuru;
    MsGuru dataGuru;
    Bitmap f;
    ModelGuru dataModelGuru;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile_setting_edit);

        btnSimpan = findViewById(R.id.btnSimpan);
        namaGuru = findViewById(R.id.namaGuru);
        nipGuru = findViewById(R.id.nipGuru);
        btnUbahFotoProfile = findViewById(R.id.btnUbahFotoProfile);
        imgGuru = findViewById(R.id.imgGuru);

        editFirstName = findViewById(R.id.editFirstName);
        editMidName = findViewById(R.id.editMiddleName);
        editLastName = findViewById(R.id.editLastName);
        editNik = findViewById(R.id.editNik);
        editNip = findViewById(R.id.editNip);
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

        final FilePickerDialog dialogPicker1 = new FilePickerDialog(ProfileTeacherSettingEdit.this, properties);
        dialogPicker1.setTitle("Pilih File Foto");
        dialogPicker1.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                String tempFotoPiagam = "file://" + files[0];
                Picasso.get().load(tempFotoPiagam).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        f = bitmap;
                        thread2();
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(ProfileTeacherSettingEdit.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
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
                                editNip.getText().toString()!=null && editJk.getText().toString()!=null && editAlamat.getText().toString()!=null &&
                                editEmail.getText().toString()!=null && editTelp.getText().toString()!=null) {
                            thread();
                        } else {
                            Toast.makeText(ProfileTeacherSettingEdit.this, "Lengkapi Data Terlebih Dahulu !", Toast.LENGTH_SHORT).show();
                        }
                    }
        });

    }

    public void setDataProfile(){
        namaGuru.setText(PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext()));
        nipGuru.setText(PreferenceUtils.getNip(getApplicationContext()));

        if (!PreferenceUtils.getPhotoGuru(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoGuru(getApplicationContext())!= null){
            Picasso.get().load(PreferenceUtils.getPhotoGuru(getApplicationContext())).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imgGuru.setImageBitmap(bitmap);
                }
                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Toast.makeText(ProfileTeacherSettingEdit.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }

        editFirstName.setText(PreferenceUtils.getFirstName(getApplicationContext()));
        editMidName.setText(PreferenceUtils.getMidName(getApplicationContext()));
        editLastName.setText(PreferenceUtils.getLastName(getApplicationContext()));
        editNik.setText(PreferenceUtils.getNik(getApplicationContext()));
        editNip.setText(PreferenceUtils.getNip(getApplicationContext()));
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
                updateDataGuruRequired();
            }
        }).start();
    }

    private void thread2(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDataGuruFoto();
            }
        }).start();
    }

    private void updateDataGuruRequired(){

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataGuru> postAdd = apiInterface.updateDataGuruRequired(

                PreferenceUtils.getIdGuru(getApplicationContext()),
                PreferenceUtils.getUserId(getApplicationContext()),
                PreferenceUtils.getIdSekolahGuru(getApplicationContext()),
                editFirstName.getText().toString(),
                editMidName.getText().toString(),
                editLastName.getText().toString(),
                editNik.getText().toString(),
                editNip.getText().toString(),
                editJk.getText().toString(),
                editAlamat.getText().toString(),
                editEmail.getText().toString(),
                editTelp.getText().toString());

        postAdd.enqueue(new Callback<ModelUpdateDataGuru>() {
            @Override
            public void onResponse(Call<ModelUpdateDataGuru> call, Response<ModelUpdateDataGuru> response) {
                getGuru();
            }

            @Override
            public void onFailure(Call<ModelUpdateDataGuru> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProfileTeacherSettingEdit.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getGuru() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelGuru> dataSiswax = apiInterface.getdataGuru(apikey, 1000);

        try {
            Response<ModelGuru> response = dataSiswax.execute();
            dataModelGuru = response.body();
            for (int a = 0; a < dataModelGuru.getData().getMsGuru().size(); a++) {
                if (PreferenceUtils.getIdGuru(getApplicationContext()).equalsIgnoreCase(dataModelGuru.getData().getMsGuru().get(a).getIdGuru())) {
                    dataGuru = dataModelGuru.getData().getMsGuru().get(a);
                    saveDataGuru();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDataGuru(){

        PreferenceUtils.saveFirstName(dataGuru.getFirstname(), getApplicationContext());
        PreferenceUtils.saveMidName(dataGuru.getMidname(), getApplicationContext());
        PreferenceUtils.saveLastName(dataGuru.getLastname(), getApplicationContext());
        PreferenceUtils.saveNik(dataGuru.getNik(), getApplicationContext());
        PreferenceUtils.saveNip(dataGuru.getNip(), getApplicationContext());
        PreferenceUtils.saveJk(dataGuru.getGender(), getApplicationContext());
        PreferenceUtils.saveAddress(dataGuru.getAddress(), getApplicationContext());
        PreferenceUtils.saveEmail(dataGuru.getEmail(), getApplicationContext());
        PreferenceUtils.saveTlp(dataGuru.getPhone(), getApplicationContext());
        PreferenceUtils.savePhotoGuru(dataGuru.getPhoto(), getApplicationContext());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(ProfileTeacherSettingEdit.this, "Data Selesai di Input", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(ProfileTeacherSettingEdit.this, ProfileTeacher.class);
                startActivity(a);
                finish();
            }
        });
    }

    //send post data with image
    private void updateDataGuruFoto(){

        File foto2 = createTempFile(f);
        byte[] bImg1 = AppUtil.FiletoByteArray(foto2);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
        MultipartBody.Part fotox = MultipartBody.Part.createFormData("photo", PreferenceUtils.getFirstName(getApplicationContext())+".jpg", requestFile1);

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataGuru> postAdd = apiInterface.updateDataGuruFoto(

                PreferenceUtils.getIdGuru(getApplicationContext()),
                PreferenceUtils.getUserId(getApplicationContext()),
                PreferenceUtils.getIdSekolahGuru(getApplicationContext()),
                PreferenceUtils.getFirstName(getApplicationContext()),
                PreferenceUtils.getMidName(getApplicationContext()),
                PreferenceUtils.getLastName(getApplicationContext()),
                PreferenceUtils.getNik(getApplicationContext()),
                PreferenceUtils.getNip(getApplicationContext()),
                PreferenceUtils.getJk(getApplicationContext()),
                PreferenceUtils.getAddress(getApplicationContext()),
                PreferenceUtils.getEmail(getApplicationContext()),
                PreferenceUtils.getTlp(getApplicationContext()),
                fotox
        );

        try {
            postAdd.execute();
            getGuru();

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
        Intent a = new Intent(ProfileTeacherSettingEdit.this, HomeTeacher.class);
        startActivity(a);
        finish();
    }
}
