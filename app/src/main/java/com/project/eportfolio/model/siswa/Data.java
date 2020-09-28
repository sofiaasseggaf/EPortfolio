
package com.project.eportfolio.model.siswa;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_murid")
    @Expose
    private List<MsMurid> msMurid = null;
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
    private final static long serialVersionUID = -2580164872265547048L;

    protected Data(Parcel in) {
        in.readList(this.msMurid, (MsMurid.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msMurid
     */
    public Data(List<MsMurid> msMurid) {
        super();
        this.msMurid = msMurid;
    }

    public List<MsMurid> getMsMurid() {
        return msMurid;
    }

    public void setMsMurid(List<MsMurid> msMurid) {
        this.msMurid = msMurid;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msMurid);
    }

    public int describeContents() {
        return  0;
    }

}
