
package com.project.eportfolio.model.gradesekolah;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsGradeSekolah implements Serializable, Parcelable
{

    @SerializedName("id_grade_sekolah")
    @Expose
    private String idGradeSekolah;
    @SerializedName("sekolahid")
    @Expose
    private String sekolahid;
    @SerializedName("gradeid")
    @Expose
    private String gradeid;
    @SerializedName("nilai")
    @Expose
    private String nilai;
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
    public final static Creator<MsGradeSekolah> CREATOR = new Creator<MsGradeSekolah>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsGradeSekolah createFromParcel(android.os.Parcel in) {
            return new MsGradeSekolah(in);
        }

        public MsGradeSekolah[] newArray(int size) {
            return (new MsGradeSekolah[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1696369614862749214L;

    protected MsGradeSekolah(android.os.Parcel in) {
        this.idGradeSekolah = ((String) in.readValue((String.class.getClassLoader())));
        this.sekolahid = ((String) in.readValue((String.class.getClassLoader())));
        this.gradeid = ((String) in.readValue((String.class.getClassLoader())));
        this.nilai = ((String) in.readValue((String.class.getClassLoader())));
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
    public MsGradeSekolah() {
    }

    /**
     * 
     * @param sekolahid
     * @param gradeid
     * @param updatedate
     * @param createdby
     * @param createddate
     * @param updateby
     * @param nilai
     * @param isactive
     * @param idGradeSekolah
     */
    public MsGradeSekolah(String idGradeSekolah, String sekolahid, String gradeid, String nilai, String createdby, String createddate, String updateby, String updatedate, String isactive) {
        super();
        this.idGradeSekolah = idGradeSekolah;
        this.sekolahid = sekolahid;
        this.gradeid = gradeid;
        this.nilai = nilai;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.isactive = isactive;
    }

    public String getIdGradeSekolah() {
        return idGradeSekolah;
    }

    public void setIdGradeSekolah(String idGradeSekolah) {
        this.idGradeSekolah = idGradeSekolah;
    }

    public String getSekolahid() {
        return sekolahid;
    }

    public void setSekolahid(String sekolahid) {
        this.sekolahid = sekolahid;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(idGradeSekolah);
        dest.writeValue(sekolahid);
        dest.writeValue(gradeid);
        dest.writeValue(nilai);
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
