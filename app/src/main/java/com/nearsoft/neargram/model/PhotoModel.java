package com.nearsoft.neargram.model;

import com.nearsoft.neargram.model.realm.Caption;
import com.nearsoft.neargram.model.realm.Comment;
import com.nearsoft.neargram.model.realm.Photo;
import com.nearsoft.neargram.model.realm.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Photo model to handle photo actions.
 * Created by epool on 11/28/15.
 */
public class PhotoModel {

    public static Photo getPhotoById(Realm realm, String id) {
        return realm.where(Photo.class).equalTo(Photo.ID, id).findFirst();
    }

    public static RealmResults<Photo> getAllPhotos(Realm realm) {
        return realm.where(Photo.class).findAllSorted(Photo.CREATED_TIME, Sort.DESCENDING);
    }

    public static void savePhotos(List<com.nearsoft.neargram.instagram.Photo> photos) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        for (com.nearsoft.neargram.instagram.Photo photo : photos) {
            Photo p = new Photo(photo);
            realm.copyToRealmOrUpdate(p);
        }
        realm.commitTransaction();

        realm.close();
    }

    public static void clearPhotos() {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.delete(Caption.class);
        realm.delete(Comment.class);
        realm.delete(Photo.class);
        realm.delete(User.class);
        realm.commitTransaction();

        realm.close();
    }

}
