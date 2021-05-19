package com.project.eportfolio.student;


import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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
import com.project.eportfolio.student.portfolio.PortfolioStudentProject;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ProfileStudent extends AppCompatActivity {

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    LinearLayout btn_beranda2, btn_portfolio2, btn_input2, btn_profile2;
    TextView namaProfile;
    ImageView imgProfile;
    String namasiswa;

    LinearLayout btnEditPrivacy, btnLogout, btnEditAccount, btnAbout, btnContact, btnHelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAnimation();
        setContentView(R.layout.student_profile);

        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btn_beranda2 = findViewById(R.id.btn_home2);
        btn_portfolio2 = findViewById(R.id.btn_portfolio2);
        btn_input2 = findViewById(R.id.btn_input2);
        btn_profile2 = findViewById(R.id.btn_profile2);

        btnEditAccount = findViewById(R.id.btnEditAccount);
        btnEditPrivacy = findViewById(R.id.btnEditPrivacy);
        btnLogout = findViewById(R.id.btnLogout);
        btnAbout = findViewById(R.id.btnAbout);
        btnContact = findViewById(R.id.btnContact);
        btnHelp = findViewById(R.id.btnHelp);

        namaProfile = findViewById(R.id.namaProfile);
        //nisProfile = findViewById(R.id.nisProfile);
        //sekolahProfile = findViewById(R.id.sekolahProfile);
        imgProfile = findViewById(R.id.imgProfile);

        namasiswa = PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext());
        namaProfile.setText(namasiswa);
        //nisProfile.setText("NIS : "+PreferenceUtils.getNis(getApplicationContext()));
        //sekolahProfile.setText(PreferenceUtils.getSekolahNama(getApplicationContext()));
        try{
            if (!PreferenceUtils.getPhotoSiswa(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoSiswa(getApplicationContext())!= null){

                Picasso.get().load(PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imgProfile.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        //Toast.makeText(ProfileStudent.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

                /*
                Picasso.get().load("https://eportofolio.id/uploads/ms_murid/"+PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imgProfile.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        //Toast.makeText(ProfileStudent.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
                */

            }
        } catch (Exception e){

        }

        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileStudent.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();

            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileStudent.this, InputStudent.class);
                startActivity(a);
                finish();

            }
        });

        btn_beranda2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_portfolio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileStudent.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();

            }
        });

        btn_input2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileStudent.this, InputStudent.class);
                startActivity(a);
                finish();

            }
        });


        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileStudent.this, ProfileStudentSettingEdit.class);
                startActivity(a);
                finish();
                /*if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(ProfileStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }*/
            }
        });

        btnEditPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ProfileStudent.this, ProfileStudentSettingPrivacy.class);
                startActivity(a);
                finish();
                /*if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(ProfileStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }*/
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup();

            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileStudent.this, "Trial Version", Toast.LENGTH_SHORT).show();
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileStudent.this, "Trial Version", Toast.LENGTH_SHORT).show();
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileStudent.this, "Trial Version", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ProfileStudent.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        deleteDataMurid(); // Last step. Logout function

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    public void deleteDataMurid() {

        PreferenceUtils.saveIdSiswa(null, getApplicationContext());
        PreferenceUtils.saveUserId(null, getApplicationContext());
        PreferenceUtils.savePassword("", getApplicationContext());
        PreferenceUtils.saveUsername("", getApplicationContext());
        PreferenceUtils.saveIdSekolahSiswa(null, getApplicationContext());
        PreferenceUtils.saveIdKelas(null, getApplicationContext());
        PreferenceUtils.saveFirstName(null, getApplicationContext());
        PreferenceUtils.saveMidName(null, getApplicationContext());
        PreferenceUtils.saveLastName(null, getApplicationContext());
        PreferenceUtils.saveNis(null, getApplicationContext());
        PreferenceUtils.saveJk(null, getApplicationContext());
        PreferenceUtils.saveTtl(null, getApplicationContext());
        PreferenceUtils.saveReligion(null, getApplicationContext());
        PreferenceUtils.saveProvince(null, getApplicationContext());
        PreferenceUtils.saveCity(null, getApplicationContext());
        PreferenceUtils.saveDistrict(null, getApplicationContext());
        PreferenceUtils.saveVillage(null, getApplicationContext());
        PreferenceUtils.saveAddress(null, getApplicationContext());
        PreferenceUtils.savePostalCode(null, getApplicationContext());
        PreferenceUtils.saveTlp(null, getApplicationContext());
        PreferenceUtils.saveEmail(null, getApplicationContext());
        PreferenceUtils.savePhotoSiswa(null, getApplicationContext());
        PreferenceUtils.saveStatus("", getApplicationContext());

        deleteDataSekolah();
    }

    public void deleteDataSekolah() {

        PreferenceUtils.saveSekolahId(null, getApplicationContext());
        PreferenceUtils.saveSekolahNama(null, getApplicationContext());
        PreferenceUtils.saveSekolahAlamat(null, getApplicationContext());
        PreferenceUtils.saveSekolahEmail(null, getApplicationContext());
        PreferenceUtils.saveSekolahPhone(null, getApplicationContext());
        PreferenceUtils.saveSekolahWebsite(null, getApplicationContext());

        deleteDataKelas();

    }

    public void deleteDataKelas() {

        PreferenceUtils.saveKelasId(null, getApplicationContext());
        PreferenceUtils.saveKelasNama(null, getApplicationContext());

        Intent a = new Intent(ProfileStudent.this, LoginActivity.class);
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        finish();

    }

   /* public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(500);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }*/

    @Override
    public void onBackPressed() {
        Intent a = new Intent(ProfileStudent.this, HomeStudent.class);
        startActivity(a);
        finish();
        /*if(Build.VERSION.SDK_INT>20){
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(ProfileStudent.this);
            startActivity(a,options.toBundle());
        }else {
            startActivity(a);
            finish();
        }*/
    }

}