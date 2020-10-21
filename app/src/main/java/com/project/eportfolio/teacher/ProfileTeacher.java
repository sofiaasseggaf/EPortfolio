package com.project.eportfolio.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.LoginActivity;
import com.project.eportfolio.R;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.student.ProfileStudent;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ProfileTeacher extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile;
    TextView namaProfile, nipProfile, sekolahProfile;
    ImageView imgProfile;
    String namaguru;

    LinearLayout btnEditPrivacy, btnLogout, btnEditAccount, btnAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_profile);

        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);

        btnEditAccount = findViewById(R.id.btnEditAccount);
        btnEditPrivacy = findViewById(R.id.btnEditPrivacy);
        btnLogout = findViewById(R.id.btnLogout);
        btnAbout = findViewById(R.id.btnAbout);

        namaProfile = findViewById(R.id.namaProfile);
        imgProfile = findViewById(R.id.imgProfile);
        nipProfile = findViewById(R.id.nipProfile);
        sekolahProfile = findViewById(R.id.sekolahProfile);

        namaguru = PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext());
        namaProfile.setText(namaguru);
        nipProfile.setText(PreferenceUtils.getNip(getApplicationContext()));
        sekolahProfile.setText(PreferenceUtils.getSekolahNama(getApplicationContext()));
        if (!PreferenceUtils.getPhotoGuru(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoGuru(getApplicationContext())!= null){
            Picasso.get().load(PreferenceUtils.getPhotoGuru(getApplicationContext())).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imgProfile.setImageBitmap(bitmap);
                }
                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Toast.makeText(ProfileTeacher.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
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
                Intent a = new Intent(ProfileTeacher.this, MasterTeacher.class);
                startActivity(a);
                finish();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileTeacher.this, InputTeacher.class);
                startActivity(a);
                finish();
            }
        });

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileTeacher.this, ProfileTeacherSettingEdit.class);
                startActivity(a);
                finish();
            }
        });

        btnEditPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileTeacher.this, ProfileTeacherSettingPrivacy.class);
                startActivity(a);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup();

            }
        });
    }

    public void showPopup(){
        AlertDialog.Builder alert = new AlertDialog.Builder(ProfileTeacher.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        deleteDataGuru(); // Last step. Logout function

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    public void deleteDataGuru(){

        PreferenceUtils.saveIdGuru(null, getApplicationContext());
        PreferenceUtils.savePassword("", getApplicationContext());
        PreferenceUtils.saveUsername("", getApplicationContext());
        PreferenceUtils.saveIdSekolahGuru(null, getApplicationContext());
        PreferenceUtils.saveFirstName(null, getApplicationContext());
        PreferenceUtils.saveMidName(null, getApplicationContext());
        PreferenceUtils.saveLastName(null, getApplicationContext());
        PreferenceUtils.saveNik(null, getApplicationContext());
        PreferenceUtils.saveNip(null, getApplicationContext());
        PreferenceUtils.saveJk(null, getApplicationContext());
        PreferenceUtils.saveReligion(null, getApplicationContext());
        PreferenceUtils.saveProvince(null, getApplicationContext());
        PreferenceUtils.saveCity(null, getApplicationContext());
        PreferenceUtils.saveDistrict(null, getApplicationContext());
        PreferenceUtils.saveVillage(null, getApplicationContext());
        PreferenceUtils.saveAddress(null, getApplicationContext());
        PreferenceUtils.savePostalCode(null, getApplicationContext());
        PreferenceUtils.saveTlp(null, getApplicationContext());
        PreferenceUtils.saveEmail(null, getApplicationContext());
        PreferenceUtils.savePhotoGuru(null, getApplicationContext());
        PreferenceUtils.saveStatus("", getApplicationContext());

        deleteDataSekolah();
    }

    public void deleteDataSekolah(){

        PreferenceUtils.saveSekolahId(null, getApplicationContext());
        PreferenceUtils.saveSekolahNama(null, getApplicationContext());
        PreferenceUtils.saveSekolahAlamat(null, getApplicationContext());
        PreferenceUtils.saveSekolahEmail(null, getApplicationContext());
        PreferenceUtils.saveSekolahPhone(null, getApplicationContext());
        PreferenceUtils.saveSekolahWebsite(null, getApplicationContext());

        Intent a = new Intent(ProfileTeacher.this, LoginActivity.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        Intent a = new Intent(ProfileTeacher.this, HomeTeacher.class);
        startActivity(a);
        finish();
    }
}