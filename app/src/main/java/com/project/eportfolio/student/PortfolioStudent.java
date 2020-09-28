package com.project.eportfolio.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.R;
import com.project.eportfolio.student.portfolio.ForumEdukasi;
import com.project.eportfolio.student.portfolio.Karya;
import com.project.eportfolio.student.portfolio.Organisasi;
import com.project.eportfolio.student.portfolio.Penghargaan;
import com.project.eportfolio.student.portfolio.Proyek;
import com.project.eportfolio.student.portfolio.UnjukKerja;

public class PortfolioStudent extends AppCompatActivity {

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    LinearLayout btn_unjuk_kerja, btn_proyek, btn_karya, btn_penghargaan, btn_organisasi, btn_forum_edukasi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                startActivity(a);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudent.this, ProfileStudent.class);
                startActivity(a);
                finish();
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


    @Override
    public void onBackPressed() {
        Intent a = new Intent(PortfolioStudent.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}