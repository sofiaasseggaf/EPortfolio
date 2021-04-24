
package com.project.eportfolio.model.gradesekolah;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelGradeSekolah implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("total")
    @Expose
    private Integer total;
    public final static Creator<ModelGradeSekolah> CREATOR = new Creator<ModelGradeSekolah>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelGradeSekolah createFromParcel(android.os.Parcel in) {
            return new ModelGradeSekolah(in);
        }

        public ModelGradeSekolah[] newArray(int size) {
            return (new ModelGradeSekolah[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8214876048707766935L;

    protected ModelGradeSekolah(android.os.Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.total = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelGradeSekolah() {
    }

    /**
     * 
     * @param total
     * @param data
     * @param message
     * @param status
     */
    public ModelGradeSekolah(Boolean status, String message, Data data, Integer total) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(data);
        dest.writeValue(total);
    }

    public int describeContents() {
        return  0;
    }

}
