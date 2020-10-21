package com.project.eportfolio.student;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
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
import androidx.core.app.ActivityCompat;
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
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPostPortofolio;
import com.project.eportfolio.teacher.InputTeacher;
import com.project.eportfolio.utility.FileCompressor;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    //String pathToFile;
    File photoFile, mPhotoFile;
    FileCompressor mCompressor;
    String mFileName;
    Bitmap foto;
    MultipartBody.Part fotox;
    //@BindView(R.id.imageViewProfilePic)

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
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


    /*
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

    private File createPhotoFile() throws  IOException {
        String name = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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
    */

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

   /* @OnClick(R.id.imageViewProfilePic)
    public void onViewClicked() {
        selectImage();
    }*/

    private void sendDataPortfolio(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = formatter.format(new Date());

       // File fotoo = createTempFile(foto);
        if (mPhotoFile!=null){
            byte[] bImg1 = AppUtil.FiletoByteArray(mPhotoFile);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
            fotox = MultipartBody.Part.createFormData("foto", mFileName + ".jpg", requestFile1);
        }

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
            if (data.execute()!=null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                        Toast.makeText(InputStudent.this, "Portfolio Berhasil di Input !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    Toast.makeText(InputStudent.this, "Portfolio Gagal di Input !", Toast.LENGTH_SHORT).show();
                }
            });
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