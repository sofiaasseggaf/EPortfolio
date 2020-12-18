
package com.project.eportfolio.model.grade;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsGrade implements Serializable, Parcelable
{

    @SerializedName("id_grade")
    @Expose
    private String idGrade;
    @SerializedName("sekolahid")
    @Expose
    private String sekolahid;
    @SerializedName("nilai")
    @Expose
    private String nilai;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("predikat")
    @Expose
    private String predikat;
    @SerializedName("createdby")
    @Expose
    private String createdby;
    @SerializedName("createddate")
    @Expose
    private String createddate;
    @SerializedName("updateby")
    @Expose
    private String updateby;
    @SerializedName("updatedate")
    @Expose
    private String updatedate;
    @SerializedName("isactive")
    @Expose
    private String isactive;
    public final static Creator<MsGrade> CREATOR = new Creator<MsGrade>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsGrade createFromParcel(Parcel in) {
            return new MsGrade(in);
        }

        public MsGrade[] newArray(int size) {
            return (new MsGrade[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7802150903616024742L;

    protected MsGrade(Parcel in) {
        this.idGrade = ((String) in.readValue((String.class.getClassLoader())));
        this.sekolahid = ((String) in.readValue((String.class.getClassLoader())));
        this.nilai = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.predikat = ((String) in.readValue((String.class.getClassLoader())));
        this.createdby = ((String) in.readValue((String.class.getClassLoader())));
        this.createddate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateby = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedate = ((String) in.readValue((String.class.getClassLoader())));
        this.isactive = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MsGrade() {
    }

    /**
     * 
     * @param sekolahid
     * @param predikat
     * @param updatedate
     * @param createdby
     * @param createddate
     * @param updateby
     * @param nilai
     * @param isactive
     * @param description
     * @param idGrade
     */
    public MsGrade(String idGrade, String sekolahid, String nilai, String description, String predikat, String createdby, String createddate, String updateby, String updatedate, String isactive) {
        super();
        this.idGrade = idGrade;
        this.sekolahid = sekolahid;
        this.nilai = nilai;
        this.description = description;
        this.predikat = predikat;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.isactive = isactive;
    }

    public String getIdGrade() {
        return idGrade;
    }

    public void setIdGrade(String idGrade) {
        this.idGrade = idGrade;
    }

    public String getSekolahid() {
        return sekolahid;
    }

    public void setSekolahid(String sekolahid) {
        this.sekolahid = sekolahid;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPredikat() {
        return predikat;
    }

    public void setPredikat(String predikat) {
        this.predikat = predikat;
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

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idGrade);
        dest.writeValue(sekolahid);
        dest.writeValue(nilai);
        dest.writeValue(description);
        dest.writeValue(predikat);
        dest.writeValue(createdby);
        dest.writeValue(createddate);
        dest.writeValue(updateby);
        dest.writeValue(updatedate);
        dest.writeValue(isactive);
    }

    public int describeContents() {
        return  0;
    }

}
