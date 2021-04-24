package com.project.eportfolio.student.outputportfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.project.eportfolio.R;
import com.project.eportfolio.student.HomeStudent;

public class OutputPortfolioD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_output_portfolio_d);
    }

    public void onBackPressed() {
        Intent a = new Intent(OutputPortfolioD.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}