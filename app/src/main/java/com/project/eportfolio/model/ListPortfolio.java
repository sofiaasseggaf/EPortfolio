package com.project.eportfolio.model;

import android.os.Parcel;

public class ListPortfolio {

    private Object foto;
    private String judulKd;
    private String mapelid;
    private String kelas;
    private String semester;
    private String thAjaran;
    private String predikat;
    private String narasi;
    private String tanggal;

    public ListPortfolio(Object foto, String judulKd, String mapelid, String kelas, String semester, String thAjaran, String predikat, String narasi, String tanggal) {
        this.foto = foto;
        this.judulKd = judulKd;
        this.mapelid = mapelid;
        this.kelas = kelas;
        this.semester = semester;
        this.thAjaran = thAjaran;
        this.predikat = predikat;
        this.narasi = narasi;
        this.tanggal = tanggal;
    }

    public Object getFoto() {
        return foto;
    }

    public void setFoto(Object foto) {
        this.foto = foto;
    }

    public String getJudulKd() {
        return judulKd;
    }

    public void setJudulKd(String judulKd) {
        this.judulKd = judulKd;
    }

    public String getMapelid() {
        return mapelid;
    }

    public void setMapelid(String mapelid) {
        this.mapelid = mapelid;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getThAjaran() {
        return thAjaran;
    }

    public void setThAjaran(String thAjaran) {
        this.thAjaran = thAjaran;
    }

    public String getPredikat() {
        return predikat;
    }

    public void setPredikat(String predikat) {
        this.predikat = predikat;
    }

    public String getNarasi() {
        return narasi;
    }

    public void setNarasi(String narasi) {
        this.narasi = narasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    protected ListPortfolio(Parcel in) {
        this.mapelid = ((String) in.readValue((String.class.getClassLoader())));
        this.judulKd = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggal = ((String) in.readValue((String.class.getClassLoader())));
        this.narasi = ((String) in.readValue((String.class.getClassLoader())));
        this.foto = ((Object) in.readValue((Object.class.getClassLoader())));
        this.kelas = ((String) in.readValue((String.class.getClassLoader())));
        this.thAjaran = ((String) in.readValue((String.class.getClassLoader())));
        this.semester = ((String) in.readValue((String.class.getClassLoader())));
        this.predikat = ((String) in.readValue((String.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mapelid);
        dest.writeValue(judulKd);
        dest.writeValue(tanggal);
        dest.writeValue(narasi);
        dest.writeValue(foto);
        dest.writeValue(kelas);
        dest.writeValue(thAjaran);
        dest.writeValue(semester);
        dest.writeValue(predikat);
    }

    public int describeContents() {
        return  0;
    }
}
