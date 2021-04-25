package com.project.eportfolio.student.outputportfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.project.eportfolio.R;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.utility.OnSwipeTouchListener;

public class OutputPortfolioD extends AppCompatActivity {

    OnSwipeTouchListener onSwipeTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_output_portfolio_d);

        onSwipeTouchListener = new OnSwipeTouchListener(OutputPortfolioD.this) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                Intent a = new Intent(OutputPortfolioD.this, OutputPortfolioC.class);
                startActivity(a);
                finish();
            }
            public void onSwipeLeft() {

            }
            public void onSwipeBottom() {

            }
        };



    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public void onBackPressed() {
        Intent a = new Intent(OutputPortfolioD.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}