package com.nearsoft.neargram.view.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearsoft.neargram.BR;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Comment view model.
 * Created by epool on 11/26/15.
 */
public class CommentVM extends BaseObservable implements Parcelable {
    public static final Parcelable.Creator<CommentVM> CREATOR = new Parcelable.Creator<CommentVM>() {
        public CommentVM createFromParcel(Parcel source) {
            return new CommentVM(source);
        }

        public CommentVM[] newArray(int size) {
            return new CommentVM[size];
        }
    };
    private String id;
    private long createdTime;
    private String text;

    public CommentVM() {
    }

    protected CommentVM(Parcel in) {
        this.id = in.readString();
        this.createdTime = in.readLong();
        this.text = in.readString();
    }

    public CommentVM(com.nearsoft.neargram.instagram.Comment comment) {
        this.id = comment.getId();
        this.createdTime = comment.getCreatedTime();
        this.text = comment.getText();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.createdTime);
        dest.writeString(this.text);
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

    public String getFormattedDate() {
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        return dateFormat.format(new Date(createdTime * 1000));
    }
}
