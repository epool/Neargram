package com.nearsoft.neargram.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Comment model.
 * Created by epool on 11/27/15.
 */
public class Comment extends RealmObject {

    @PrimaryKey
    private String id;
    private String text;
    private long createdTime;
    private User user;

    public Comment() {
    }

    public Comment(com.nearsoft.neargram.instagram.Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.createdTime = comment.getCreatedTime();
        User user = new User();
        user.setId(comment.getUser().getId());
        user.setUsername(comment.getUser().getUsername());
        user.setFullName(comment.getUser().getFullName());
        user.setProfilePicture(comment.getUser().getProfilePicture());
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
