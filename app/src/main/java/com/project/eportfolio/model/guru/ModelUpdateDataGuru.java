
package com.project.eportfolio.model.guru;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUpdateDataGuru implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<ModelUpdateDataGuru> CREATOR = new Creator<ModelUpdateDataGuru>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelUpdateDataGuru createFromParcel(Parcel in) {
            return new ModelUpdateDataGuru(in);
        }

        public ModelUpdateDataGuru[] newArray(int size) {
            return (new ModelUpdateDataGuru[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2139176683656835256L;

    protected ModelUpdateDataGuru(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelUpdateDataGuru() {
    }

    /**
     * 
     * @param message
     * @param status
     */
    public ModelUpdateDataGuru(Boolean status, String message) {
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
