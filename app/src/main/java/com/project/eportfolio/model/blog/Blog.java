
package com.project.eportfolio.model.blog;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Blog implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("viewers")
    @Expose
    private String viewers;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    public final static Creator<Blog> CREATOR = new Creator<Blog>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }

        public Blog[] newArray(int size) {
            return (new Blog[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1235560260780024649L;

    protected Blog(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.tags = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.viewers = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Blog() {
    }

    /**
     * 
     * @param image
     * @param createdAt
     * @param viewers
     * @param author
     * @param id
     * @param title
     * @param category
     * @param slug
     * @param content
     * @param tags
     * @param status
     * @param updatedAt
     */
    public Blog(String id, String title, String slug, String content, String image, String tags, String category, String status, String author, String viewers, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.image = image;
        this.tags = tags;
        this.category = category;
        this.status = status;
        this.author = author;
        this.viewers = viewers;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getViewers() {
        return viewers;
    }

    public void setViewers(String viewers) {
        this.viewers = viewers;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(slug);
        dest.writeValue(content);
        dest.writeValue(image);
        dest.writeValue(tags);
        dest.writeValue(category);
        dest.writeValue(status);
        dest.writeValue(author);
        dest.writeValue(viewers);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}
