package com.nearsoft.neargram.instagram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("form")
    @Expose
    private User user;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * @return The createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime The created_time
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

}
