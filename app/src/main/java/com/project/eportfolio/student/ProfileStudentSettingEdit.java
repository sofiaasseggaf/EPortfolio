package com.project.eportfolio.student;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.ModelUpdateDataSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.utility.FileCompressor;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileStudentSettingEdit extends AppCompatActivity {

    LinearLayout btnSimpan;
    TextView btnUbahFotoProfile, namaSiswa, nisSiswa;
    EditText editFirstName, editMidName, editLastName, editTtl, editNis, editJk, editAlamat, editEmail, editTelp;
    ImageView imgSiswa;
    ModelSiswa dataModelSiswa;
    MsMurid dataSiswa;
    // mPhotoFile itu file yg dipake buat update data
    // mPhotofile bisa diambil dari foto awal di ImageVIew pake void createTempFile
    // mPhotofile bisa diambil dari camera/galery
    File photoFile, mPhotoFile;
    String mFileName;
    FileCompressor mCompressor;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    String apikey = "E0DBFA6CBB4C2F63E0ABBB25C4CE3CA3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.student_profile_setting_edit);

        ButterKnife.bind(this);
        mCompressor = new FileCompressor(this);

        namaSiswa = findViewById(R.id.namaSiswa);
        nisSiswa = findViewById(R.id.nisSiswa);
        btnUbahFotoProfile = findViewById(R.id.btnUbahFotoProfile);
        imgSiswa = findViewById(R.id.fotoSiswa);
        btnSimpan = findViewById(R.id.btnSimpan);

        editFirstName = findViewById(R.id.editFirstName);
        editMidName = findViewById(R.id.editMiddleName);
        editLastName = findViewById(R.id.editLastName);
        editTtl = findViewById(R.id.editTtl);
        editNis = findViewById(R.id.editNis);
        editJk = findViewById(R.id.editJk);
        editAlamat = findViewById(R.id.editAlamat);
        editEmail = findViewById(R.id.editEmail);
        editTelp = findViewById(R.id.editTelp);

        setDataProfile();

        btnUbahFotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileStudentSettingEdit.this, "Trial Version", Toast.LENGTH_SHORT).show();
                /*try{
                    if (Build.VERSION.SDK_INT>=23){
                        requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        if (ContextCompat.checkSelfPermission(ProfileStudentSettingEdit.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            if  (ContextCompat.checkSelfPermission(ProfileStudentSettingEdit.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                if (ContextCompat.checkSelfPermission(ProfileStudentSettingEdit.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    selectImage();
                                }
                            }
                        } else {
                            Toast.makeText(ProfileStudentSettingEdit.this, "camera gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                }*/
            }
        });


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editFirstName.getText().toString().equalsIgnoreCase("") && !editMidName.getText().toString().equalsIgnoreCase("")  && !editLastName.getText().toString().equalsIgnoreCase("")  &&
                        !editTtl.getText().toString().equalsIgnoreCase("") && !editJk.getText().toString().equalsIgnoreCase("") && !editAlamat.getText().toString().equalsIgnoreCase("") &&
                        !editEmail.getText().toString().equalsIgnoreCase("") && !editTelp.getText().toString().equalsIgnoreCase("")
                        //&& mPhotoFile!=null
                ) {
                    thread();
                } else {
                    Toast.makeText(ProfileStudentSettingEdit.this, "Lengkapi Data Terlebih Dahulu !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setDataProfile(){

        namaSiswa.setText(PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext()));
        nisSiswa.setText("NIS : "+PreferenceUtils.getNis(getApplicationContext()));
        try {
            if (!PreferenceUtils.getPhotoSiswa(getApplicationContext()).equalsIgnoreCase("") || PreferenceUtils.getPhotoSiswa(getApplicationContext())!= null){
                Picasso.get().load("https://eportofolio.id/uploads/ms_murid/"+PreferenceUtils.getPhotoSiswa(getApplicationContext())).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imgSiswa.setImageBitmap(bitmap);
                        mPhotoFile = createTempFile(bitmap);
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(ProfileStudentSettingEdit.this, "Please Upload Your Photo", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            }
        } catch (Exception e){

        }

        editFirstName.setText(PreferenceUtils.getFirstName(getApplicationContext()));
        editMidName.setText(PreferenceUtils.getMidName(getApplicationContext()));
        editLastName.setText(PreferenceUtils.getLastName(getApplicationContext()));
        editTtl.setText(PreferenceUtils.getTtl(getApplicationContext()));
        editNis.setText(PreferenceUtils.getNis(getApplicationContext()));
        editJk.setText(PreferenceUtils.getJk(getApplicationContext()));
        editAlamat.setText(PreferenceUtils.getAddress(getApplicationContext()));
        editEmail.setText(PreferenceUtils.getEmail(getApplicationContext()));
        editTelp.setText(PreferenceUtils.getTlp(getApplicationContext()));

    }

    private void selectImage() {
        final CharSequence[] items = {
                "Take Photo", "Choose from Library",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileStudentSettingEdit.this);
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
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                Uri photoUri = FileProvider.getUriForFile(ProfileStudentSettingEdit.this, "com.project.eportfolio.fileprovider", photoFile);
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
                Glide.with(ProfileStudentSettingEdit.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgSiswa);

            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(ProfileStudentSettingEdit.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgSiswa);
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

    private void thread() {
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDataSiswaFoto();
            }
        }).start();
    }

    //bitmap to file
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

    //send post data with image
    private void updateDataSiswaFoto(){


       // byte[] bImg1 = AppUtil.FiletoByteArray(mPhotoFile);
       // RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
       // MultipartBody.Part fotox = MultipartBody.Part.createFormData("photo", PreferenceUtils.getFirstName(getApplicationContext())+".jpg", requestFile1);

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataSiswa> postAdd = apiInterface.updateDataSiswaRequired(
                apikey,
                PreferenceUtils.getIdSiswa(getApplicationContext()),
                PreferenceUtils.getUserId(getApplicationContext()),
                PreferenceUtils.getIdSekolahSiswa(getApplicationContext()),
                PreferenceUtils.getIdKelas(getApplicationContext()),
                editFirstName.getText().toString(),
                editMidName.getText().toString(),
                editLastName.getText().toString(),
                editNis.getText().toString(),
                editJk.getText().toString(),
                editTtl.getText().toString(),
                editAlamat.getText().toString(),
                editEmail.getText().toString(),
                editTelp.getText().toString(),
               // fotox,
                editFirstName.getText().toString(),
                "0"
        );

        postAdd.enqueue(new Callback<ModelUpdateDataSiswa>() {
            @Override
            public void onResponse(Call<ModelUpdateDataSiswa> call, Response<ModelUpdateDataSiswa> response) {

                if (response.isSuccessful() ) {
                    try {
                        getSiswa();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ProfileStudentSettingEdit.this, "Gagal Update Data", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ModelUpdateDataSiswa> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProfileStudentSettingEdit.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });

        /*try {
            postAdd.execute();
            getSiswa();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void getSiswa() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSiswa> dataSiswax = apiInterface.getDataSiswa(apikey, 1000);

        dataSiswax.enqueue(new Callback<ModelSiswa>() {
            @Override
            public void onResponse(Call<ModelSiswa> call, Response<ModelSiswa> response) {

                dataModelSiswa = response.body();

                if (response.body()!=null){

                    for (int a = 0; a < dataModelSiswa.getTotal(); a++) {
                        try {
                            if (PreferenceUtils.getIdSiswa(getApplicationContext()).equalsIgnoreCase(dataModelSiswa.getData().getMsMurid().get(a).getId())) {
                                dataSiswa = dataModelSiswa.getData().getMsMurid().get(a);
                                saveDataMurid();
                            }
                        } catch (Exception e){
                        }
                    }

                }
            }
            @Override
            public void onFailure(Call<ModelSiswa> call, Throwable t) {

                //Toast.makeText(LoginActivity.this, "Terjadi gangguan koneksi (getSiswa)", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });


    }

    public void saveDataMurid(){

        PreferenceUtils.saveFirstName(dataSiswa.getFirstname(), getApplicationContext());
        PreferenceUtils.saveMidName(dataSiswa.getMidname(), getApplicationContext());
        PreferenceUtils.saveLastName(dataSiswa.getLastname(), getApplicationContext());
        PreferenceUtils.saveTtl(dataSiswa.getTtl(), getApplicationContext());
        PreferenceUtils.saveNis(dataSiswa.getNis(), getApplicationContext());
        PreferenceUtils.saveJk(dataSiswa.getGender(), getApplicationContext());
        PreferenceUtils.saveAddress(dataSiswa.getAddress(), getApplicationContext());
        PreferenceUtils.saveEmail(dataSiswa.getEmail(), getApplicationContext());
        PreferenceUtils.saveTlp(dataSiswa.getPhone(), getApplicationContext());
        PreferenceUtils.savePhotoSiswa(dataSiswa.getPhoto(), getApplicationContext());


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(ProfileStudentSettingEdit.this, "Update Berhasil", Toast.LENGTH_LONG).show();
                Intent a = new Intent(ProfileStudentSettingEdit.this, ProfileStudent.class);
                startActivity(a);
                finish();
            }
        });
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


    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Edit Profile ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //HomeTeacher.super.onBackPressed();
                        Intent a = new Intent(ProfileStudentSettingEdit.this, ProfileStudent.class);
                        if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(ProfileStudentSettingEdit.this);
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
