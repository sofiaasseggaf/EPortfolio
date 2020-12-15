package com.project.studentportfolio.student;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.studentportfolio.R;
import com.project.studentportfolio.student.portfolio.ForumEdukasi;
import com.project.studentportfolio.student.portfolio.Karya;
import com.project.studentportfolio.student.portfolio.Organisasi;
import com.project.studentportfolio.student.portfolio.Penghargaan;
import com.project.studentportfolio.student.portfolio.Proyek;
import com.project.studentportfolio.student.portfolio.UnjukKerja;

public class PortfolioStudent extends AppCompatActivity {

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    LinearLayout btn_unjuk_kerja, btn_proyek, btn_karya, btn_penghargaan, btn_organisasi, btn_forum_edukasi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.student_portfolio);

        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);

        btn_unjuk_kerja = findViewById(R.id.btn_unjuk_kerja);
        btn_karya = findViewById(R.id.btn_karya);
        btn_proyek = findViewById(R.id.btn_proyek);
        btn_penghargaan = findViewById(R.id.btn_penghargaan);
        btn_organisasi = findViewById(R.id.btn_organisasi);
        btn_forum_edukasi = findViewById(R.id.btn_forum_edukasi);


        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, InputStudent.class);
                if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(PortfolioStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, ProfileStudent.class);
                if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(PortfolioStudent.this);
                    startActivity(a,options.toBundle());
                }else {
                    startActivity(a);
                    finish();
                }
            }
        });

        btn_unjuk_kerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, UnjukKerja.class);
                startActivity(a);
                finish();
            }
        });

        btn_proyek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, Proyek.class);
                startActivity(a);
                finish();
            }
        });

        btn_karya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, Karya.class);
                startActivity(a);
                finish();
            }
        });

        btn_forum_edukasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, ForumEdukasi.class);
                startActivity(a);
                finish();
            }
        });

        btn_organisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, Organisasi.class);
                startActivity(a);
                finish();
            }
        });

        btn_penghargaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, Penghargaan.class);
                startActivity(a);
                finish();
            }
        });
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
        Intent a = new Intent(PortfolioStudent.this, HomeStudent.class);
        if(Build.VERSION.SDK_INT>20){
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(PortfolioStudent.this);
            startActivity(a,options.toBundle());
        }else {
            startActivity(a);
            finish();
        }
    }
}