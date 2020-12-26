package com.project.eportfolio.student;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.APIService.AppUtil;
import com.project.eportfolio.R;
//import com.project.eportfolio.utility.FileCompressor;
import com.project.eportfolio.model.portfolio.ModelPostPortfolio;
import com.project.eportfolio.teacher.InputTeacher;
import com.project.eportfolio.utility.FileCompressor;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static okhttp3.internal.http.HttpDate.format;

public class InputStudent extends AppCompatActivity {

    ImageButton btn_beranda, btn_portfolio, btn_input, btn_profile;
    Spinner sp_strategi;
    EditText txtJudul, txtTempat, txtNarasi;
    Button btnInputPortfolio, btnOpenCamera;
    ImageView imgPortofolio;
    TextView txtload;
    int idkategori;

    File photoFile, mPhotoFile;
    FileCompressor mCompressor;
    String mFileName;
    MultipartBody.Part fotox;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.student_input);

        ButterKnife.bind(this);
        mCompressor = new FileCompressor(this);

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

        txtload = findViewById(R.id.textloading);

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
                try{
                    if (Build.VERSION.SDK_INT>=23){
                        requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        if (ContextCompat.checkSelfPermission(InputStudent.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            if  (ContextCompat.checkSelfPermission(InputStudent.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                if (ContextCompat.checkSelfPermission(InputStudent.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    selectImage();
                                }
                            }
                        } else {
                            Toast.makeText(InputStudent.this, "camera gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                }
            }
        });

        btnInputPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtJudul.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(InputStudent.this, "Lengkapi Judul KD !", Toast.LENGTH_SHORT).show();
                } else {
                    thread();
                }
            }
        });
/*
        sp_strategi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (sp_strategi.getSelectedItem().toString().equalsIgnoreCase("Organisasi")) {
                    idkategori = 4;
                } else if (sp_strategi.getSelectedItem().toString().equalsIgnoreCase("Penghargaan")) {
                    idkategori = 5;
                } else if (sp_strategi.getSelectedItem().toString().equalsIgnoreCase("Forum Edukasi")) {
                    idkategori = 6;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
    }

    private void thread() {

        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            int count = 0;

            @Override
            public void run() {
                count++;

                if (count == 1)
                {
                    txtload.setText("Sending Portfolio .");
                }
                else if (count == 2)
                {
                    txtload.setText("Sending Portfolio . .");
                }
                else if (count == 3)
                {
                    txtload.setText("Sending Portfolio . . .");
                }

                if (count == 3)
                    count = 0;

                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                sendDataPortfolio();
            }
        }).start();
    }

    private void selectImage() {
        final CharSequence[] items = {
                "Take Photo", "Choose from Library",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(InputStudent.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Choose from Library")) {
                requestStoragePermission(false);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void requestStoragePermission(boolean isCamera) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(
                        error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT)
                                .show())
                .onSameThread()
                .check();
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try{
                photoFile = createImageFile();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            if (photoFile!=null){
                //pathToFile = photoFile.getAbsolutePath();
                mPhotoFile = photoFile;
                Uri photoUri = FileProvider.getUriForFile(InputStudent.this, "com.project.eportfolio.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage(
                "This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(InputStudent.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgPortofolio);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(InputStudent.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgPortofolio);
            }
        }
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private void sendDataPortfolio() {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String now = formatter.format(new Date());

        SimpleDateFormat foror = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
        String tgl = foror.format(new Date());

        if (mPhotoFile!=null){
            byte[] bImg1 = AppUtil.FiletoByteArray(mPhotoFile);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
            fotox = MultipartBody.Part.createFormData("foto", PreferenceUtils.getFirstName(getApplicationContext())+
                    "_" + tgl + ".jpg", requestFile1);
        }

       /* if (sp_strategi.getSelectedItem().toString().equalsIgnoreCase("Organisasi")) {
            idkategori = 4;
        } else if (sp_strategi.getSelectedItem().toString().equalsIgnoreCase("Penghargaan")) {
            idkategori = 5;
        } else if (sp_strategi.getSelectedItem().toString().equalsIgnoreCase("Forum Edukasi")) {
            idkategori = 6;
        }*/

            APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
            Call<ModelPostPortfolio> data = apiInterface.sendDataPortfolioSiswa(
                    apikey,
                    Integer.parseInt(PreferenceUtils.getIdSiswa(getApplicationContext())),
                    Integer.parseInt("0"),
                    txtJudul.getText().toString(),
                    now,
                    txtTempat.getText().toString(),
                    txtNarasi.getText().toString(),
                    fotox,
                    PreferenceUtils.getFirstName(getApplicationContext()),
                    now,
                    Integer.parseInt("100"),
                    Integer.parseInt(PreferenceUtils.getIdKelas(getApplicationContext()))
            );

            data.enqueue(new Callback<ModelPostPortfolio>() {
                @Override
                public void onResponse(Call<ModelPostPortfolio> call, Response<ModelPostPortfolio> response) {
                    try {
                        if (response.body() != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(InputStudent.this, "berhasil input portfolio", Toast.LENGTH_LONG).show();
                                    imgPortofolio.setImageBitmap(null);
                                    txtJudul.setText("");
                                    txtNarasi.setText("");
                                    txtTempat.setText("");
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                                    Toast.makeText(InputStudent.this, "gagal input portfolio", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (Exception a){
                        a.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ModelPostPortfolio> call, Throwable t) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            Toast.makeText(InputStudent.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                        }
                    });
                    call.cancel();
                }
            });






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
                        if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputStudent.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }
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
                        if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputStudent.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //HomeTeacher.super.onBackPressed();
                        Intent a = new Intent(InputStudent.this, HomeStudent.class);
                        if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputStudent.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }
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
