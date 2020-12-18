
package com.project.eportfolio.model.siswa;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

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
    private final static long serialVersionUID = -2022948459535419466L;

    protected Data(Parcel in) {
        in.readList(this.msMurid, (MsMurid.class.getClassLoader()));
    }

    public Data() {
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
