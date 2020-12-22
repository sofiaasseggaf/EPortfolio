package com.project.eportfolio.teacher;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.ModelUpdateDataGuru;
import com.project.eportfolio.model.guru.MsGuru;
import com.project.eportfolio.utility.FileCompressor;
import com.project.eportfolio.utility.PreferenceUtils;
import com.squareup.picasso.Picasso;

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

public class ProfileTeacherSettingEdit extends AppCompatActivity {

    LinearLayout btnSimpan;
    TextView btnUbahFotoProfile, btnSaveFotoProfile, namaGuru, nipGuru;
    EditText editFirstName, editMidName, editLastName, editNik, editNip, editJk, editEmail, editAlamat, editTelp;
    ImageView imgGuru;
    MsGuru dataGuru;
    Bitmap f;
    ModelGuru dataModelGuru;

    File photoFile, mPhotoFile;
    String mFileName;
    FileCompressor mCompressor;
    MultipartBody.Part fotox;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;


    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.teacher_profile_setting_edit);

        ButterKnife.bind(this);
        mCompressor = new FileCompressor(this);

        btnSimpan = findViewById(R.id.btnSimpan);
        namaGuru = findViewById(R.id.namaGuru);
        nipGuru = findViewById(R.id.nipGuru);
        btnUbahFotoProfile = findViewById(R.id.btnUbahFotoProfile);
        btnSaveFotoProfile = findViewById(R.id.btnSaveFotoProfile);
        imgGuru = findViewById(R.id.imgGuru);

        editFirstName = findViewById(R.id.editFirstName);
        editMidName = findViewById(R.id.editMiddleName);
        editLastName = findViewById(R.id.editLastName);
        editNik = findViewById(R.id.editNik);
        editNip = findViewById(R.id.editNip);
        editJk = findViewById(R.id.editJk);
        editAlamat = findViewById(R.id.editAlamat);
        editEmail = findViewById(R.id.editEmail);
        editTelp = findViewById(R.id.editTelp);

        setDataProfile();

        btnUbahFotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (Build.VERSION.SDK_INT>=23){
                        requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        if (ContextCompat.checkSelfPermission(ProfileTeacherSettingEdit.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            if  (ContextCompat.checkSelfPermission(ProfileTeacherSettingEdit.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                if (ContextCompat.checkSelfPermission(ProfileTeacherSettingEdit.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    selectImage();
                                }
                            }
                        } else {
                            Toast.makeText(ProfileTeacherSettingEdit.this, "camera gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                }
            }
        });

        btnSaveFotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileTeacherSettingEdit.this, "Trial Version", Toast.LENGTH_SHORT).show();
                //thread2();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       Toast.makeText(ProfileTeacherSettingEdit.this, "Trial Version", Toast.LENGTH_SHORT).show();
/*
                        if (editFirstName.getText().toString()!=null && editMidName.getText().toString()!=null  && editLastName.getText().toString()!=null  &&
                                editNip.getText().toString()!=null && editJk.getText().toString()!=null && editAlamat.getText().toString()!=null &&
                                editEmail.getText().toString()!=null && editTelp.getText().toString()!=null) {
                            thread();
                        } else {
                            Toast.makeText(ProfileTeacherSettingEdit.this, "Lengkapi Data Terlebih Dahulu !", Toast.LENGTH_SHORT).show();
                        }
                        */
                    }
        });

    }


    private void selectImage() {
        final CharSequence[] items = {
                "Take Photo", "Choose from Library",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileTeacherSettingEdit.this);
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
                Uri photoUri = FileProvider.getUriForFile(ProfileTeacherSettingEdit.this, "com.project.eportfolio.fileprovider", photoFile);
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
                Glide.with(ProfileTeacherSettingEdit.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgGuru);
                btnSaveFotoProfile.setClickable(true);
                btnSaveFotoProfile.setTextColor((getResources().getColor(R.color.blue)));

            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(ProfileTeacherSettingEdit.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgGuru);
                btnSaveFotoProfile.setClickable(true);
                btnSaveFotoProfile.setTextColor((getResources().getColor(R.color.blue)));
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



    public void setDataProfile(){

        btnSaveFotoProfile.setClickable(false);
        btnSaveFotoProfile.setTextColor((getResources().getColor(R.color.colorFont2)));

        namaGuru.setText(PreferenceUtils.getFirstName(getApplicationContext()) + " " +
                PreferenceUtils.getMidName(getApplicationContext()) + " " +
                PreferenceUtils.getLastName(getApplicationContext()));
        nipGuru.setText(PreferenceUtils.getNip(getApplicationContext()));

        try{
            Picasso.get().load("https://eportofolio.id/uploads/ms_guru/"+PreferenceUtils.getPhotoGuru(getApplicationContext())).into(imgGuru);
        } catch (Exception e){
            e.printStackTrace();
        }

        editFirstName.setText(PreferenceUtils.getFirstName(getApplicationContext()));
        editMidName.setText(PreferenceUtils.getMidName(getApplicationContext()));
        editLastName.setText(PreferenceUtils.getLastName(getApplicationContext()));
        editNik.setText(PreferenceUtils.getNik(getApplicationContext()));
        editNip.setText(PreferenceUtils.getNip(getApplicationContext()));
        editJk.setText(PreferenceUtils.getJk(getApplicationContext()));
        editAlamat.setText(PreferenceUtils.getAddress(getApplicationContext()));
        editEmail.setText(PreferenceUtils.getEmail(getApplicationContext()));
        editTelp.setText(PreferenceUtils.getTlp(getApplicationContext()));
    }

    private void thread() {

        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDataGuruRequired();
            }
        }).start();
    }

    private void thread2() {

        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDataGuruFoto();
            }
        }).start();
    }


    private void updateDataGuruRequired(){

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataGuru> postAdd = apiInterface.updateDataGuruRequired(

                apikey,
                PreferenceUtils.getIdGuru(getApplicationContext()),
                PreferenceUtils.getUserId(getApplicationContext()),
                PreferenceUtils.getIdSekolahGuru(getApplicationContext()),
                editFirstName.getText().toString(),
                editMidName.getText().toString(),
                editLastName.getText().toString(),
                editNik.getText().toString(),
                editNip.getText().toString(),
                editJk.getText().toString(),
                editAlamat.getText().toString(),
                editTelp.getText().toString(),
                editEmail.getText().toString(),
                editFirstName.getText().toString(),
                "0"
        );


        postAdd.enqueue(new Callback<ModelUpdateDataGuru>() {
            @Override
            public void onResponse(Call<ModelUpdateDataGuru> call, Response<ModelUpdateDataGuru> response) {
                getGuru();
            }

            @Override
            public void onFailure(Call<ModelUpdateDataGuru> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProfileTeacherSettingEdit.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    private void updateDataGuruFoto(){

        if (mPhotoFile!=null){
            byte[] bImg1 = AppUtil.FiletoByteArray(mPhotoFile);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
            fotox = MultipartBody.Part.createFormData("photo", mFileName + ".jpg", requestFile1);
        }

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelUpdateDataGuru> postAdd = apiInterface.updateDataGuruFoto(

                apikey,
                PreferenceUtils.getIdGuru(getApplicationContext()),
                PreferenceUtils.getUserId(getApplicationContext()),
                PreferenceUtils.getIdSekolahGuru(getApplicationContext()),
                PreferenceUtils.getFirstName(getApplicationContext()),
                PreferenceUtils.getMidName(getApplicationContext()),
                PreferenceUtils.getLastName(getApplicationContext()),
                PreferenceUtils.getNik(getApplicationContext()),
                PreferenceUtils.getNip(getApplicationContext()),
                PreferenceUtils.getJk(getApplicationContext()),
                PreferenceUtils.getAddress(getApplicationContext()),
                PreferenceUtils.getEmail(getApplicationContext()),
                PreferenceUtils.getTlp(getApplicationContext()),
                fotox
        );


        postAdd.enqueue(new Callback<ModelUpdateDataGuru>() {
            @Override
            public void onResponse(Call<ModelUpdateDataGuru> call, Response<ModelUpdateDataGuru> response) {
                getGuru();
            }

            @Override
            public void onFailure(Call<ModelUpdateDataGuru> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProfileTeacherSettingEdit.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }


    public void getGuru() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelGuru> dataSiswax = apiInterface.getdataGuru(apikey, 1000);

        try {
            Response<ModelGuru> response = dataSiswax.execute();
            dataModelGuru = response.body();
            for (int a = 0; a < dataModelGuru.getData().getMsGuru().size(); a++) {
                if (PreferenceUtils.getIdGuru(getApplicationContext()).equalsIgnoreCase(dataModelGuru.getData().getMsGuru().get(a).getIdGuru())) {
                    dataGuru = dataModelGuru.getData().getMsGuru().get(a);
                    saveDataGuru();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDataGuru(){

        PreferenceUtils.saveFirstName(dataGuru.getFirstname(), getApplicationContext());
        PreferenceUtils.saveMidName(dataGuru.getMidname(), getApplicationContext());
        PreferenceUtils.saveLastName(dataGuru.getLastname(), getApplicationContext());
        PreferenceUtils.saveNik(dataGuru.getNik(), getApplicationContext());
        PreferenceUtils.saveNip(dataGuru.getNip(), getApplicationContext());
        PreferenceUtils.saveJk(dataGuru.getGender(), getApplicationContext());
        PreferenceUtils.saveAddress(dataGuru.getAddress(), getApplicationContext());
        PreferenceUtils.saveEmail(dataGuru.getEmail(), getApplicationContext());
        PreferenceUtils.saveTlp(dataGuru.getPhone(), getApplicationContext());
        PreferenceUtils.savePhotoGuru(dataGuru.getPhoto(), getApplicationContext());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.framelayout).setVisibility(View.GONE);
                Toast.makeText(ProfileTeacherSettingEdit.this, "Data Selesai di Input", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(ProfileTeacherSettingEdit.this, ProfileTeacherSettingEdit.class);
                startActivity(a);
                finish();
            }
        });
    }


    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(500);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }

    public void onBackPressed() {
        Intent a = new Intent(ProfileTeacherSettingEdit.this, HomeTeacher.class);
        if(Build.VERSION.SDK_INT>20){
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(ProfileTeacherSettingEdit.this);
            startActivity(a,options.toBundle());
        }else {
            startActivity(a);
            finish();
        }
    }
}
