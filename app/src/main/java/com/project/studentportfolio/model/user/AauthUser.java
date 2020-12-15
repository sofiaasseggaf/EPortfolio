
package com.project.studentportfolio.model.user;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AauthUser implements Serializable, Parcelable
{

    @SerializedName("id_sekolah")
    @Expose
    private String idSekolah;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("oauth_uid")
    @Expose
    private Object oauthUid;
    @SerializedName("oauth_provider")
    @Expose
    private Object oauthProvider;
    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("keypass")
    @Expose
    private String keypass;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("id")
    @Expose
    private String id;
    public final static Creator<AauthUser> CREATOR = new Creator<AauthUser>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AauthUser createFromParcel(Parcel in) {
            return new AauthUser(in);
        }

        public AauthUser[] newArray(int size) {
            return (new AauthUser[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6769627206325028778L;

    protected AauthUser(Parcel in) {
        this.idSekolah = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.groupId = ((String) in.readValue((String.class.getClassLoader())));
        this.oauthUid = ((Object) in.readValue((Object.class.getClassLoader())));
        this.oauthProvider = ((Object) in.readValue((Object.class.getClassLoader())));
        this.pass = ((String) in.readValue((String.class.getClassLoader())));
        this.keypass = ((String) in.readValue((Object.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
        this.avatar = ((String) in.readValue((String.class.getClassLoader())));
        this.foto = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AauthUser() {
    }

    public String getIdSekolah() {
        return idSekolah;
    }

    public void setIdSekolah(String idSekolah) {
        this.idSekolah = idSekolah;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Object getOauthUid() {
        return oauthUid;
    }

    public void setOauthUid(Object oauthUid) {
        this.oauthUid = oauthUid;
    }

    public Object getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(Object oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getKeypass() {
        return keypass;
    }

    public void setKeypass(String keypass) {
        this.keypass = keypass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idSekolah);
        dest.writeValue(email);
        dest.writeValue(groupId);
        dest.writeValue(oauthUid);
        dest.writeValue(oauthProvider);
        dest.writeValue(pass);
        dest.writeValue(keypass);
        dest.writeValue(username);
        dest.writeValue(fullName);
        dest.writeValue(avatar);
        dest.writeValue(foto);
        dest.writeValue(id);
    }

    public int describeContents() {
        return  0;
    }

}
