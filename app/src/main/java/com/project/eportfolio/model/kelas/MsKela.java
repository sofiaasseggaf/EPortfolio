
package com.project.eportfolio.model.kelas;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsKela implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sekolahid")
    @Expose
    private String sekolahid;
    @SerializedName("name")
    @Expose
    private String name;
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
    public final static Creator<MsKela> CREATOR = new Creator<MsKela>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsKela createFromParcel(Parcel in) {
            return new MsKela(in);
        }

        public MsKela[] newArray(int size) {
            return (new MsKela[size]);
        }

    }
    ;
    private final static long serialVersionUID = 9181256649269665172L;

    protected MsKela(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.sekolahid = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
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
    public MsKela() {
    }

    /**
     * 
     * @param sekolahid
     * @param updatedate
     * @param createdby
     * @param createddate
     * @param updateby
     * @param isactive
     * @param name
     * @param id
     */
    public MsKela(String id, String sekolahid, String name, String createdby, String createddate, String updateby, String updatedate, String isactive) {
        super();
        this.id = id;
        this.sekolahid = sekolahid;
        this.name = name;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.isactive = isactive;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeValue(id);
        dest.writeValue(sekolahid);
        dest.writeValue(name);
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
