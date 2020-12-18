
package com.project.eportfolio.model.sekolah;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsSekolah implements Serializable, Parcelable
{

    @SerializedName("id_sekolah")
    @Expose
    private String idSekolah;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("name_sekolah")
    @Expose
    private String nameSekolah;
    @SerializedName("nis")
    @Expose
    private String nis;
    @SerializedName("status_sekolah")
    @Expose
    private String statusSekolah;
    @SerializedName("accreditation")
    @Expose
    private String accreditation;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("postalcode")
    @Expose
    private String postalcode;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("photo")
    @Expose
    private Object photo;
    @SerializedName("createdby")
    @Expose
    private String createdby;
    @SerializedName("createddate")
    @Expose
    private String createddate;
    @SerializedName("updateby")
    @Expose
    private String updateby;
    @SerializedName("updatedate")
    @Expose
    private String updatedate;
    @SerializedName("isactive")
    @Expose
    private Object isactive;
    public final static Creator<MsSekolah> CREATOR = new Creator<MsSekolah>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsSekolah createFromParcel(Parcel in) {
            return new MsSekolah(in);
        }

        public MsSekolah[] newArray(int size) {
            return (new MsSekolah[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6387758654041562138L;

    protected MsSekolah(Parcel in) {
        this.idSekolah = ((String) in.readValue((String.class.getClassLoader())));
        this.userid = ((String) in.readValue((String.class.getClassLoader())));
        this.nameSekolah = ((String) in.readValue((String.class.getClassLoader())));
        this.nis = ((String) in.readValue((String.class.getClassLoader())));
        this.statusSekolah = ((String) in.readValue((String.class.getClassLoader())));
        this.accreditation = ((String) in.readValue((String.class.getClassLoader())));
        this.province = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.district = ((String) in.readValue((String.class.getClassLoader())));
        this.village = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.postalcode = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.website = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.photo = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createdby = ((String) in.readValue((String.class.getClassLoader())));
        this.createddate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateby = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedate = ((String) in.readValue((String.class.getClassLoader())));
        this.isactive = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MsSekolah() {
    }

    /**
     * 
     * @param website
     * @param address
     * @param statusSekolah
     * @param city
     * @param createddate
     * @param isactive
     * @param photo
     * @param userid
     * @param province
     * @param updatedate
     * @param phone
     * @param createdby
     * @param updateby
     * @param postalcode
     * @param district
     * @param nis
     * @param accreditation
     * @param nameSekolah
     * @param village
     * @param email
     * @param idSekolah
     */
    public MsSekolah(String idSekolah, String userid, String nameSekolah, String nis, String statusSekolah, String accreditation, String province, String city, String district, String village, String address, String postalcode, String phone, String website, String email, Object photo, String createdby, String createddate, String updateby, String updatedate, Object isactive) {
        super();
        this.idSekolah = idSekolah;
        this.userid = userid;
        this.nameSekolah = nameSekolah;
        this.nis = nis;
        this.statusSekolah = statusSekolah;
        this.accreditation = accreditation;
        this.province = province;
        this.city = city;
        this.district = district;
        this.village = village;
        this.address = address;
        this.postalcode = postalcode;
        this.phone = phone;
        this.website = website;
        this.email = email;
        this.photo = photo;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.isactive = isactive;
    }

    public String getIdSekolah() {
        return idSekolah;
    }

    public void setIdSekolah(String idSekolah) {
        this.idSekolah = idSekolah;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNameSekolah() {
        return nameSekolah;
    }

    public void setNameSekolah(String nameSekolah) {
        this.nameSekolah = nameSekolah;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getStatusSekolah() {
        return statusSekolah;
    }

    public void setStatusSekolah(String statusSekolah) {
        this.statusSekolah = statusSekolah;
    }

    public String getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(String accreditation) {
        this.accreditation = accreditation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public Object getIsactive() {
        return isactive;
    }

    public void setIsactive(Object isactive) {
        this.isactive = isactive;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idSekolah);
        dest.writeValue(userid);
        dest.writeValue(nameSekolah);
        dest.writeValue(nis);
        dest.writeValue(statusSekolah);
        dest.writeValue(accreditation);
        dest.writeValue(province);
        dest.writeValue(city);
        dest.writeValue(district);
        dest.writeValue(village);
        dest.writeValue(address);
        dest.writeValue(postalcode);
        dest.writeValue(phone);
        dest.writeValue(website);
        dest.writeValue(email);
        dest.writeValue(photo);
        dest.writeValue(createdby);
        dest.writeValue(createddate);
        dest.writeValue(updateby);
        dest.writeValue(updatedate);
        dest.writeValue(isactive);
    }

    public int describeContents() {
        return  0;
    }

}
