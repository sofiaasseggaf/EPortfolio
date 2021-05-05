
package com.project.eportfolio.model.kategoripo;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("po_kategori")
    @Expose
    private List<PoKategorus> poKategori = null;
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
    private final static long serialVersionUID = 8521975428318923237L;

    protected Data(android.os.Parcel in) {
        in.readList(this.poKategori, (PoKategorus.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param poKategori
     */
    public Data(List<PoKategorus> poKategori) {
        super();
        this.poKategori = poKategori;
    }

    public List<PoKategorus> getPoKategori() {
        return poKategori;
    }

    public void setPoKategori(List<PoKategorus> poKategori) {
        this.poKategori = poKategori;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("poKategori");
        sb.append('=');
        sb.append(((this.poKategori == null)?"<null>":this.poKategori));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(poKategori);
    }

    public int describeContents() {
        return  0;
    }

}
