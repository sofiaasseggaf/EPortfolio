package com.project.eportfolio.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.R;
import com.project.eportfolio.teacher.master.DataGuru;
import com.project.eportfolio.teacher.master.DataKelas;
import com.project.eportfolio.teacher.master.DataMapel;
import com.project.eportfolio.teacher.master.DataMurid;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.teacher.master.DataPortfolio;

public class MasterTeacher extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile;
    LinearLayout btnDataMurid, btnDataGuru, btnDataKelas, btnDataMapel, btnDataPortfolio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_master);

        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btnDataMurid = findViewById(R.id.btnDataMurid);
        btnDataGuru = findViewById(R.id.btnDataGuru);
        btnDataKelas = findViewById(R.id.btnDataKelas);
        btnDataMapel = findViewById(R.id.btnDataMapel);
        btnDataPortfolio = findViewById(R.id.btnDataPortfolio);

        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MasterTeacher.this, InputTeacher.class);
                startActivity(a);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MasterTeacher.this, ProfileTeacher.class);
                startActivity(a);
                finish();
            }
        });


        btnDataMurid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MasterTeacher.this, DataMurid.class);
                startActivity(a);
                finish();
            }
        });

        btnDataGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MasterTeacher.this, DataGuru.class);
                startActivity(a);
                finish();
            }
        });

        btnDataMapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MasterTeacher.this, DataMapel.class);
                startActivity(a);
                finish();
            }
        });

        btnDataKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MasterTeacher.this, DataKelas.class);
                startActivity(a);
                finish();
            }
        });

        btnDataPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MasterTeacher.this, DataPortfolio.class);
                startActivity(a);
                finish();
            }
        });

    }

    public void onBackPressed() {
        Intent a = new Intent(MasterTeacher.this, HomeTeacher.class);
        startActivity(a);
        finish();
    }
}
