
package com.project.eportfolio.model.rubrik;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsRubrik implements Serializable, Parcelable
{

    @SerializedName("id_rubrik")
    @Expose
    private String idRubrik;
    @SerializedName("strategiid")
    @Expose
    private String strategiid;
    @SerializedName("name_rubrik")
    @Expose
    private String nameRubrik;
    @SerializedName("desc_rubrik")
    @Expose
    private String descRubrik;
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
    @SerializedName("isactive")
    @Expose
    private String isactive;
    public final static Creator<MsRubrik> CREATOR = new Creator<MsRubrik>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsRubrik createFromParcel(Parcel in) {
            return new MsRubrik(in);
        }

        public MsRubrik[] newArray(int size) {
            return (new MsRubrik[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7418469523134148308L;

    protected MsRubrik(Parcel in) {
        this.idRubrik = ((String) in.readValue((String.class.getClassLoader())));
        this.strategiid = ((String) in.readValue((String.class.getClassLoader())));
        this.nameRubrik = ((String) in.readValue((String.class.getClassLoader())));
        this.descRubrik = ((String) in.readValue((String.class.getClassLoader())));
        this.createdby = ((String) in.readValue((String.class.getClassLoader())));
        this.createddate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateby = ((Object) in.readValue((Object.class.getClassLoader())));
        this.updatedate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isactive = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MsRubrik() {
    }

    /**
     * 
     * @param updatedate
     * @param createdby
     * @param createddate
     * @param updateby
     * @param idRubrik
     * @param isactive
     * @param strategiid
     * @param descRubrik
     * @param nameRubrik
     */
    public MsRubrik(String idRubrik, String strategiid, String nameRubrik, String descRubrik, String createdby, String createddate, Object updateby, Object updatedate, String isactive) {
        super();
        this.idRubrik = idRubrik;
        this.strategiid = strategiid;
        this.nameRubrik = nameRubrik;
        this.descRubrik = descRubrik;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.isactive = isactive;
    }

    public String getIdRubrik() {
        return idRubrik;
    }

    public void setIdRubrik(String idRubrik) {
        this.idRubrik = idRubrik;
    }

    public String getStrategiid() {
        return strategiid;
    }

    public void setStrategiid(String strategiid) {
        this.strategiid = strategiid;
    }

    public String getNameRubrik() {
        return nameRubrik;
    }

    public void setNameRubrik(String nameRubrik) {
        this.nameRubrik = nameRubrik;
    }

    public String getDescRubrik() {
        return descRubrik;
    }

    public void setDescRubrik(String descRubrik) {
        this.descRubrik = descRubrik;
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idRubrik);
        dest.writeValue(strategiid);
        dest.writeValue(nameRubrik);
        dest.writeValue(descRubrik);
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
