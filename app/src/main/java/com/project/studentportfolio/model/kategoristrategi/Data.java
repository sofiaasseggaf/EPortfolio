
package com.project.studentportfolio.model.kategoristrategi;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_kategoristrategi")
    @Expose
    private List<MsKategoristrategi> msKategoristrategi = null;
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
    private final static long serialVersionUID = -7085581627285372448L;

    protected Data(Parcel in) {
        in.readList(this.msKategoristrategi, (com.project.studentportfolio.model.kategoristrategi.MsKategoristrategi.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msKategoristrategi
     */
    public Data(List<MsKategoristrategi> msKategoristrategi) {
        super();
        this.msKategoristrategi = msKategoristrategi;
    }

    public List<MsKategoristrategi> getMsKategoristrategi() {
        return msKategoristrategi;
    }

    public void setMsKategoristrategi(List<MsKategoristrategi> msKategoristrategi) {
        this.msKategoristrategi = msKategoristrategi;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msKategoristrategi);
    }

    public int describeContents() {
        return  0;
    }

}
