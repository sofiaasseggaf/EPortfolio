package com.project.eportfolio.student.outputportfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.eportfolio.APIService.APIClient;
import com.project.eportfolio.APIService.APIInterfacesRest;
import com.project.eportfolio.R;
import com.project.eportfolio.model.gradesekolah.ModelGradeSekolah;
import com.project.eportfolio.model.gradesekolah.MsGradeSekolah;
import com.project.eportfolio.model.rubrik.ModelMasterRubrik;
import com.project.eportfolio.model.rubrik.MsRubrik;
import com.project.eportfolio.student.HomeStudent;
import com.project.eportfolio.teacher.input.InputTeacherC;
import com.project.eportfolio.utility.OnSwipeTouchListener;
import com.project.eportfolio.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutputPortfolioC extends AppCompatActivity {

    TextView outputRubrikA, outputRubrikB, outputRubrikC, outputRubrikD;
    TextView outputNilaiA, outputNilaiB, outputNilaiC, outputNilaiD;
    TextView outputPredikatA, outputPredikatB, outputPredikatC, outputPredikatD;
    String n1, n2, n3, n4, p1, p2, p3, p4;
    OnSwipeTouchListener onSwipeTouchListener;

    ModelMasterRubrik dataMasterRubrik;
    List<MsRubrik> listRubrik = new ArrayList<>();
    ModelGradeSekolah dataGradeSekolah;
    List<MsGradeSekolah> listGradeSekolah = new ArrayList<>();

    String apikey = "7826470ABBA476706DB24D47C6A6ED64";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_output_portfolio_c);

        outputNilaiA = findViewById(R.id.outputNilaiA);
        outputNilaiB = findViewById(R.id.outputNilaiB);
        outputNilaiC = findViewById(R.id.outputNilaiC);
        outputNilaiD = findViewById(R.id.outputNilaiD);

        outputRubrikA = findViewById(R.id.outputRubrikA);
        outputRubrikB = findViewById(R.id.outputRubrikB);
        outputRubrikC = findViewById(R.id.outputRubrikC);
        outputRubrikD = findViewById(R.id.outputRubrikD);

        outputPredikatA = findViewById(R.id.outputPredikatA);
        outputPredikatB = findViewById(R.id.outputPredikatB);
        outputPredikatC = findViewById(R.id.outputPredikatC);
        outputPredikatD = findViewById(R.id.outputPredikatD);

        first();

        onSwipeTouchListener = new OnSwipeTouchListener(OutputPortfolioC.this) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                Intent a = new Intent(OutputPortfolioC.this, OutputPortfolioA.class);
                startActivity(a);
                finish();
            }
            public void onSwipeLeft() {
                Intent a = new Intent(OutputPortfolioC.this, OutputPortfolioB.class);
                startActivity(a);
                finish();
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

    private void first(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        getRubrik();
    }

    public void getRubrik() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelMasterRubrik> dataSiswax = apiInterface.getDataMasterRubrik(  apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelMasterRubrik>() {
            @Override
            public void onResponse(Call<ModelMasterRubrik> call, Response<ModelMasterRubrik> response) {
                dataMasterRubrik = response.body();
                if (response.body()!=null){

                    getGradeSekolah();

                    /*
                    listRubrik.clear();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);


                            if (!inputIdStrategi.equalsIgnoreCase("") || inputIdStrategi!=null){
                                try{
                                    for(int i=0; i<dataMasterRubrik.getTotal(); i++){
                                        if (dataMasterRubrik.getData().getMsRubrik().get(i).getStrategiid().equalsIgnoreCase(inputIdStrategi)) {
                                            listRubrik.add(dataMasterRubrik.getData().getMsRubrik().get(i));
                                        }
                                    }

                                    if (listRubrik!=null){
                                        for (int x=0; x<listRubrik.size(); x++){
                                            if (listRubrik.get(x).getNameRubrik().equalsIgnoreCase("POIN 1")){
                                                idRubrik1 = listRubrik.get(x).getIdRubrik();
                                                deskRubrik1 = listRubrik.get(x).getDescRubrik();
                                                txtPoint1.setText(deskRubrik1);
                                            } else if (listRubrik.get(x).getNameRubrik().equalsIgnoreCase("POIN 2")){
                                                idRubrik2 = listRubrik.get(x).getIdRubrik();
                                                deskRubrik2 = listRubrik.get(x).getDescRubrik();
                                                txtPoint2.setText(deskRubrik2);
                                            } else if (listRubrik.get(x).getNameRubrik().equalsIgnoreCase("POIN 3")){
                                                idRubrik3 = listRubrik.get(x).getIdRubrik();
                                                deskRubrik3 = listRubrik.get(x).getDescRubrik();
                                                txtPoint3.setText(deskRubrik3);
                                            } else if (listRubrik.get(x).getNameRubrik().equalsIgnoreCase("POIN 4")){
                                                idRubrik4 = listRubrik.get(x).getIdRubrik();
                                                deskRubrik4 = listRubrik.get(x).getDescRubrik();
                                                txtPoint4.setText(deskRubrik4);
                                            }
                                        }
                                    }

                                } catch (Exception a){ }
                            }


                        }
                    });

                    */

                }
            }
            @Override
            public void onFailure(Call<ModelMasterRubrik> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OutputPortfolioC.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getGradeSekolah() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelGradeSekolah> dataSiswax = apiInterface.getDataGradeSekolah(  apikey, 1000);
        dataSiswax.enqueue(new Callback<ModelGradeSekolah>() {
            @Override
            public void onResponse(Call<ModelGradeSekolah> call, Response<ModelGradeSekolah> response) {
                dataGradeSekolah = response.body();
                if (response.body()!=null){
                    listGradeSekolah.clear();

                    try {
                        for (int i=0; i<dataGradeSekolah.getTotal(); i++) {
                            if(dataGradeSekolah.getData().getMsGradeSekolah().get(i).getSekolahid().equalsIgnoreCase(
                                    PreferenceUtils.getSekolahId(getApplicationContext()))){
                                listGradeSekolah.add(dataGradeSekolah.getData().getMsGradeSekolah().get(i));
                            }
                        }

                        if(listGradeSekolah!=null){
                            try {
                                for(int x=0; x<listGradeSekolah.size(); x++){
                                    if(listGradeSekolah.get(x).getGradeid().equalsIgnoreCase("1")){
                                        n1 = listGradeSekolah.get(x).getNilai();
                                    } else if(listGradeSekolah.get(x).getGradeid().equalsIgnoreCase("2")){
                                        n2 = listGradeSekolah.get(x).getNilai();
                                    } else if(listGradeSekolah.get(x).getGradeid().equalsIgnoreCase("3")){
                                        n3 = listGradeSekolah.get(x).getNilai();
                                    } else if(listGradeSekolah.get(x).getGradeid().equalsIgnoreCase("4")) {
                                        n4 = listGradeSekolah.get(x).getNilai();
                                    }
                                }
                            } catch (Exception E){ }
                        }

                    } catch (Exception E){ }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            setDataNilai();
                        }
                    });

                }
            }
            @Override
            public void onFailure(Call<ModelGradeSekolah> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OutputPortfolioC.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void setDataNilai(){
        outputNilaiA.setText(n1);
        outputNilaiB.setText(n2);
        outputNilaiC.setText(n3);
        outputNilaiD.setText(n4);
    }

    public void onBackPressed() {
        Intent a = new Intent(OutputPortfolioC.this, HomeStudent.class);
        startActivity(a);
        finish();
    }
}