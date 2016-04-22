package com.nearsoft.neargram.view.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearsoft.neargram.BR;
import com.nearsoft.neargram.model.realm.User;

/**
 * User view model.
 * Created by epool on 11/28/15.
 */
public class UserVM extends BaseObservable implements Parcelable {

    public static final Parcelable.Creator<UserVM> CREATOR = new Parcelable.Creator<UserVM>() {
        public UserVM createFromParcel(Parcel source) {
            return new UserVM(source);
        }

        public UserVM[] newArray(int size) {
            return new UserVM[size];
        }
    };

    private String id;
    private String username;
    private String fullName;
    private String profilePicture;

    public UserVM() {
    }

    public UserVM(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.profilePicture = user.getProfilePicture();
    }

    protected UserVM(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.fullName = in.readString();
        this.profilePicture = in.readString();
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
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        notifyPropertyChanged(BR.fullName);
    }

    @Bindable
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        notifyPropertyChanged(BR.profilePicture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.fullName);
        dest.writeString(this.profilePicture);
    }

}
