
package com.project.eportfolio.model.strategi;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_strategi")
    @Expose
    private List<MsStrategi> msStrategi = null;
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
    private final static long serialVersionUID = 1840322110125936847L;

    protected Data(Parcel in) {
        in.readList(this.msStrategi, (com.project.eportfolio.model.strategi.MsStrategi.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msStrategi
     */
    public Data(List<MsStrategi> msStrategi) {
        super();
        this.msStrategi = msStrategi;
    }

    public List<MsStrategi> getMsStrategi() {
        return msStrategi;
    }

    public void setMsStrategi(List<MsStrategi> msStrategi) {
        this.msStrategi = msStrategi;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msStrategi);
    }

    public int describeContents() {
        return  0;
    }

}
