package com.project.eportfolio.student;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.ModelUpdateDataSiswa;
import com.project.eportfolio.model.user.AauthUser;
import com.project.eportfolio.model.user.ModelUser;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileStudentSettingPrivacy extends AppCompatActivity {

    LinearLayout btnSimpan;

    ModelSiswa dataModelSiswa;
    EditText editPassLama, editPassBaru, editPassBaruu;
    String newpass;
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile_setting_privacy);

        editPassLama = findViewById(R.id.editPasswordLama);
        editPassBaru = findViewById(R.id.editPasswordBaru);
        editPassBaruu = findViewById(R.id.editPasswordBaruu);

        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editPassLama.getText().toString().equalsIgnoreCase(PreferenceUtils.getPassword(getApplicationContext()))){
                    if (editPassBaru.getText().toString().equalsIgnoreCase(editPassBaruu.getText().toString())){
                        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                updatePasswordSiswa();
                            }
                        }).start();

                    } else {
                        Toast.makeText(ProfileStudentSettingPrivacy.this, "Password yang diinput tidak sama", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileStudentSettingPrivacy.this, "Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePasswordSiswa(){

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataSiswa> postAdd = apiInterface.updatePasswordSiswa(

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
                editPassBaru.getText().toString()
        );

        postAdd.enqueue(new Callback<ModelUpdateDataSiswa>() {
            @Override
            public void onResponse(Call<ModelUpdateDataSiswa> call, Response<ModelUpdateDataSiswa> response) {
                getSiswa();
            }

            @Override
            public void onFailure(Call<ModelUpdateDataSiswa> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProfileStudentSettingPrivacy.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getSiswa() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSiswa> dataSiswax = apiInterface.getDataSiswa(apikey, 1000);

        try {

            Response<ModelSiswa> response = dataSiswax.execute();

            dataModelSiswa = response.body();

            for (int a = 0; a < dataModelSiswa.getData().getMsMurid().size(); a++) {
                if (PreferenceUtils.getIdSiswa(getApplicationContext()).equalsIgnoreCase(dataModelSiswa.getData().getMsMurid().get(a).getId())) {
                    newpass = dataModelSiswa.getData().getMsMurid().get(a).getPassword().toString();
                    PreferenceUtils.savePassword(newpass, getApplicationContext());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(ProfileStudentSettingPrivacy.this, "Password Berhasil Diganti !", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(ProfileStudentSettingPrivacy.this, ProfileStudent.class);
                            startActivity(a);
                            finish();
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Edit Password ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //HomeTeacher.super.onBackPressed();
                        Intent a = new Intent(ProfileStudentSettingPrivacy.this, ProfileStudent.class);
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
