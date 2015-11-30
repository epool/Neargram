package com.nearsoft.neargram.view.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearsoft.neargram.BR;
import com.nearsoft.neargram.model.realm.Comment;

/**
 * Comment view model.
 * Created by epool on 11/26/15.
 */
public class CommentVM extends BaseObservable implements Parcelable {

    public static final Creator<CommentVM> CREATOR = new Creator<CommentVM>() {
        public CommentVM createFromParcel(Parcel source) {
            return new CommentVM(source);
        }

        public CommentVM[] newArray(int size) {
            return new CommentVM[size];
        }
    };

    private String id;
    private String text;
    private long createdTime;
    private UserVM userVM;

    public CommentVM() {
    }

    public CommentVM(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.createdTime = comment.getCreatedTime();
    }

    protected CommentVM(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
        this.createdTime = in.readLong();
        this.userVM = in.readParcelable(UserVM.class.getClassLoader());
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
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
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
    public UserVM getUserVM() {
        return userVM;
    }

    public void setUserVM(UserVM userVM) {
        this.userVM = userVM;
        notifyPropertyChanged(BR.userVM);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.text);
        dest.writeLong(this.createdTime);
        dest.writeParcelable(this.userVM, flags);
    }

}