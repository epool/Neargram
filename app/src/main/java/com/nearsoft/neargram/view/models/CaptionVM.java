package com.nearsoft.neargram.view.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearsoft.neargram.BR;
import com.nearsoft.neargram.model.realm.Caption;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Caption view model.
 * Created by epool on 11/28/15.
 */
public class CaptionVM extends BaseObservable implements Parcelable {

    public static final Creator<CaptionVM> CREATOR = new Creator<CaptionVM>() {
        public CaptionVM createFromParcel(Parcel source) {
            return new CaptionVM(source);
        }

        public CaptionVM[] newArray(int size) {
            return new CaptionVM[size];
        }
    };

    private String id;
    private long createdTime;
    private String text;
    private UserVM userVM;

    public CaptionVM() {
    }

    public CaptionVM(Caption caption) {
        this.id = caption.getId();
        this.createdTime = caption.getCreatedTime();
        this.text = caption.getText();
    }

    protected CaptionVM(Parcel in) {
        this.id = in.readString();
        this.createdTime = in.readLong();
        this.text = in.readString();
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
    public UserVM getUserVM() {
        return userVM;
    }

    public void setUserVM(UserVM userVM) {
        this.userVM = userVM;
        notifyPropertyChanged(BR.userVM);
    }

    public String getFormattedDate() {
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        return dateFormat.format(new Date(createdTime * 1000));
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
        dest.writeParcelable(this.userVM, flags);
    }

}
