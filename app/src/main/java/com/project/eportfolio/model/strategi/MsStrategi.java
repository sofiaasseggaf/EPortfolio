
package com.project.eportfolio.model.strategi;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsStrategi implements Serializable, Parcelable
{

    @SerializedName("id_strategi")
    @Expose
    private String idStrategi;
    @SerializedName("kategoriid")
    @Expose
    private String kategoriid;
    @SerializedName("name_strategi")
    @Expose
    private String nameStrategi;
    @SerializedName("desc_strategi")
    @Expose
    private String descStrategi;
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
    public final static Creator<MsStrategi> CREATOR = new Creator<MsStrategi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsStrategi createFromParcel(Parcel in) {
            return new MsStrategi(in);
        }

        public MsStrategi[] newArray(int size) {
            return (new MsStrategi[size]);
        }

    }
    ;
    private final static long serialVersionUID = 4862117136777180637L;

    protected MsStrategi(Parcel in) {
        this.idStrategi = ((String) in.readValue((String.class.getClassLoader())));
        this.kategoriid = ((String) in.readValue((String.class.getClassLoader())));
        this.nameStrategi = ((String) in.readValue((String.class.getClassLoader())));
        this.descStrategi = ((String) in.readValue((String.class.getClassLoader())));
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
    public MsStrategi() {
    }

    /**
     * 
     * @param kategoriid
     * @param updatedate
     * @param nameStrategi
     * @param createdby
     * @param createddate
     * @param updateby
     * @param isactive
     * @param descStrategi
     * @param idStrategi
     */
    public MsStrategi(String idStrategi, String kategoriid, String nameStrategi, String descStrategi, String createdby, String createddate, String updateby, String updatedate, String isactive) {
        super();
        this.idStrategi = idStrategi;
        this.kategoriid = kategoriid;
        this.nameStrategi = nameStrategi;
        this.descStrategi = descStrategi;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.isactive = isactive;
    }

    public String getIdStrategi() {
        return idStrategi;
    }

    public void setIdStrategi(String idStrategi) {
        this.idStrategi = idStrategi;
    }

    public String getKategoriid() {
        return kategoriid;
    }

    public void setKategoriid(String kategoriid) {
        this.kategoriid = kategoriid;
    }

    public String getNameStrategi() {
        return nameStrategi;
    }

    public void setNameStrategi(String nameStrategi) {
        this.nameStrategi = nameStrategi;
    }

    public String getDescStrategi() {
        return descStrategi;
    }

    public void setDescStrategi(String descStrategi) {
        this.descStrategi = descStrategi;
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
        dest.writeValue(idStrategi);
        dest.writeValue(kategoriid);
        dest.writeValue(nameStrategi);
        dest.writeValue(descStrategi);
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
