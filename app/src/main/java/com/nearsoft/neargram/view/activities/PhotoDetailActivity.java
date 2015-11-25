package com.nearsoft.neargram.view.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.ActivityPhotoDetailBinding;
import com.nearsoft.neargram.view.models.PhotoVM;

/**
 * An activity representing a single Photo detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PhotoListActivity}.
 */
public class PhotoDetailActivity extends BaseActivity {
    public static String ARG_PHOTO = "ARG_PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPhotoDetailBinding binding = getBinding(ActivityPhotoDetailBinding.class);

        PhotoVM photoVM = getIntent().getParcelableExtra(ARG_PHOTO);
        binding.setPhoto(photoVM);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    protected int getToolbarId() {
        return R.id.detail_toolbar;
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
