package com.project.eportfolio.teacher;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.ModelUpdateDataGuru;
import com.project.eportfolio.student.ProfileStudent;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileTeacherSettingPrivacy extends AppCompatActivity {

    LinearLayout btnSimpan;

    ModelGuru dataModelGuru;
    EditText editPassLama, editPassBaru, editPassBaruu;
    String newpass;
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.teacher_profile_setting_privacy);

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
                                updatePasswordGuru();
                            }
                        }).start();

                    } else {
                        Toast.makeText(ProfileTeacherSettingPrivacy.this, "Password yang diinput tidak sama", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileTeacherSettingPrivacy.this, "Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePasswordGuru(){

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataGuru> postAdd = apiInterface.updatePasswordGuru(

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
                editPassBaru.getText().toString()
        );

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
                        Toast.makeText(ProfileTeacherSettingPrivacy.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
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
                    newpass = dataModelGuru.getData().getMsGuru().get(a).getPassword().toString();
                    PreferenceUtils.savePassword(newpass, getApplicationContext());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(ProfileTeacherSettingPrivacy.this, "Password Berhasil Diganti !", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(ProfileTeacherSettingPrivacy.this, ProfileStudent.class);
                            if(Build.VERSION.SDK_INT>20){
                                ActivityOptions options =
                                        ActivityOptions.makeSceneTransitionAnimation(ProfileTeacherSettingPrivacy.this);
                                startActivity(a,options.toBundle());
                            }else {
                                startActivity(a);
                                finish();
                            }
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(500);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
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
                        Intent a = new Intent(ProfileTeacherSettingPrivacy.this, ProfileTeacher.class);
                        if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(ProfileTeacherSettingPrivacy.this);
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
