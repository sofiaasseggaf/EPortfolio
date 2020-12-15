package com.project.studentportfolio.model.portfolio;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPostPortofolio implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<ModelPostPortofolio> CREATOR = new Creator<ModelPostPortofolio>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ModelPostPortofolio createFromParcel(Parcel in) {
            return new ModelPostPortofolio(in);
        }

        public ModelPostPortofolio[] newArray(int size) {
            return (new ModelPostPortofolio[size]);
        }

    }
            ;
    private final static long serialVersionUID = -3246957670320084555L;

    protected ModelPostPortofolio(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ModelPostPortofolio() {
    }

    /**
     *
     * @param message
     * @param status
     */
    public ModelPostPortofolio(Boolean status, String message) {
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