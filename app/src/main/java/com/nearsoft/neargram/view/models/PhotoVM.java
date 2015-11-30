package com.nearsoft.neargram.view.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearsoft.neargram.BR;
import com.nearsoft.neargram.model.realm.Comment;
import com.nearsoft.neargram.model.realm.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Photo view model.
 * Created by epool on 11/24/15.
 */
public class PhotoVM extends BaseObservable implements Parcelable {

    public static final Creator<PhotoVM> CREATOR = new Creator<PhotoVM>() {
        public PhotoVM createFromParcel(Parcel source) {
            return new PhotoVM(source);
        }

        public PhotoVM[] newArray(int size) {
            return new PhotoVM[size];
        }
    };

    private String id;
    private String lowResolutionUrl;
    private String standardResolutionUrl;
    private long createdTime;
    private CaptionVM captionVM;
    private UserVM userVM;
    private List<CommentVM> commentVMs = new ArrayList<>();

    public PhotoVM() {
    }

    public PhotoVM(Photo photo) {
        this.id = photo.getId();
        this.lowResolutionUrl = photo.getLowResolutionUrl();
        this.standardResolutionUrl = photo.getStandardResolutionUrl();
        this.createdTime = photo.getCreatedTime();
        if (photo.getCaption() != null) {
            this.captionVM = new CaptionVM(photo.getCaption());
        }
        this.userVM = new UserVM(photo.getUser());
        for (Comment comment : photo.getComments()) {
            commentVMs.add(new CommentVM(comment));
        }
    }

    protected PhotoVM(Parcel in) {
        this.id = in.readString();
        this.lowResolutionUrl = in.readString();
        this.standardResolutionUrl = in.readString();
        this.createdTime = in.readLong();
        this.captionVM = in.readParcelable(CaptionVM.class.getClassLoader());
        this.userVM = in.readParcelable(UserVM.class.getClassLoader());
        this.commentVMs = in.createTypedArrayList(CommentVM.CREATOR);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getLowResolutionUrl() {
        return lowResolutionUrl;
    }

    public void setLowResolutionUrl(String lowResolutionUrl) {
        this.lowResolutionUrl = lowResolutionUrl;
        notifyPropertyChanged(BR.lowResolutionUrl);
    }

    @Bindable
    public String getStandardResolutionUrl() {
        return standardResolutionUrl;
    }

    public void setStandardResolutionUrl(String standardResolutionUrl) {
        this.standardResolutionUrl = standardResolutionUrl;
        notifyPropertyChanged(BR.standardResolutionUrl);
    }

    @Bindable
    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
        notifyPropertyChanged(BR.createdTime);
    }

    @Bindable
    public CaptionVM getCaptionVM() {
        return captionVM;
    }

    public void setCaptionVM(CaptionVM captionVM) {
        this.captionVM = captionVM;
        notifyPropertyChanged(BR.captionVM);
    }

    @Bindable
    public UserVM getUserVM() {
        return userVM;
    }

    public void setUserVM(UserVM userVM) {
        this.userVM = userVM;
        notifyPropertyChanged(BR.userVM);
    }

    @Bindable
    public List<CommentVM> getCommentVMs() {
        return commentVMs;
    }

    public void setCommentVMs(List<CommentVM> commentVMs) {
        this.commentVMs = commentVMs;
        notifyPropertyChanged(BR.commentVMs);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.lowResolutionUrl);
        dest.writeString(this.standardResolutionUrl);
        dest.writeLong(this.createdTime);
        dest.writeParcelable(this.captionVM, flags);
        dest.writeParcelable(this.userVM, flags);
        dest.writeTypedList(commentVMs);
    }

}
