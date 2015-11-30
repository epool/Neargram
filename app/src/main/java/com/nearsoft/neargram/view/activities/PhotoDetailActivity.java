package com.nearsoft.neargram.view.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.ActivityPhotoDetailBinding;
import com.nearsoft.neargram.model.PhotoModel;
import com.nearsoft.neargram.model.realm.Photo;
import com.nearsoft.neargram.util.ViewUtil;
import com.nearsoft.neargram.view.adapters.realm.PhotoDetailPagerAdapter;
import com.nearsoft.neargram.view.models.PhotoVM;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * An activity representing a single Photo detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PhotoListActivity}.
 */
public class PhotoDetailActivity extends BaseActivity implements RealmChangeListener {
    public static String ARG_PHOTO = "ARG_PHOTO";
    public static String ARG_POSITION = "ARG_POSITION";
    private Realm realm;
    private RealmResults<Photo> photos;
    private PhotoDetailPagerAdapter photoDetailPagerAdapter;
    private ActivityPhotoDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PhotoVM photoVM = getIntent().getParcelableExtra(ARG_PHOTO);

        binding = getBinding(ActivityPhotoDetailBinding.class);
        binding.setPhoto(photoVM);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);

        updateToolbarColors(photoVM);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupViewPager();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_photo_detail;
    }

    private void setupViewPager() {
        int position = getIntent().getIntExtra(ARG_POSITION, 0);
        photos = PhotoModel.getAllPhotos(realm);
        photoDetailPagerAdapter = new PhotoDetailPagerAdapter(getSupportFragmentManager(), photos);
        binding.viewPagerPhotoDetail.setAdapter(photoDetailPagerAdapter);
        binding.viewPagerPhotoDetail.setCurrentItem(position);
        binding.viewPagerPhotoDetail.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                PhotoVM photoVM = new PhotoVM(photos.get(position));
                binding.setPhoto(photoVM);
                binding.executePendingBindings();

                updateToolbarColors(photoVM);
            }
        });
    }

    private void updateToolbarColors(PhotoVM photoVM) {
        Glide.with(PhotoDetailActivity.this)
                .load(photoVM.getStandardResolutionUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                final Palette.Swatch swatch = palette.getVibrantSwatch();
                                binding.toolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantColor(ContextCompat.getColor(PhotoDetailActivity.this, R.color.colorPrimaryDark)));
                                binding.toolbarLayout.setContentScrimColor(palette.getVibrantColor(ContextCompat.getColor(PhotoDetailActivity.this, R.color.colorPrimary)));

                                binding.toolbarLayout.setExpandedTitleColor(Color.WHITE);
                                binding.toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                                ViewUtil.Toolbar.colorizeToolbar(binding.toolbar, Color.WHITE, PhotoDetailActivity.this);

                                if (swatch != null) {
                                    int color = swatch.getTitleTextColor();
                                    binding.toolbarLayout.setExpandedTitleColor(color);
                                    binding.toolbarLayout.setCollapsedTitleTextColor(color);
                                    ViewUtil.Toolbar.colorizeToolbar(binding.toolbar, color, PhotoDetailActivity.this);
                                }
                            }
                        });
                    }
                });
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
