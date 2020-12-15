
package com.project.studentportfolio.model.portfolio;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("tr_portofolio")
    @Expose
    private List<TrPortofolio> trPortofolio = null;
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
    private final static long serialVersionUID = 967706925152490323L;

    protected Data(Parcel in) {
        in.readList(this.trPortofolio, (TrPortofolio.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param trPortofolio
     */
    public Data(List<TrPortofolio> trPortofolio) {
        super();
        this.trPortofolio = trPortofolio;
    }

    public List<TrPortofolio> getTrPortofolio() {
        return trPortofolio;
    }

    public void setTrPortofolio(List<TrPortofolio> trPortofolio) {
        this.trPortofolio = trPortofolio;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(trPortofolio);
    }

    public int describeContents() {
        return  0;
    }

}
