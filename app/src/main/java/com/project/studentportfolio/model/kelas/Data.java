
package com.project.studentportfolio.model.kelas;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_kelas")
    @Expose
    private List<MsKela> msKelas = null;
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
    private final static long serialVersionUID = -364842016900442241L;

    protected Data(Parcel in) {
        in.readList(this.msKelas, (MsKela.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msKelas
     */
    public Data(List<MsKela> msKelas) {
        super();
        this.msKelas = msKelas;
    }

    public List<MsKela> getMsKelas() {
        return msKelas;
    }

    public void setMsKelas(List<MsKela> msKelas) {
        this.msKelas = msKelas;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msKelas);
    }

    public int describeContents() {
        return  0;
    }

}
