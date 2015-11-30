package com.nearsoft.neargram.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.adapters.PhotoItemRecyclerViewAdapter;
import com.nearsoft.neargram.databinding.ActivityPhotoListBinding;
import com.nearsoft.neargram.dummy.DummyContent;
import com.nearsoft.neargram.view.fragments.PhotoDetailFragment;

/**
 * An activity representing a list of Photos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PhotoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PhotoListActivity extends BaseActivity implements PhotoItemRecyclerViewAdapter.OnItemClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPhotoListBinding binding = getBinding(ActivityPhotoListBinding.class);

        binding.toolbar.setTitle(getTitle());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupRecyclerView(binding.photoListLayout.photoList);

        if (binding.photoListLayout.photoDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_photo_list;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        PhotoItemRecyclerViewAdapter adapter = new PhotoItemRecyclerViewAdapter(DummyContent.ITEMS, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position, DummyContent.DummyItem item) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(PhotoDetailFragment.ARG_ITEM_ID, item.id);
            PhotoDetailFragment fragment = new PhotoDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.photo_detail_container, fragment)
                    .commit();
        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, PhotoDetailActivity.class);
            intent.putExtra(PhotoDetailFragment.ARG_ITEM_ID, item.id);

            context.startActivity(intent);
        }
    }

}
