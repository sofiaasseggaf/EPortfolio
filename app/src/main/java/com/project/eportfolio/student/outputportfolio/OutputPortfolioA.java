package com.project.eportfolio.student.outputportfolio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.R;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.student.MapelStudent;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class OutputPortfolioA extends AppCompatActivity {

    ImageView imgOutput;
    TextView namaOutput, sekolahOutput, kelasOutput, alamatOutput;
    String namasiswa;
    float x1, x2, y1, y2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_output_portfolio_a);

        imgOutput = findViewById(R.id.imgOutput);
        namaOutput = findViewById(R.id.namaOutput);
        kelasOutput = findViewById(R.id.kelasOutput);
        alamatOutput = findViewById(R.id.alamatOutput);

        setDataSiswa();

    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
                    Intent a = new Intent(OutputPortfolioA.this, OutputPortfolioB.class);
                    startActivity(a);
                    finish();
                } else if(x1 > x2){
                    Intent a = new Intent(OutputPortfolioA.this, HomeStudent.class);
                    startActivity(a);
                    finish();
                }
                break;
        }
        return false;
    }

    public void setDataSiswa(){

        namasiswa = PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext());
        namaOutput.setText(namasiswa);
        sekolahOutput.setText(PreferenceUtils.getSekolahNama(getApplicationContext()));
        kelasOutput.setText(PreferenceUtils.getKelasNama(getApplicationContext()));
        alamatOutput.setText(PreferenceUtils.getAddress(getApplicationContext()));
        try{
            if (!PreferenceUtils.getPhotoSiswa(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoSiswa(getApplicationContext())!=null){
                Picasso.get().load(PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imgOutput.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        //Toast.makeText(HomeStudent.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

            }
        } catch (Exception e){

        }



    }

    public void onBackPressed() {
        Intent a = new Intent(OutputPortfolioA.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}