
package com.project.eportfolio.model.kategoripo;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoKategorus implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    public final static Creator<PoKategorus> CREATOR = new Creator<PoKategorus>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PoKategorus createFromParcel(android.os.Parcel in) {
            return new PoKategorus(in);
        }

        public PoKategorus[] newArray(int size) {
            return (new PoKategorus[size]);
        }

    }
    ;
    private final static long serialVersionUID = 251819345865930704L;

    protected PoKategorus(android.os.Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.nama = ((String) in.readValue((String.class.getClassLoader())));
        this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public PoKategorus() {
    }

    /**
     * 
     * @param nama
     * @param id
     * @param deskripsi
     */
    public PoKategorus(String id, String nama, String deskripsi) {
        super();
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PoKategorus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("nama");
        sb.append('=');
        sb.append(((this.nama == null)?"<null>":this.nama));
        sb.append(',');
        sb.append("deskripsi");
        sb.append('=');
        sb.append(((this.deskripsi == null)?"<null>":this.deskripsi));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(nama);
        dest.writeValue(deskripsi);
    }

    public int describeContents() {
        return  0;
    }

}
