
package com.project.eportfolio.model.portfolio;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrPortofolio implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("muridid")
    @Expose
    private Object muridid;
    @SerializedName("guruid")
    @Expose
    private String guruid;
    @SerializedName("mapelid")
    @Expose
    private String mapelid;
    @SerializedName("idkategori")
    @Expose
    private String idkategori;
    @SerializedName("strategiid")
    @Expose
    private String strategiid;
    @SerializedName("rubrikid")
    @Expose
    private Object rubrikid;
    @SerializedName("judul_kd")
    @Expose
    private String judulKd;
    @SerializedName("rubrikdesk")
    @Expose
    private String rubrikdesk;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("tempat")
    @Expose
    private String tempat;
    @SerializedName("nilai")
    @Expose
    private String nilai;
    @SerializedName("predikat_mutu")
    @Expose
    private String predikatMutu;
    @SerializedName("narasi")
    @Expose
    private String narasi;
    @SerializedName("foto")
    @Expose
    private Object foto;
    @SerializedName("kelas")
    @Expose
    private String kelas;
    @SerializedName("th_ajaran")
    @Expose
    private String thAjaran;
    @SerializedName("semester")
    @Expose
    private String semester;
    @SerializedName("createdby")
    @Expose
    private String createdby;
    @SerializedName("createddate")
    @Expose
    private String createddate;
    @SerializedName("updateby")
    @Expose
    private Object updateby;
    @SerializedName("updatedate")
    @Expose
    private Object updatedate;
    @SerializedName("predikat")
    @Expose
    private String predikat;
    @SerializedName("kelasid")
    @Expose
    private String kelasid;
    public final static Creator<TrPortofolio> CREATOR = new Creator<TrPortofolio>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TrPortofolio createFromParcel(Parcel in) {
            return new TrPortofolio(in);
        }

        public TrPortofolio[] newArray(int size) {
            return (new TrPortofolio[size]);
        }

    }
    ;
    private final static long serialVersionUID = 650711374863548658L;

    protected TrPortofolio(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.muridid = ((Object) in.readValue((Object.class.getClassLoader())));
        this.guruid = ((String) in.readValue((String.class.getClassLoader())));
        this.mapelid = ((String) in.readValue((String.class.getClassLoader())));
        this.idkategori = ((String) in.readValue((String.class.getClassLoader())));
        this.strategiid = ((String) in.readValue((String.class.getClassLoader())));
        this.rubrikid = ((Object) in.readValue((Object.class.getClassLoader())));
        this.judulKd = ((String) in.readValue((String.class.getClassLoader())));
        this.rubrikdesk = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggal = ((String) in.readValue((String.class.getClassLoader())));
        this.tempat = ((String) in.readValue((String.class.getClassLoader())));
        this.nilai = ((String) in.readValue((String.class.getClassLoader())));
        this.predikatMutu = ((String) in.readValue((String.class.getClassLoader())));
        this.narasi = ((String) in.readValue((String.class.getClassLoader())));
        this.foto = ((Object) in.readValue((Object.class.getClassLoader())));
        this.kelas = ((String) in.readValue((String.class.getClassLoader())));
        this.thAjaran = ((String) in.readValue((String.class.getClassLoader())));
        this.semester = ((String) in.readValue((String.class.getClassLoader())));
        this.createdby = ((String) in.readValue((String.class.getClassLoader())));
        this.createddate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateby = ((Object) in.readValue((Object.class.getClassLoader())));
        this.updatedate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.predikat = ((String) in.readValue((String.class.getClassLoader())));
        this.kelasid = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public TrPortofolio() {
    }

    /**
     * 
     * @param predikat
     * @param rubrikid
     * @param muridid
     * @param guruid
     * @param thAjaran
     * @param createddate
     * @param nilai
     * @param idkategori
     * @param strategiid
     * @param kelasid
     * @param tempat
     * @param foto
     * @param updatedate
     * @param createdby
     * @param updateby
     * @param predikatMutu
     * @param kelas
     * @param mapelid
     * @param narasi
     * @param judulKd
     * @param rubrikdesk
     * @param semester
     * @param id
     * @param tanggal
     */
    public TrPortofolio(String id, Object muridid, String guruid, String mapelid, String idkategori, String strategiid, Object rubrikid, String judulKd, String rubrikdesk, String tanggal, String tempat, String nilai, String predikatMutu, String narasi, Object foto, String kelas, String thAjaran, String semester, String createdby, String createddate, Object updateby, Object updatedate, String predikat, String kelasid) {
        super();
        this.id = id;
        this.muridid = muridid;
        this.guruid = guruid;
        this.mapelid = mapelid;
        this.idkategori = idkategori;
        this.strategiid = strategiid;
        this.rubrikid = rubrikid;
        this.judulKd = judulKd;
        this.rubrikdesk = rubrikdesk;
        this.tanggal = tanggal;
        this.tempat = tempat;
        this.nilai = nilai;
        this.predikatMutu = predikatMutu;
        this.narasi = narasi;
        this.foto = foto;
        this.kelas = kelas;
        this.thAjaran = thAjaran;
        this.semester = semester;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.predikat = predikat;
        this.kelasid = kelasid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getMuridid() {
        return muridid;
    }

    public void setMuridid(Object muridid) {
        this.muridid = muridid;
    }

    public String getGuruid() {
        return guruid;
    }

    public void setGuruid(String guruid) {
        this.guruid = guruid;
    }

    public String getMapelid() {
        return mapelid;
    }

    public void setMapelid(String mapelid) {
        this.mapelid = mapelid;
    }

    public String getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(String idkategori) {
        this.idkategori = idkategori;
    }

    public String getStrategiid() {
        return strategiid;
    }

    public void setStrategiid(String strategiid) {
        this.strategiid = strategiid;
    }

    public Object getRubrikid() {
        return rubrikid;
    }

    public void setRubrikid(Object rubrikid) {
        this.rubrikid = rubrikid;
    }

    public String getJudulKd() {
        return judulKd;
    }

    public void setJudulKd(String judulKd) {
        this.judulKd = judulKd;
    }

    public String getRubrikdesk() {
        return rubrikdesk;
    }

    public void setRubrikdesk(String rubrikdesk) {
        this.rubrikdesk = rubrikdesk;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getPredikatMutu() {
        return predikatMutu;
    }

    public void setPredikatMutu(String predikatMutu) {
        this.predikatMutu = predikatMutu;
    }

    public String getNarasi() {
        return narasi;
    }

    public void setNarasi(String narasi) {
        this.narasi = narasi;
    }

    public Object getFoto() {
        return foto;
    }

    public void setFoto(Object foto) {
        this.foto = foto;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getThAjaran() {
        return thAjaran;
    }

    public void setThAjaran(String thAjaran) {
        this.thAjaran = thAjaran;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public Object getUpdateby() {
        return updateby;
    }

    public void setUpdateby(Object updateby) {
        this.updateby = updateby;
    }

    public Object getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Object updatedate) {
        this.updatedate = updatedate;
    }

    public String getPredikat() {
        return predikat;
    }

    public void setPredikat(String predikat) {
        this.predikat = predikat;
    }

    public String getKelasid() {
        return kelasid;
    }

    public void setKelasid(String kelasid) {
        this.kelasid = kelasid;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(muridid);
        dest.writeValue(guruid);
        dest.writeValue(mapelid);
        dest.writeValue(idkategori);
        dest.writeValue(strategiid);
        dest.writeValue(rubrikid);
        dest.writeValue(judulKd);
        dest.writeValue(rubrikdesk);
        dest.writeValue(tanggal);
        dest.writeValue(tempat);
        dest.writeValue(nilai);
        dest.writeValue(predikatMutu);
        dest.writeValue(narasi);
        dest.writeValue(foto);
        dest.writeValue(kelas);
        dest.writeValue(thAjaran);
        dest.writeValue(semester);
        dest.writeValue(createdby);
        dest.writeValue(createddate);
        dest.writeValue(updateby);
        dest.writeValue(updatedate);
        dest.writeValue(predikat);
        dest.writeValue(kelasid);
    }

    public int describeContents() {
        return  0;
    }

}
