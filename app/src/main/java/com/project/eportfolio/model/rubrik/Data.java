
package com.project.eportfolio.model.rubrik;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_rubrik")
    @Expose
    private List<MsRubrik> msRubrik = null;
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
    private final static long serialVersionUID = -7840437232558358869L;

    protected Data(Parcel in) {
        in.readList(this.msRubrik, (MsRubrik.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msRubrik
     */
    public Data(List<MsRubrik> msRubrik) {
        super();
        this.msRubrik = msRubrik;
    }

    public List<MsRubrik> getMsRubrik() {
        return msRubrik;
    }

    public void setMsRubrik(List<MsRubrik> msRubrik) {
        this.msRubrik = msRubrik;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msRubrik);
    }

    public int describeContents() {
        return  0;
    }

}
