package com.project.eportfolio;

import android.content.Intent;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.gson.Gson;
import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.model.sekolah.ModelSekolah;
import com.project.eportfolio.model.sekolah.MsSekolah;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.user.ModelUser;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.teacher.HomeTeacher;
import com.project.eportfolio.utility.Constants;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    RadioButton rbStudent, rbTeacher;
    String username, password, idUser;
    Bitmap photosiswa, photoguru;
    boolean isValid;

    ModelSiswa dataModelSiswa;
    ModelUser dataModelUser;
    ModelGuru dataModelGuru;
    ModelSekolah dataModelSekolah;

    MsMurid userSiswa;
    MsGuru userGuru;
    MsSekolah dataSekolah;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        rbTeacher = findViewById(R.id.rbTeacher);
        rbStudent = findViewById(R.id.rbStudent);

        first();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                login();
            }
        });
    }


    private void first(){
        if (PreferenceUtils.getUsername(this) == null || PreferenceUtils.getUsername(this).equalsIgnoreCase("")){
            second();
        } else if(PreferenceUtils.getStatus(this).equalsIgnoreCase("murid")){
            Intent a = new Intent(LoginActivity.this, HomeStudent.class);
            startActivity(a);
            finish();
        } else if (PreferenceUtils.getStatus(this).equalsIgnoreCase("guru")){
            Intent a = new Intent(LoginActivity.this, HomeTeacher.class);
            startActivity(a);
            finish();
        }
    }

    private void second(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        getUser();
    }

    public void login(){

        if(rbStudent.isChecked()) {
            if (!txtUsername.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
                isValid = false;
                for (int i = 0; i < dataModelUser.getTotal(); i++) {
                    username = dataModelUser.getData().getAauthUsers().get(i).getUsername();
                    password = dataModelUser.getData().getAauthUsers().get(i).getPass();
                    idUser = dataModelUser.getData().getAauthUsers().get(i).getId();
                    if (username.equalsIgnoreCase(txtUsername.getText().toString()) && password.equalsIgnoreCase(txtPassword.getText().toString())) {
                        isValid = true;
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "akun belum terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
                }
                if (isValid) {
                    for(int a = 0; a < dataModelSiswa.getTotal(); a++) {
                        if (dataModelSiswa.getData().getMsMurid().get(a).getUserid().equalsIgnoreCase(idUser)) {
                            userSiswa = dataModelSiswa.getData().getMsMurid().get(a);
                            saveDataMurid();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "akun belum terdaftar", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        break;
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "akun belum terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else if (txtUsername.getText().toString().isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "please fill any empty field", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (txtPassword.getText().toString().isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "please fill any empty field", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (txtPassword.getText().toString().isEmpty() && txtUsername.getText().toString().isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "please fill any empty field", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        else if(rbTeacher.isChecked()) {

            if (!txtUsername.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
                isValid = false;
                for (int i = 0; i <= dataModelUser.getTotal(); i++) {
                    username = dataModelUser.getData().getAauthUsers().get(i).getUsername();
                    password = dataModelUser.getData().getAauthUsers().get(i).getPass();
                    idUser = dataModelUser.getData().getAauthUsers().get(i).getId();
                    if (username.equalsIgnoreCase(txtUsername.getText().toString()) && password.equalsIgnoreCase(txtPassword.getText().toString())) {
                        isValid = true;
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "akun belum terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
                }

                if (isValid) {
                    for(int a = 0; a < dataModelGuru.getTotal(); a++){
                        if (dataModelGuru.getData().getMsGuru().get(a).getUserid().equalsIgnoreCase(idUser)){
                            userGuru = dataModelGuru.getData().getMsGuru().get(a);
                            saveDataGuru();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "akun belum terdaftar", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        break;
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "akun belum terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else if (txtUsername.getText().toString().isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "please fill any empty field", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (txtPassword.getText().toString().isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "please fill any empty field", Toast.LENGTH_SHORT).show();
                    }
                });
            }else if (txtPassword.getText().toString().isEmpty() && txtUsername.getText().toString().isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "please fill any empty field", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (!rbTeacher.isChecked() || !rbStudent.isChecked()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Please Choose Your Status Account", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void saveDataGuru(){
        PreferenceUtils.saveIdGuru(userGuru.getIdGuru(), getApplicationContext());
        PreferenceUtils.savePassword(password,getApplicationContext());
        PreferenceUtils.saveUsername(username, getApplicationContext());
        PreferenceUtils.saveIdSekolahGuru(userGuru.getSekolahid(), getApplicationContext());
        PreferenceUtils.saveFirstName(userGuru.getFirstname(), getApplicationContext());
        PreferenceUtils.saveMidName(userGuru.getMidname(), getApplicationContext());
        PreferenceUtils.saveLastName(userGuru.getLastname(), getApplicationContext());
        PreferenceUtils.saveNik(userGuru.getNik(), getApplicationContext());
        PreferenceUtils.saveNip(userGuru.getNip(), getApplicationContext());
        PreferenceUtils.saveJk(userGuru.getGender(), getApplicationContext());
        PreferenceUtils.saveReligion(userGuru.getReligion(), getApplicationContext());
        PreferenceUtils.saveProvince(userGuru.getProvince(), getApplicationContext());
        PreferenceUtils.saveCity(userGuru.getCity(), getApplicationContext());
        PreferenceUtils.saveDistrict(userGuru.getDistrict(), getApplicationContext());
        PreferenceUtils.saveVillage(userGuru.getVillage(), getApplicationContext());
        PreferenceUtils.saveAddress(userGuru.getAddress(), getApplicationContext());
        PreferenceUtils.savePostalCode(userGuru.getPostalcode(), getApplicationContext());
        PreferenceUtils.saveTlp(userGuru.getPhone(), getApplicationContext());
        PreferenceUtils.saveEmail(userGuru.getEmail(), getApplicationContext());
        PreferenceUtils.savePhotoGuru(userGuru.getPhoto(), getApplicationContext());
        PreferenceUtils.saveStatus("guru", getApplicationContext());

        for (int x = 0; x < dataModelSekolah.getData().getMsSekolah().size(); x++) {
            if (userGuru.getSekolahid().equalsIgnoreCase(dataModelSekolah.getData().getMsSekolah().get(x).getIdSekolah())) {
                dataSekolah = dataModelSekolah.getData().getMsSekolah().get(x);
                saveDataSekolahGuru();
            }
        }
    }

    public void saveDataMurid(){
        PreferenceUtils.saveIdSiswa(userSiswa.getId(), getApplicationContext());
        PreferenceUtils.savePassword(password,getApplicationContext());
        PreferenceUtils.saveUsername(username, getApplicationContext());
        PreferenceUtils.saveIdSekolahSiswa(userSiswa.getSekolahid(), getApplicationContext());
        PreferenceUtils.saveIdKelas(userSiswa.getKelasid(), getApplicationContext());
        PreferenceUtils.saveFirstName(userSiswa.getFirstname(), getApplicationContext());
        PreferenceUtils.saveMidName(userSiswa.getMidname(), getApplicationContext());
        PreferenceUtils.saveLastName(userSiswa.getLastname(), getApplicationContext());
        PreferenceUtils.saveNis(userSiswa.getNis(), getApplicationContext());
        PreferenceUtils.saveJk(userSiswa.getGender(), getApplicationContext());
        PreferenceUtils.saveTtl(userSiswa.getTtl(), getApplicationContext());
        PreferenceUtils.saveReligion(userSiswa.getReligion(), getApplicationContext());
        PreferenceUtils.saveProvince(userSiswa.getProvince(), getApplicationContext());
        PreferenceUtils.saveCity(userSiswa.getCity(), getApplicationContext());
        PreferenceUtils.saveDistrict(userSiswa.getDistrict(), getApplicationContext());
        PreferenceUtils.saveVillage(userSiswa.getVillage(), getApplicationContext());
        PreferenceUtils.saveAddress(userSiswa.getAddress(), getApplicationContext());
        PreferenceUtils.savePostalCode(userSiswa.getPostalcode(), getApplicationContext());
        PreferenceUtils.saveTlp(userSiswa.getPhone(), getApplicationContext());
        PreferenceUtils.saveEmail(userSiswa.getEmail(), getApplicationContext());
        PreferenceUtils.savePhotoSiswa(userSiswa.getPhoto(), getApplicationContext());
        PreferenceUtils.saveStatus("murid", getApplicationContext());

        for (int x = 0; x < dataModelSekolah.getData().getMsSekolah().size(); x++) {
            if (userSiswa.getSekolahid().equalsIgnoreCase(dataModelSekolah.getData().getMsSekolah().get(x).getIdSekolah())) {
                dataSekolah = dataModelSekolah.getData().getMsSekolah().get(x);
                saveDataSekolahMurid();
            }
        }
    }

    public void saveDataSekolahGuru(){
        PreferenceUtils.saveSekolahId(dataSekolah.getIdSekolah(), getApplicationContext());
        PreferenceUtils.saveSekolahNama(dataSekolah.getNameSekolah(), getApplicationContext());
        PreferenceUtils.saveSekolahAlamat(dataSekolah.getAddress(), getApplicationContext());
        PreferenceUtils.saveSekolahEmail(dataSekolah.getEmail(), getApplicationContext());
        PreferenceUtils.saveSekolahPhone(dataSekolah.getPhone(), getApplicationContext());
        PreferenceUtils.saveSekolahWebsite(dataSekolah.getWebsite(), getApplicationContext());

            findViewById(R.id.framelayout).setVisibility(View.GONE);
            Intent x = new Intent(LoginActivity.this, HomeTeacher.class);
            startActivity(x);
            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
            finish();

    }

    public void saveDataSekolahMurid(){
        PreferenceUtils.saveSekolahId(dataSekolah.getIdSekolah(), getApplicationContext());
        PreferenceUtils.saveSekolahNama(dataSekolah.getNameSekolah(), getApplicationContext());
        PreferenceUtils.saveSekolahAlamat(dataSekolah.getAddress(), getApplicationContext());
        PreferenceUtils.saveSekolahEmail(dataSekolah.getEmail(), getApplicationContext());
        PreferenceUtils.saveSekolahPhone(dataSekolah.getPhone(), getApplicationContext());
        PreferenceUtils.saveSekolahWebsite(dataSekolah.getWebsite(), getApplicationContext());

        findViewById(R.id.framelayout).setVisibility(View.GONE);
        Intent x = new Intent(LoginActivity.this, HomeStudent.class);
        startActivity(x);
        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void getUser() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelUser> dataSiswax = apiInterface.getDataUser( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                dataModelUser = response.body();

                if (response.body()!=null){
                    getGuru();
                }
            }
            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {

                //Toast.makeText(LoginActivity.this, "Terjadi gangguan koneksi (getGuru)", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void getGuru() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelGuru> dataSiswax = apiInterface.getdataGuru( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelGuru>() {
            @Override
            public void onResponse(Call<ModelGuru> call, Response<ModelGuru> response) {
                dataModelGuru = response.body();

                if (response.body()!=null){
                    getSiswa();
                }
            }
            @Override
            public void onFailure(Call<ModelGuru> call, Throwable t) {

                //Toast.makeText(LoginActivity.this, "Terjadi gangguan koneksi (getGuru)", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void getSiswa() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSiswa> dataSiswax = apiInterface.getDataSiswa( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelSiswa>() {
            @Override
            public void onResponse(Call<ModelSiswa> call, Response<ModelSiswa> response) {

                dataModelSiswa = response.body();

                if (response.body()!=null){
                    getSekolah();
                }
            }
            @Override
            public void onFailure(Call<ModelSiswa> call, Throwable t) {

                //Toast.makeText(LoginActivity.this, "Terjadi gangguan koneksi (getSiswa)", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void getSekolah() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSekolah> dataSiswax = apiInterface.getDataSekolah( apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelSekolah>() {
            @Override
            public void onResponse(Call<ModelSekolah> call, Response<ModelSekolah> response) {

                dataModelSekolah = response.body();
                if (response.body()!=null){
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ModelSekolah> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

}
