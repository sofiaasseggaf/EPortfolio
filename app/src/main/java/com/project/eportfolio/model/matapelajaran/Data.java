
package com.project.eportfolio.model.matapelajaran;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_matapelajaran")
    @Expose
    private List<MsMatapelajaran> msMatapelajaran = null;
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
    private final static long serialVersionUID = -8794072729755428964L;

    protected Data(Parcel in) {
        in.readList(this.msMatapelajaran, (MsMatapelajaran.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msMatapelajaran
     */
    public Data(List<MsMatapelajaran> msMatapelajaran) {
        super();
        this.msMatapelajaran = msMatapelajaran;
    }

    public List<MsMatapelajaran> getMsMatapelajaran() {
        return msMatapelajaran;
    }

    public void setMsMatapelajaran(List<MsMatapelajaran> msMatapelajaran) {
        this.msMatapelajaran = msMatapelajaran;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msMatapelajaran);
    }

    public int describeContents() {
        return  0;
    }

}
