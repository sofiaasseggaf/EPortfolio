
package com.project.eportfolio.model.siswa;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMurid implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("total")
    @Expose
    private Integer total;
    public final static Creator<ModelMurid> CREATOR = new Creator<ModelMurid>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelMurid createFromParcel(Parcel in) {
            return new ModelMurid(in);
        }

        public ModelMurid[] newArray(int size) {
            return (new ModelMurid[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8374178283821830113L;

    protected ModelMurid(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.total = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelMurid() {
    }

    /**
     * 
     * @param total
     * @param data
     * @param message
     * @param status
     */
    public ModelMurid(Boolean status, String message, Data data, Integer total) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(data);
        dest.writeValue(total);
    }

    public int describeContents() {
        return  0;
    }

}
