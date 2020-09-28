
package com.project.eportfolio.model.user;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("aauth_users")
    @Expose
    private List<AauthUser> aauthUsers = null;
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
    private final static long serialVersionUID = -1572454247171535909L;

    protected Data(Parcel in) {
        in.readList(this.aauthUsers, (AauthUser.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param aauthUsers
     */
    public Data(List<AauthUser> aauthUsers) {
        super();
        this.aauthUsers = aauthUsers;
    }

    public List<AauthUser> getAauthUsers() {
        return aauthUsers;
    }

    public void setAauthUsers(List<AauthUser> aauthUsers) {
        this.aauthUsers = aauthUsers;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(aauthUsers);
    }

    public int describeContents() {
        return  0;
    }

}
