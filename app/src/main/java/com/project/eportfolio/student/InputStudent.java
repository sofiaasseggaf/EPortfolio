package com.project.eportfolio.student;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.APIService.AppUtil;
import com.project.eportfolio.R;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPostPortofolio;
import com.project.eportfolio.teacher.InputTeacher;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class InputStudent extends AppCompatActivity {

    //MsMurid dataSiswa;

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    Spinner sp_strategi;
    EditText txtJudul, txtTempat, txtNarasi;
    Button btnInputPortfolio, btnOpenCamera;
    ImageView imgPortofolio;

    String pathToFile;
    File photoFile;
    Bitmap foto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_input);

        if (Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        sp_strategi = findViewById(R.id.sp_strategi);
        txtJudul = findViewById(R.id.txtJudul);
        txtTempat = findViewById(R.id.txtTempat);
        txtNarasi = findViewById(R.id.txtNarasi);
        imgPortofolio = findViewById(R.id.imgPortofolio);

        btnInputPortfolio = findViewById(R.id.btnInputPortfolio);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        btn_beranda = findViewById(R.id.btn_home);
        btn_portfolio = findViewById(R.id.btn_portfolio);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);

        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               kePortfolio();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keProfile();
            }
        });

        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        btnInputPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendDataPortfolio();
                    }
                }).start();
            }
        });

    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = createPhotoFile();
            if (photoFile!=null){
                pathToFile= photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(InputStudent.this, "com.project.eportfolio.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, 2);
            }
        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image= null;
        try {
            image= File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e){
            Log.d("mylog", "Except  :" + e.toString());
        }
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Bitmap bitmap= BitmapFactory.decodeFile(pathToFile);
                imgPortofolio.setImageBitmap(bitmap);
                foto = bitmap;
            }
        }
    }

    private void sendDataPortfolio(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = formatter.format(new Date());

        File fotoo = createTempFile(foto);
        byte[] bImg1 = AppUtil.FiletoByteArray(fotoo);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
        MultipartBody.Part fotox = MultipartBody.Part.createFormData("foto", fotoo.getName() + ".jpg", requestFile1);

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelPostPortofolio> data = apiInterface.sendDataPortfolioSiswa(
                PreferenceUtils.getIdSiswa(getApplicationContext()),
                "0",
                "0",
                sp_strategi.getSelectedItem().toString(),
                "0",
                txtJudul.getText().toString(),
                now,
                txtTempat.getText().toString(),
                txtNarasi.getText().toString(),
                fotox,
                PreferenceUtils.getFirstName(getApplicationContext()),
                now,
                PreferenceUtils.getFirstName(getApplicationContext()),
                now
        );

        try {
            data.execute();
            if (data!=null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                        Toast.makeText(InputStudent.this, "Portfolio Berhasil di Input !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void keProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //HomeTeacher.super.onBackPressed();
                        Intent a = new Intent(InputStudent.this, ProfileStudent.class);
                        startActivity(a);
                        finish();
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void kePortfolio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //HomeTeacher.super.onBackPressed();
                        Intent a = new Intent(InputStudent.this, PortfolioStudent.class);
                        startActivity(a);
                        finish();
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //HomeTeacher.super.onBackPressed();
                        Intent a = new Intent(InputStudent.this, HomeStudent.class);
                        startActivity(a);
                        finish();
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
