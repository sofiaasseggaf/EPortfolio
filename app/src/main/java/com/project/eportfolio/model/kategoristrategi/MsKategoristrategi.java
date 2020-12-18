
package com.project.eportfolio.model.kategoristrategi;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsKategoristrategi implements Serializable, Parcelable
{

    @SerializedName("id_kategori")
    @Expose
    private String idKategori;
    @SerializedName("name_kategori")
    @Expose
    private String nameKategori;
    @SerializedName("desc_kategori")
    @Expose
    private String descKategori;
    @SerializedName("createdby")
    @Expose
    private Object createdby;
    @SerializedName("createddate")
    @Expose
    private String createddate;
    @SerializedName("updateby")
    @Expose
    private Object updateby;
    @SerializedName("updatedate")
    @Expose
    private Object updatedate;
    @SerializedName("isactive")
    @Expose
    private Object isactive;
    public final static Creator<MsKategoristrategi> CREATOR = new Creator<MsKategoristrategi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsKategoristrategi createFromParcel(Parcel in) {
            return new MsKategoristrategi(in);
        }

        public MsKategoristrategi[] newArray(int size) {
            return (new MsKategoristrategi[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3771428815351312904L;

    protected MsKategoristrategi(Parcel in) {
        this.idKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.nameKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.descKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.createdby = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createddate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateby = ((Object) in.readValue((Object.class.getClassLoader())));
        this.updatedate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isactive = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MsKategoristrategi() {
    }

    /**
     * 
     * @param idKategori
     * @param updatedate
     * @param createdby
     * @param createddate
     * @param updateby
     * @param isactive
     * @param nameKategori
     * @param descKategori
     */
    public MsKategoristrategi(String idKategori, String nameKategori, String descKategori, Object createdby, String createddate, Object updateby, Object updatedate, Object isactive) {
        super();
        this.idKategori = idKategori;
        this.nameKategori = nameKategori;
        this.descKategori = descKategori;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.isactive = isactive;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getNameKategori() {
        return nameKategori;
    }

    public void setNameKategori(String nameKategori) {
        this.nameKategori = nameKategori;
    }

    public String getDescKategori() {
        return descKategori;
    }

    public void setDescKategori(String descKategori) {
        this.descKategori = descKategori;
    }

    public Object getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Object createdby) {
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

    public Object getIsactive() {
        return isactive;
    }

    public void setIsactive(Object isactive) {
        this.isactive = isactive;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idKategori);
        dest.writeValue(nameKategori);
        dest.writeValue(descKategori);
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
