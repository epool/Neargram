package com.nearsoft.neargram.view.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.ActivityPhotoDetailBinding;
import com.nearsoft.neargram.model.PhotoModel;
import com.nearsoft.neargram.view.adapters.realm.PhotoDetailPagerAdapter;
import com.nearsoft.neargram.view.fragments.PhotoDetailFragment;

import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * An activity representing a single Photo detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PhotoListActivity}.
 */
public class PhotoDetailActivity extends BaseActivity implements RealmChangeListener, PhotoDetailFragment.PhotoDetailFragmentListener {
    public static String ARG_PHOTO = "ARG_PHOTO";
    public static String ARG_POSITION = "ARG_POSITION";
    private Realm realm;
    private PhotoDetailPagerAdapter photoDetailPagerAdapter;
    private ActivityPhotoDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getBinding(ActivityPhotoDetailBinding.class);

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);

        setupViewPager();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_photo_detail;
    }

    private void setupViewPager() {
        int position = getIntent().getIntExtra(ARG_POSITION, 0);
        photoDetailPagerAdapter = new PhotoDetailPagerAdapter(getSupportFragmentManager(), PhotoModel.getAllPhotos(realm));
        binding.viewPagerPhotoDetail.setAdapter(photoDetailPagerAdapter);
        binding.viewPagerPhotoDetail.setCurrentItem(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (realm != null) {
            realm.removeChangeListener(this);
            realm.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onChange() {
        photoDetailPagerAdapter.notifyDataSetChanged();
    }
}
