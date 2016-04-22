package com.nearsoft.neargram.view.adapters.realm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nearsoft.neargram.model.realm.Photo;
import com.nearsoft.neargram.view.fragments.PhotoDetailFragment;
import com.nearsoft.neargram.view.models.PhotoVM;

import io.realm.RealmResults;

/**
 * Photo detail pager adapter.
 * Created by epool on 11/30/15.
 */
public class PhotoDetailPagerAdapter extends FragmentPagerAdapter {
    private RealmResults<Photo> photos;

    public PhotoDetailPagerAdapter(FragmentManager fragmentManager, RealmResults<Photo> photos) {
        super(fragmentManager);
        this.photos = photos;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoDetailFragment.newInstance(new PhotoVM(photos.get(position)));
    }

    @Override
    public int getCount() {
        return photos.size();
    }
}
