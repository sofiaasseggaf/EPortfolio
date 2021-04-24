
package com.project.eportfolio.model.gradesekolah;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("ms_grade_sekolah")
    @Expose
    private List<MsGradeSekolah> msGradeSekolah = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3035369757603596307L;

    protected Data(android.os.Parcel in) {
        in.readList(this.msGradeSekolah, (MsGradeSekolah.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param msGradeSekolah
     */
    public Data(List<MsGradeSekolah> msGradeSekolah) {
        super();
        this.msGradeSekolah = msGradeSekolah;
    }

    public List<MsGradeSekolah> getMsGradeSekolah() {
        return msGradeSekolah;
    }

    public void setMsGradeSekolah(List<MsGradeSekolah> msGradeSekolah) {
        this.msGradeSekolah = msGradeSekolah;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(msGradeSekolah);
    }

    public int describeContents() {
        return  0;
    }

}
