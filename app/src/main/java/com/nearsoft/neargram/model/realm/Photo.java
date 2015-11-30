package com.nearsoft.neargram.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Photo model.
 * Created by epool on 11/27/15.
 */
public class Photo extends RealmObject {
    public static final String ID = "id";
    public static final String CREATED_TIME = "createdTime";

    @PrimaryKey
    private String id;
    private String lowResolutionUrl;
    private String standardResolutionUrl;
    private long createdTime;
    private Caption caption;
    private int likes;
    private User user;
    private RealmList<Comment> comments;

    public Photo() {
    }

    public Photo(com.nearsoft.neargram.instagram.Photo photo) {
        this.id = photo.getId();
        this.lowResolutionUrl = photo.getImages().getLowResolution().getUrl();
        this.standardResolutionUrl = photo.getImages().getStandardResolution().getUrl();
        this.createdTime = photo.getCreatedTime();
        if (photo.getCaption() != null) {
            this.caption = new Caption(photo.getCaption());
        }
        this.likes = photo.getLikes().getCount();
        this.user = new User(photo.getUser());
        this.comments = new RealmList<>();
        for (com.nearsoft.neargram.instagram.Comment comment : photo.getComments().getData()) {
            this.comments.add(new Comment(comment));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLowResolutionUrl() {
        return lowResolutionUrl;
    }

    public void setLowResolutionUrl(String lowResolutionUrl) {
        this.lowResolutionUrl = lowResolutionUrl;
    }

    public String getStandardResolutionUrl() {
        return standardResolutionUrl;
    }

    public void setStandardResolutionUrl(String standardResolutionUrl) {
        this.standardResolutionUrl = standardResolutionUrl;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public Caption getCaption() {
        return caption;
    }

    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RealmList<Comment> getComments() {
        return comments;
    }

    public void setComments(RealmList<Comment> comments) {
        this.comments = comments;
    }
}
