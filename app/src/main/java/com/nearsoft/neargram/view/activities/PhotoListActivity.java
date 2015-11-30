package com.nearsoft.neargram.view.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.ActivityPhotoListBinding;
import com.nearsoft.neargram.model.PhotoModel;
import com.nearsoft.neargram.model.realm.Photo;
import com.nearsoft.neargram.util.SyncUtil;
import com.nearsoft.neargram.view.adapters.realm.PhotoRecyclerViewAdapter;
import com.nearsoft.neargram.view.models.PhotoVM;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * An activity representing a list of Photos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PhotoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PhotoListActivity extends BaseActivity implements PhotoRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, RealmChangeListener {
    private Realm realm;
    private PhotoRecyclerViewAdapter photoRecyclerViewAdapter;
    private ActivityPhotoListBinding binding;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mIsTablet;

    /**
     * Handle to a SyncObserver. The ProgressBar element is visible until the SyncObserver reports
     * that the sync is complete.
     * <p/>
     * <p>This allows us to delete our SyncObserver once the application is no longer in the
     * foreground.
     */
    private Object mSyncObserverHandle;

    /**
     * Create a new anonymous SyncStatusObserver. It's attached to the app's ContentResolver in
     * onResume(), and removed in onPause(). If status changes, it sets the state of the Refresh
     * button. If a sync is active or pending, the Refresh button is replaced by an indeterminate
     * ProgressBar; otherwise, the button itself is displayed.
     */
    private SyncStatusObserver mSyncStatusObserver = new SyncStatusObserver() {
        /** Callback invoked with the sync adapter status changes. */
        @Override
        public void onStatusChanged(int which) {
            runOnUiThread(new Runnable() {
                /**
                 * The SyncAdapter runs on a background thread. To update the UI, onStatusChanged()
                 * runs on the UI thread.
                 */
                @Override
                public void run() {
                    binding.photoListLayout.swipeRefreshLayout.setRefreshing(SyncUtil.isSyncing());
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getBinding(ActivityPhotoListBinding.class);

        binding.toolbar.setTitle(getTitle());

        binding.photoListLayout.swipeRefreshLayout.setOnRefreshListener(this);

        binding.photoListLayout.textViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        binding.executePendingBindings();

        // The detail container view will be present only in the
        // large-screen layouts (res/values-w900dp).
        mIsTablet = getResources().getBoolean(R.bool.isTablet);

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);

        setupRecyclerView();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_photo_list;
    }

    private void setupRecyclerView() {
        binding.photoListLayout.photoList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(PhotoListActivity.this, mIsTablet ? 3 : 1);
        binding.photoListLayout.photoList.setLayoutManager(layoutManager);

        RealmResults<Photo> photos = PhotoModel.getAllPhotos(realm);
        photoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(this, photos, true, this);
        binding.photoListLayout.photoList.setAdapter(photoRecyclerViewAdapter);

        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSyncStatusObserver.onStatusChanged(0);

        // Watch for sync state changes
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING |
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        mSyncObserverHandle = ContentResolver.addStatusChangeListener(mask, mSyncStatusObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mSyncObserverHandle != null) {
            ContentResolver.removeStatusChangeListener(mSyncObserverHandle);
            mSyncObserverHandle = null;
        }
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
    public void onItemClick(View view, int position, PhotoVM photoVM) {
        Context context = view.getContext();
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.putExtra(PhotoDetailActivity.ARG_PHOTO, photoVM);
        intent.putExtra(PhotoDetailActivity.ARG_POSITION, position);

        String transitionName = getString(R.string.photo_transition_name);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view.findViewById(R.id.imageViewPhoto),   // The view which starts the transition
                transitionName    // The transitionName of the view we’re transitioning to
        );
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    public void onRefresh() {
        if (!SyncUtil.isSyncing()) {
            SyncUtil.TriggerRefresh();
        }
    }

    @Override
    public void onChange() {
        updateUI();
    }

    private void updateUI() {
        if (photoRecyclerViewAdapter != null) {
            if (photoRecyclerViewAdapter.getItemCount() == 0) {
                binding.photoListLayout.photoList.setVisibility(View.GONE);
                binding.photoListLayout.textViewEmpty.setVisibility(View.VISIBLE);
            } else {
                binding.photoListLayout.photoList.setVisibility(View.VISIBLE);
                binding.photoListLayout.textViewEmpty.setVisibility(View.GONE);
            }
            photoRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

}
