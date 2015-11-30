package com.nearsoft.neargram.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Caption model.
 * Created by epool on 11/27/15.
 */
public class Caption extends RealmObject {
    @PrimaryKey
    private String id;
    private String text;
    private long createdTime;
    private User user;

    public Caption() {
    }

    public Caption(com.nearsoft.neargram.instagram.Caption caption) {
        this.id = caption.getId();
        this.text = caption.getText();
        this.createdTime = caption.getCreatedTime();
        User user = new User();
        user.setId(caption.getUser().getId());
        user.setUsername(caption.getUser().getUsername());
        user.setFullName(caption.getUser().getFullName());
        user.setProfilePicture(caption.getUser().getProfilePicture());
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
