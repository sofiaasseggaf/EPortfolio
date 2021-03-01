
package com.project.eportfolio.model.blog;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("blog")
    @Expose
    private List<Blog> blog = null;
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
    private final static long serialVersionUID = -6145157064099792609L;

    protected Data(Parcel in) {
        in.readList(this.blog, (Blog.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param blog
     */
    public Data(List<Blog> blog) {
        super();
        this.blog = blog;
    }

    public List<Blog> getBlog() {
        return blog;
    }

    public void setBlog(List<Blog> blog) {
        this.blog = blog;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(blog);
    }

    public int describeContents() {
        return  0;
    }

}
