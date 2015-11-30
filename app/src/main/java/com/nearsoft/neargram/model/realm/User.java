package com.nearsoft.neargram.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User model.
 * Created by epool on 11/27/15.
 */
public class User extends RealmObject {
    @PrimaryKey
    private String id;
    private String username;
    private String fullName;
    private String profilePicture;

    public User() {
    }

    public User(com.nearsoft.neargram.instagram.User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.profilePicture = user.getProfilePicture();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
