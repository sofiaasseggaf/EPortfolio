
package com.project.eportfolio.model.achievement;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("achievment")
    @Expose
    private List<Achievment> achievment = null;
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
    private final static long serialVersionUID = 4247380129373574126L;

    protected Data(android.os.Parcel in) {
        in.readList(this.achievment, (Achievment.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param achievment
     */
    public Data(List<Achievment> achievment) {
        super();
        this.achievment = achievment;
    }

    public List<Achievment> getAchievment() {
        return achievment;
    }

    public void setAchievment(List<Achievment> achievment) {
        this.achievment = achievment;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(achievment);
    }

    public int describeContents() {
        return  0;
    }

}
