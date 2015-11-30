package com.nearsoft.neargram.view.fragments;


import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.FragmentPhotoDetailBinding;
import com.nearsoft.neargram.model.PhotoModel;
import com.nearsoft.neargram.model.realm.Comment;
import com.nearsoft.neargram.model.realm.Photo;
import com.nearsoft.neargram.view.adapters.realm.CommentRecyclerViewAdapter;
import com.nearsoft.neargram.view.models.PhotoVM;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoDetailFragment extends Fragment implements RealmChangeListener {
    private static String ARG_PHOTO = "ARG_PHOTO";
    private PhotoVM photoVM;
    private Realm realm;
    private CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    private FragmentPhotoDetailBinding binding;

    public PhotoDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PhotoDetailFragment.
     */
    public static PhotoDetailFragment newInstance(PhotoVM photoVM) {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHOTO, photoVM);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photoVM = getArguments().getParcelable(ARG_PHOTO);
        }

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_detail, container, false);

        Glide.with(this)
                .load(photoVM.getStandardResolutionUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                final Palette.Swatch swatch = palette.getVibrantSwatch();

                                int vibrantColor = palette.getVibrantColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                                int titleTextColor = Color.WHITE;
                                int bodyTextColor = Color.WHITE;
                                if (swatch != null) {
                                    titleTextColor = swatch.getTitleTextColor();
                                    bodyTextColor = swatch.getBodyTextColor();
                                }
                                setupRecyclerView(vibrantColor, titleTextColor, bodyTextColor);
                            }
                        });
                    }
                });

        return binding.getRoot();
    }

    private void setupRecyclerView(int vibrantColor, int titleTextColor, int bodyTextColor) {
        Photo photo = PhotoModel.getPhotoById(realm, photoVM.getId());
        RealmResults<Comment> comments = photo.getComments().where().findAll();
        commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(getContext(), comments, true, photoVM, vibrantColor, titleTextColor, bodyTextColor);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewComments.setLayoutManager(layoutManager);
        binding.recyclerViewComments.setAdapter(commentRecyclerViewAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (realm != null) {
            realm.removeChangeListener(this);
            realm.close();
        }
    }

    @Override
    public void onChange() {
        commentRecyclerViewAdapter.notifyDataSetChanged();
    }
}
