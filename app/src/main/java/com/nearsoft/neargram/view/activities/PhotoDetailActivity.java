package com.nearsoft.neargram.view.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.nearsoft.neargram.R;
import com.nearsoft.neargram.adapters.CommentItemRecyclerViewAdapter;
import com.nearsoft.neargram.databinding.ActivityPhotoDetailBinding;
import com.nearsoft.neargram.util.ViewUtil;
import com.nearsoft.neargram.view.models.CommentVM;
import com.nearsoft.neargram.view.models.PhotoVM;

import java.util.List;

/**
 * An activity representing a single Photo detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PhotoListActivity}.
 */
public class PhotoDetailActivity extends BaseActivity {
    public static String ARG_PHOTO = "ARG_PHOTO";
    private ActivityPhotoDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getBinding(ActivityPhotoDetailBinding.class);

        PhotoVM photoVM = getIntent().getParcelableExtra(ARG_PHOTO);
        binding.setPhoto(photoVM);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupRecyclerView(photoVM.getCommentVMs());

        Glide.with(this)
                .load(photoVM.getStandardResolutionUrl())
                .asBitmap()
                .into(new BitmapImageViewTarget(binding.imageViewPhotoCover) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(bitmap, glideAnimation);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                final Palette.Swatch swatch = palette.getVibrantSwatch();
                                binding.toolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantColor(ContextCompat.getColor(PhotoDetailActivity.this, R.color.colorPrimaryDark)));
                                binding.toolbarLayout.setContentScrimColor(palette.getVibrantColor(ContextCompat.getColor(PhotoDetailActivity.this, R.color.colorPrimary)));
                                if (swatch != null) {
                                    int color = swatch.getTitleTextColor();
                                    binding.toolbarLayout.setExpandedTitleTextAppearance(R.style.AppTheme);
                                    binding.toolbarLayout.setExpandedTitleColor(color);
                                    binding.toolbarLayout.setCollapsedTitleTextColor(color);
                                    ViewUtil.Toolbar.colorizeToolbar(binding.toolbar, color, PhotoDetailActivity.this);
                                }
                            }
                        });
                    }
                });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(photoVM.getUsername());
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_photo_detail;
    }

    private void setupRecyclerView(List<CommentVM> commentVMs) {
        CommentItemRecyclerViewAdapter adapter = new CommentItemRecyclerViewAdapter(commentVMs, null);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewComments.setLayoutManager(layoutManager);
        binding.recyclerViewComments.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
