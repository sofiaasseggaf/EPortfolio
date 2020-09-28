
package com.project.eportfolio.model.grade;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_grade")
    @Expose
    private List<MsGrade> msGrade = null;
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
    private final static long serialVersionUID = 1627590426350939834L;

    protected Data(Parcel in) {
        in.readList(this.msGrade, (MsGrade.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msGrade
     */
    public Data(List<MsGrade> msGrade) {
        super();
        this.msGrade = msGrade;
    }

    public List<MsGrade> getMsGrade() {
        return msGrade;
    }

    public void setMsGrade(List<MsGrade> msGrade) {
        this.msGrade = msGrade;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(msGrade);
    }

    public int describeContents() {
        return  0;
    }

}
