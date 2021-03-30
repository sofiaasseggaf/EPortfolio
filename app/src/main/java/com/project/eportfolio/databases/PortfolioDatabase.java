package com.project.eportfolio.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.utility.Constants;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;

import static android.content.ContentValues.TAG;

public class PortfolioDatabase extends SQLiteOpenHelper {

    public PortfolioDatabase(@Nullable Context context) {
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DATABASE.DROP_QUERY);
        this.onCreate(db);
    }

    public void addPortfolio(TrPortofolio portofolio) {

        Log.d(TAG, "Values Got " + portofolio.getId());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.DATABASE.ID, portofolio.getId());
        values.put(Constants.DATABASE.MURIDID, portofolio.getMuridid().toString());
        values.put(Constants.DATABASE.GURUID, portofolio.getGuruid());
        values.put(Constants.DATABASE.MAPELID, portofolio.getMapelid());
        values.put(Constants.DATABASE.IDKATEGORI, portofolio.getIdkategori());
        values.put(Constants.DATABASE.STRATEGIID, portofolio.getStrategiid());
        values.put(Constants.DATABASE.RUBRIKID, portofolio.getRubrikid().toString());
        values.put(Constants.DATABASE.JUDUL_KD, portofolio.getJudulKd());
        values.put(Constants.DATABASE.RUBRIKDESK, portofolio.getRubrikdesk());
        values.put(Constants.DATABASE.TANGGAL, portofolio.getTanggal());
        values.put(Constants.DATABASE.TEMPAT, portofolio.getTempat());
        values.put(Constants.DATABASE.NILAI, portofolio.getNilai());
        values.put(Constants.DATABASE.PREDIKAT_MUTU, portofolio.getPredikatMutu());
        values.put(Constants.DATABASE.NARASI, portofolio.getNarasi());
        values.put(Constants.DATABASE.FOTO, portofolio.getFoto().toString());
        values.put(Constants.DATABASE.KELAS, portofolio.getKelas());
        values.put(Constants.DATABASE.TH_AJARAN, portofolio.getThAjaran());
        values.put(Constants.DATABASE.SEMESTER, portofolio.getSemester());
        values.put(Constants.DATABASE.CREATEDBY, portofolio.getCreatedby());
        values.put(Constants.DATABASE.CREATEDDATE, portofolio.getCreateddate());
        values.put(Constants.DATABASE.UPDATEBY, portofolio.getUpdateby().toString());
        values.put(Constants.DATABASE.UPDATEDATE, portofolio.getUpdatedate().toString());
        values.put(Constants.DATABASE.PREDIKAT, portofolio.getPredikat());
        values.put(Constants.DATABASE.KELASID, portofolio.getKelasid());

        try {
            db.insert(Constants.DATABASE.TABLE_NAME, null, values);
        } catch (Exception e) {

        }
        db.close();
    }

    public void fetchPortfolio(PortfolioFetchListener listener) {
        PortfolioFetcher fetcher = new PortfolioFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    public class PortfolioFetcher extends Thread {

        private final PortfolioFetchListener mListener;
        private final SQLiteDatabase mDb;

        public PortfolioFetcher(PortfolioFetchListener listener, SQLiteDatabase db) {
            mListener = listener;
            mDb = db;
        }

        @Override
        public void run() {
            Cursor cursor = mDb.rawQuery(Constants.DATABASE.GET_PORTFOLIO_QUERY, null);

            final List<TrPortofolio> portofolioList = new ArrayList<>();

            if (cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        TrPortofolio portofolio = new TrPortofolio();
                        portofolio.setFromDatabase(true);

                        portofolio.setId(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.ID)));
                        portofolio.setMuridid(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.MURIDID)));
                        portofolio.setGuruid(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.GURUID)));
                        portofolio.setMapelid(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.MAPELID)));
                        portofolio.setIdkategori(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.IDKATEGORI)));
                        portofolio.setStrategiid(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.STRATEGIID)));
                        portofolio.setRubrikid(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.RUBRIKID)));
                        portofolio.setJudulKd(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.JUDUL_KD)));
                        portofolio.setRubrikdesk(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.RUBRIKDESK)));
                        portofolio.setTanggal(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.TANGGAL)));
                        portofolio.setTempat(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.TEMPAT)));
                        portofolio.setNilai(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.NILAI)));
                        portofolio.setPredikatMutu(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PREDIKAT_MUTU)));
                        portofolio.setNarasi(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.NARASI)));
                        portofolio.setFoto(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.FOTO)));
                        portofolio.setKelas(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.KELAS)));
                        portofolio.setThAjaran(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.TH_AJARAN)));
                        portofolio.setSemester(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.SEMESTER)));
                        portofolio.setCreatedby(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.CREATEDBY)));
                        portofolio.setCreateddate(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.CREATEDDATE)));
                        portofolio.setUpdateby(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.UPDATEBY)));
                        portofolio.setUpdatedate(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.UPDATEDATE)));
                        portofolio.setPredikat(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PREDIKAT)));
                        portofolio.setKelasid(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.KELASID)));

                        portofolioList.add(portofolio);
                        publishPortfolio(portofolio);

                    } while (cursor.moveToNext());
                }
            }

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverAllPortfolio(portofolioList);
                    mListener.onHideDialog();
                }
            });
        }

        public void publishPortfolio(final TrPortofolio portofolio) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverPortfolio(portofolio);
                }
            });
        }
    }
}
