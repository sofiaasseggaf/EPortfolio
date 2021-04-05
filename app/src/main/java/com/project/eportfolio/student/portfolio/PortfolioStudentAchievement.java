package com.project.eportfolio.student.portfolio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.R;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.student.InputStudent;
import com.project.eportfolio.student.ProfileStudent;

public class PortfolioStudentAchievement extends AppCompatActivity {

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    LinearLayout btn_ll_a, btn_ll_k, btn_ll_p, btn_ll_uk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_portfolio_achievement);

        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btn_ll_a = findViewById(R.id.btn_ll_a);
        btn_ll_k = findViewById(R.id.btn_ll_k);
        btn_ll_p = findViewById(R.id.btn_ll_p);
        btn_ll_uk = findViewById(R.id.btn_ll_uk);

        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, HomeStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, InputStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, ProfileStudent.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, PortfolioStudentProject.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, PortfolioStudentKarya.class);
                startActivity(a);
                finish();
            }
        });

        btn_ll_uk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PortfolioStudentAchievement.this, PortfolioStudentUnjukKerja.class);
                startActivity(a);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(PortfolioStudentAchievement.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}
