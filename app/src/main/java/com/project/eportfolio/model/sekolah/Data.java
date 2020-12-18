
package com.project.eportfolio.model.sekolah;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_sekolah")
    @Expose
    private List<MsSekolah> msSekolah = null;
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
    private final static long serialVersionUID = 7665502143460985788L;

    protected Data(Parcel in) {
        in.readList(this.msSekolah, (MsSekolah.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msSekolah
     */
    public Data(List<MsSekolah> msSekolah) {
        super();
        this.msSekolah = msSekolah;
    }

    public List<MsSekolah> getMsSekolah() {
        return msSekolah;
    }

    public void setMsSekolah(List<MsSekolah> msSekolah) {
        this.msSekolah = msSekolah;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msSekolah);
    }

    public int describeContents() {
        return  0;
    }

}
