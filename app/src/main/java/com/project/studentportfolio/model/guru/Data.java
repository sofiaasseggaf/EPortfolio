
package com.project.studentportfolio.model.guru;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_guru")
    @Expose
    private List<MsGuru> msGuru = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6470673458785781123L;

    protected Data(Parcel in) {
        in.readList(this.msGuru, (MsGuru.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msGuru
     */
    public Data(List<MsGuru> msGuru) {
        super();
        this.msGuru = msGuru;
    }

    public List<MsGuru> getMsGuru() {
        return msGuru;
    }

    public void setMsGuru(List<MsGuru> msGuru) {
        this.msGuru = msGuru;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msGuru);
    }

    public int describeContents() {
        return  0;
    }

}
