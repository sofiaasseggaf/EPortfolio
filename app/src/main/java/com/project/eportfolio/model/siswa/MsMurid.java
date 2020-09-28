
package com.project.eportfolio.model.siswa;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MsMurid implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("sekolahid")
    @Expose
    private String sekolahid;
    @SerializedName("kelasid")
    @Expose
    private String kelasid;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("midname")
    @Expose
    private String midname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("nis")
    @Expose
    private String nis;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("ttl")
    @Expose
    private String ttl;
    @SerializedName("religion")
    @Expose
    private String religion;
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
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("photo")
    @Expose
    private String photo;
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
    public final static Creator<MsMurid> CREATOR = new Creator<MsMurid>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MsMurid createFromParcel(Parcel in) {
            return new MsMurid(in);
        }

        public MsMurid[] newArray(int size) {
            return (new MsMurid[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2354913465146543554L;

    protected MsMurid(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.userid = ((String) in.readValue((String.class.getClassLoader())));
        this.sekolahid = ((String) in.readValue((String.class.getClassLoader())));
        this.kelasid = ((String) in.readValue((String.class.getClassLoader())));
        this.firstname = ((String) in.readValue((String.class.getClassLoader())));
        this.midname = ((String) in.readValue((String.class.getClassLoader())));
        this.lastname = ((String) in.readValue((String.class.getClassLoader())));
        this.nis = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((String) in.readValue((String.class.getClassLoader())));
        this.ttl = ((String) in.readValue((String.class.getClassLoader())));
        this.religion = ((String) in.readValue((String.class.getClassLoader())));
        this.province = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.district = ((String) in.readValue((String.class.getClassLoader())));
        this.village = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.postalcode = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((Object) in.readValue((Object.class.getClassLoader())));
        this.photo = ((String) in.readValue((String.class.getClassLoader())));
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
    public MsMurid() {
    }

    /**
     * 
     * @param firstname
     * @param gender
     * @param city
     * @param createddate
     * @param isactive
     * @param userid
     * @param password
     * @param province
     * @param updatedate
     * @param createdby
     * @param postalcode
     * @param nis
     * @param id
     * @param village
     * @param email
     * @param sekolahid
     * @param address
     * @param photo
     * @param kelasid
     * @param ttl
     * @param lastname
     * @param religion
     * @param phone
     * @param updateby
     * @param district
     * @param midname
     */
    public MsMurid(String id, String userid, String sekolahid, String kelasid, String firstname, String midname, String lastname, String nis, String gender, String ttl, String religion, String province, String city, String district, String village, String address, String postalcode, String phone, String email, Object password, String photo, String createdby, String createddate, String updateby, String updatedate, Object isactive) {
        super();
        this.id = id;
        this.userid = userid;
        this.sekolahid = sekolahid;
        this.kelasid = kelasid;
        this.firstname = firstname;
        this.midname = midname;
        this.lastname = lastname;
        this.nis = nis;
        this.gender = gender;
        this.ttl = ttl;
        this.religion = religion;
        this.province = province;
        this.city = city;
        this.district = district;
        this.village = village;
        this.address = address;
        this.postalcode = postalcode;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.createdby = createdby;
        this.createddate = createddate;
        this.updateby = updateby;
        this.updatedate = updatedate;
        this.isactive = isactive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSekolahid() {
        return sekolahid;
    }

    public void setSekolahid(String sekolahid) {
        this.sekolahid = sekolahid;
    }

    public String getKelasid() {
        return kelasid;
    }

    public void setKelasid(String kelasid) {
        this.kelasid = kelasid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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
        dest.writeValue(id);
        dest.writeValue(userid);
        dest.writeValue(sekolahid);
        dest.writeValue(kelasid);
        dest.writeValue(firstname);
        dest.writeValue(midname);
        dest.writeValue(lastname);
        dest.writeValue(nis);
        dest.writeValue(gender);
        dest.writeValue(ttl);
        dest.writeValue(religion);
        dest.writeValue(province);
        dest.writeValue(city);
        dest.writeValue(district);
        dest.writeValue(village);
        dest.writeValue(address);
        dest.writeValue(postalcode);
        dest.writeValue(phone);
        dest.writeValue(email);
        dest.writeValue(password);
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
