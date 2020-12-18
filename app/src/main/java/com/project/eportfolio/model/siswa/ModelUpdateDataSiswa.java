
package com.project.eportfolio.model.siswa;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUpdateDataSiswa implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<ModelUpdateDataSiswa> CREATOR = new Creator<ModelUpdateDataSiswa>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelUpdateDataSiswa createFromParcel(Parcel in) {
            return new ModelUpdateDataSiswa(in);
        }

        public ModelUpdateDataSiswa[] newArray(int size) {
            return (new ModelUpdateDataSiswa[size]);
        }

    }
    ;
    private final static long serialVersionUID = 88419327506434035L;

    protected ModelUpdateDataSiswa(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelUpdateDataSiswa() {
    }

    /**
     * 
     * @param message
     * @param status
     */
    public ModelUpdateDataSiswa(Boolean status, String message) {
        super();
        this.status = status;
        this.message = message;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
