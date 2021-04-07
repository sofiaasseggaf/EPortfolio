package com.project.eportfolio.teacher.input;

import android.Manifest;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.project.eportfolio.model.kategoristrategi.ModelKategoriStrategi;
import com.project.eportfolio.model.kategoristrategi.MsKategoristrategi;
import com.project.eportfolio.model.kelas.ModelKelas;
import com.project.eportfolio.model.kelas.MsKela;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.matapelajaran.MsMatapelajaran;
import com.project.eportfolio.model.portfolio.ModelPostPortfolio;
import com.project.eportfolio.model.rubrik.ModelMasterRubrik;
import com.project.eportfolio.model.rubrik.MsRubrik;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.MsMurid;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.strategi.MsStrategi;
import com.project.eportfolio.teacher.HomeTeacher;
import com.project.eportfolio.teacher.MasterTeacher;
import com.project.eportfolio.teacher.ProfileTeacher;
import com.project.eportfolio.utility.FileCompressor;
import com.project.eportfolio.utility.PreferenceUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class InputTeacherD extends AppCompatActivity {

    ImageButton btn_beranda, btn_master, btn_input, btn_profile;
    ImageButton btnInputPortfolio, btnOpenCamera;
    ImageView imgPortofolio;
    String inputIdStrategi, inputIdKategori, inputTxtJudul, inputTahunAjaran, inputSemester;
    String inputIdKelas, inputNamaKelas, inputIdMapel, inputIdSiswa, inputIdRubrik, inputDeskRubrik;
    String inputNilai, inputPredikatMutu, inputTxtNarasi;

    File photoFile, mPhotoFile;
    FileCompressor mCompressor;
    String mFileName;
    MultipartBody.Part fotox;

    TextView txtload;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAnimation();
        setContentView(R.layout.teacher_input_d);

        ButterKnife.bind(this);
        mCompressor = new FileCompressor(this);

        btn_beranda = findViewById(R.id.btn_home);
        btn_master = findViewById(R.id.btn_master);
        btn_input = findViewById(R.id.btn_input);
        btn_profile = findViewById(R.id.btn_profile);
        btnInputPortfolio = findViewById(R.id.btnInputPortfolio);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        imgPortofolio = findViewById(R.id.imgPortofolio);
        txtload = findViewById(R.id.textloading);

        Intent intent = getIntent();
        inputIdStrategi = intent.getStringExtra("inputIdStrategi");
        inputIdKategori = intent.getStringExtra("inputIdKategori");
        inputTxtJudul = intent.getStringExtra("inputTxtJudul");
        inputTahunAjaran = intent.getStringExtra("inputTahunAjaran");
        inputSemester = intent.getStringExtra("inputSemester");
        inputIdKelas = intent.getStringExtra("inputIdKelas");
        inputNamaKelas = intent.getStringExtra("inputNamaKelas");
        inputIdMapel = intent.getStringExtra("inputIdMapel");
        inputIdSiswa = intent.getStringExtra("inputIdSiswa");
        inputIdRubrik = intent.getStringExtra("inputIdRubrik");
        inputDeskRubrik = intent.getStringExtra("inputDeskRubrik");
        inputNilai = intent.getStringExtra("inputNilai");
        inputPredikatMutu = intent.getStringExtra("inputPredikatMutu");
        inputTxtNarasi = intent.getStringExtra("inputTxtNarasi");


        btn_beranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keHome();
            }
        });
        btn_master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keMaster();
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
                        if (ContextCompat.checkSelfPermission(InputTeacherD.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            if  (ContextCompat.checkSelfPermission(InputTeacherD.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                if (ContextCompat.checkSelfPermission(InputTeacherD.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    selectImage();
                                }
                            }
                        } else {
                            Toast.makeText(InputTeacherD.this, "camera gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                }
            }
        });

        btnInputPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoFile==null) {
                    Toast.makeText(InputTeacherD.this, "Ambil Foto !", Toast.LENGTH_SHORT).show();
                } else {
                    thread();
                }
            }
        });

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


    // ============ CAMERA AND GALLERY THINGS ================

    private void selectImage() {
        final CharSequence[] items = {
                "Take Photo", "Choose from Library",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(InputTeacherD.this);
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
                Uri photoUri = FileProvider.getUriForFile(InputTeacherD.this, "com.project.eportfolio.fileprovider", photoFile);
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
                Glide.with(InputTeacherD.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgPortofolio);
                if(mPhotoFile!=null){
                    btnInputPortfolio.setBackgroundResource(R.drawable.btn_input_portfolio);
                }
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(InputTeacherD.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                        .circleCrop()
                                //.placeholder(R.drawable.profile_pic_place_holder))
                        )
                        .into(imgPortofolio);
                if(mPhotoFile!=null){
                    btnInputPortfolio.setBackgroundResource(R.drawable.btn_input_portfolio);
                }
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



    // =================== GET DATA ================


    private void sendDataPortfolio(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = formatter.format(new Date());

        if (mPhotoFile!=null){
            byte[] bImg1 = AppUtil.FiletoByteArray(mPhotoFile);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
            fotox = MultipartBody.Part.createFormData("foto", mFileName + ".jpg", requestFile1);
        }

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelPostPortfolio> data = apiInterface.sendDataPortfolioGuru(
                RequestBody.create(MediaType.parse("text/plain"),apikey),
                RequestBody.create(MediaType.parse("text/plain"),inputIdSiswa),
                RequestBody.create(MediaType.parse("text/plain"),PreferenceUtils.getUserId(getApplicationContext())),
                RequestBody.create(MediaType.parse("text/plain"),inputIdMapel),
                RequestBody.create(MediaType.parse("text/plain"),inputIdKategori),
                RequestBody.create(MediaType.parse("text/plain"),inputIdStrategi),
                RequestBody.create(MediaType.parse("text/plain"),inputIdRubrik),
                RequestBody.create(MediaType.parse("text/plain"),inputTxtJudul), //req
                RequestBody.create(MediaType.parse("text/plain"),inputDeskRubrik),
                RequestBody.create(MediaType.parse("text/plain"),now),
                RequestBody.create(MediaType.parse("text/plain"),"Sekolah"), // ganti kota
                RequestBody.create(MediaType.parse("text/plain"),inputNilai),
                RequestBody.create(MediaType.parse("text/plain"),inputPredikatMutu), // isi sesuai linear layout predikat yg dipilih
                RequestBody.create(MediaType.parse("text/plain"),inputTxtNarasi),
                fotox,
                RequestBody.create(MediaType.parse("text/plain"),inputNamaKelas),
                RequestBody.create(MediaType.parse("text/plain"),inputTahunAjaran),
                RequestBody.create(MediaType.parse("text/plain"),inputSemester),
                RequestBody.create(MediaType.parse("text/plain"),PreferenceUtils.getFirstName(getApplicationContext())),
                RequestBody.create(MediaType.parse("text/plain"),now),
                RequestBody.create(MediaType.parse("text/plain"),inputNilai), //req
                RequestBody.create(MediaType.parse("text/plain"),inputIdKelas) //req
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
                                Toast.makeText(InputTeacherD.this, "berhasil input portfolio", Toast.LENGTH_LONG).show();
                                imgPortofolio.setImageBitmap(null);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                Toast.makeText(InputTeacherD.this, "gagal input portfolio", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(InputTeacherD.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });

    }



    public void keHome(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherD.this, HomeTeacher.class);
                        startActivity(a);
                        finish();
                        /*if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }*/
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

    public void keProfile(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherD.this, ProfileTeacher.class);
                        startActivity(a);
                        finish();
                        /*if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }*/
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

    public void keMaster() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Input Portfolio ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherD.this, MasterTeacher.class);
                        startActivity(a);
                        finish();
                       /* if(Build.VERSION.SDK_INT>20){
                            ActivityOptions options =
                                    ActivityOptions.makeSceneTransitionAnimation(InputTeacher.this);
                            startActivity(a,options.toBundle());
                        }else {
                            startActivity(a);
                            finish();
                        }*/
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

    public void keInputB(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Input Siswa Selanjutnya ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent a = new Intent(InputTeacherD.this, InputTeacherB.class);
                        a.putExtra("inputIdKategori", inputIdKategori);
                        a.putExtra("inputIdStrategi", inputIdStrategi);
                        a.putExtra("inputTxtJudul", inputTxtJudul);
                        startActivity(a);
                        finish();
                    }
                })

                .setNegativeButton("SELESAI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Your Slide animation
   /* public void setAnimation(){
        if(Build.VERSION.SDK_INT>20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(500);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }*/

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Input Siswa Selanjutnya ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        keInputB();
                    }
                })

                .setNegativeButton("SELESAI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        keHome();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
