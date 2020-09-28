
package com.project.eportfolio.model.matapelajaran;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Table;

public class MsMatapelajaran implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sekolahid")
    @Expose
    private String sekolahid;
    @SerializedName("guruid")
    @Expose
    private String guruid;
    @SerializedName("name")
    @Expose
    private String name;
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
    private String isactive;
    public final static Creator<MsMatapelajaran> CREATOR = new Creator<MsMatapelajaran>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsMatapelajaran createFromParcel(Parcel in) {
            return new MsMatapelajaran(in);
        }

        public MsMatapelajaran[] newArray(int size) {
            return (new MsMatapelajaran[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1256472097940198845L;

    protected MsMatapelajaran(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.sekolahid = ((String) in.readValue((String.class.getClassLoader())));
        this.guruid = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.createdby = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createddate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateby = ((Object) in.readValue((Object.class.getClassLoader())));
        this.updatedate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isactive = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MsMatapelajaran() {
    }

    /**
     * 
     * @param sekolahid
     * @param updatedate
     * @param guruid
     * @param createdby
     * @param createddate
     * @param updateby
     * @param isactive
     * @param name
     * @param id
     */
    public MsMatapelajaran(String id, String sekolahid, String guruid, String name, Object createdby, String createddate, Object updateby, Object updatedate, String isactive) {
        super();
        this.id = id;
        this.sekolahid = sekolahid;
        this.guruid = guruid;
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

    public String getGuruid() {
        return guruid;
    }

    public void setGuruid(String guruid) {
        this.guruid = guruid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(sekolahid);
        dest.writeValue(guruid);
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
