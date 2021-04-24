
package com.project.eportfolio.model.achievement;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Achievment implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sekolahid")
    @Expose
    private String sekolahid;
    @SerializedName("muridid")
    @Expose
    private String muridid;
    @SerializedName("kategoripo")
    @Expose
    private String kategoripo;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("narasi")
    @Expose
    private String narasi;
    @SerializedName("tempat")
    @Expose
    private String tempat;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("foto")
    @Expose
    private String foto;
    public final static Creator<Achievment> CREATOR = new Creator<Achievment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Achievment createFromParcel(android.os.Parcel in) {
            return new Achievment(in);
        }

        public Achievment[] newArray(int size) {
            return (new Achievment[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6511451679629443365L;

    protected Achievment(android.os.Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.sekolahid = ((String) in.readValue((String.class.getClassLoader())));
        this.muridid = ((String) in.readValue((String.class.getClassLoader())));
        this.kategoripo = ((String) in.readValue((String.class.getClassLoader())));
        this.judul = ((String) in.readValue((String.class.getClassLoader())));
        this.narasi = ((String) in.readValue((String.class.getClassLoader())));
        this.tempat = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggal = ((String) in.readValue((String.class.getClassLoader())));
        this.foto = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Achievment() {
    }

    /**
     * 
     * @param sekolahid
     * @param tempat
     * @param foto
     * @param muridid
     * @param kategoripo
     * @param narasi
     * @param id
     * @param tanggal
     * @param judul
     */
    public Achievment(String id, String sekolahid, String muridid, String kategoripo, String judul, String narasi, String tempat, String tanggal, String foto) {
        super();
        this.id = id;
        this.sekolahid = sekolahid;
        this.muridid = muridid;
        this.kategoripo = kategoripo;
        this.judul = judul;
        this.narasi = narasi;
        this.tempat = tempat;
        this.tanggal = tanggal;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSekolahid() {
        return sekolahid;
    }

    public void setSekolahid(String sekolahid) {
        this.sekolahid = sekolahid;
    }

    public String getMuridid() {
        return muridid;
    }

    public void setMuridid(String muridid) {
        this.muridid = muridid;
    }

    public String getKategoripo() {
        return kategoripo;
    }

    public void setKategoripo(String kategoripo) {
        this.kategoripo = kategoripo;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNarasi() {
        return narasi;
    }

    public void setNarasi(String narasi) {
        this.narasi = narasi;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(sekolahid);
        dest.writeValue(muridid);
        dest.writeValue(kategoripo);
        dest.writeValue(judul);
        dest.writeValue(narasi);
        dest.writeValue(tempat);
        dest.writeValue(tanggal);
        dest.writeValue(foto);
    }

    public int describeContents() {
        return  0;
    }

}
