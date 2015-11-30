package com.nearsoft.neargram.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.adapters.PhotoItemRecyclerViewAdapter;
import com.nearsoft.neargram.databinding.ActivityPhotoListBinding;
import com.nearsoft.neargram.instagram.Photo;
import com.nearsoft.neargram.view.models.PhotoVM;
import com.nearsoft.neargram.webservices.InstagramService;
import com.nearsoft.neargram.webservices.responses.InstagramSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * An activity representing a list of Photos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PhotoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PhotoListActivity extends BaseActivity implements PhotoItemRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ActivityPhotoListBinding binding;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mIsTablet;
    private Call<InstagramSearchResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getBinding(ActivityPhotoListBinding.class);

        binding.toolbar.setTitle(getTitle());

        binding.photoListLayout.swipeRefreshLayout.setOnRefreshListener(this);

        binding.executePendingBindings();

        // The detail container view will be present only in the
        // large-screen layouts (res/values-w900dp).
        mIsTablet = getResources().getBoolean(R.bool.isTablet);

        onRefresh();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_photo_list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void onItemClick(View view, int position, PhotoVM photoVM) {
        Context context = view.getContext();
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.putExtra(PhotoDetailActivity.ARG_PHOTO, photoVM);

        String transitionName = getString(R.string.photo_transition_name);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view.findViewById(R.id.imageViewPhoto),   // The view which starts the transition
                transitionName    // The transitionName of the view we’re transitioning to
        );
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    private void setupRecyclerView(List<PhotoVM> photoVMs) {
        PhotoItemRecyclerViewAdapter adapter = new PhotoItemRecyclerViewAdapter(photoVMs, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(PhotoListActivity.this, mIsTablet ? 3 : 1);
        binding.photoListLayout.photoList.setLayoutManager(layoutManager);
        binding.photoListLayout.photoList.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        binding.photoListLayout.swipeRefreshLayout.setRefreshing(true);

        InstagramService instagramService = getApplicationComponent().provideInstagramService();
        call = instagramService.getPopularPhotos(29.0974411, -111.0220760, 5000);
        call.enqueue(new Callback<InstagramSearchResponse>() {
            @Override
            public void onResponse(Response<InstagramSearchResponse> response, Retrofit retrofit) {
                List<PhotoVM> photoVMs = new ArrayList<>();
                for (Photo photo : response.body().getData()) {
                    photoVMs.add(new PhotoVM(photo));
                }
                setupRecyclerView(photoVMs);

                binding.photoListLayout.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(binding.getRoot(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

                binding.photoListLayout.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
