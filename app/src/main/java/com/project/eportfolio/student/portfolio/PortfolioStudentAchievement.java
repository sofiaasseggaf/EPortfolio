package com.project.eportfolio.student.portfolio;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.R;
import com.project.eportfolio.student.HomeStudent;

public class PortfolioStudentAchievement extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_portfolio_achievement);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(PortfolioStudentAchievement.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}
