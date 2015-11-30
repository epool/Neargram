package com.nearsoft.neargram.view.adapters.realm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.PhotoListContentBinding;
import com.nearsoft.neargram.model.realm.Photo;
import com.nearsoft.neargram.view.models.PhotoVM;

import io.realm.RealmResults;

/**
 * Photo recycler view adapter.
 * Created by epool on 11/28/15.
 */
public class PhotoRecyclerViewAdapter extends RealmRecyclerViewAdapter<Photo> {
    private OnItemClickListener itemClickListener;

    public PhotoRecyclerViewAdapter(Context context, RealmResults<Photo> realmResults, boolean automaticUpdate, OnItemClickListener itemClickListener) {
        super(context, realmResults, automaticUpdate);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PhotoListContentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.photo_list_content, parent, false);
        return new PhotoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
        photoViewHolder.setupView(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, PhotoVM item);
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private PhotoListContentBinding binding;

        public PhotoViewHolder(PhotoListContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(final int position) {
            Photo photo = getItem(position);
            final PhotoVM photoVM = new PhotoVM(photo);
            binding.setPhoto(photoVM);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, position, photoVM);
                    }
                }
            });
            binding.executePendingBindings();
        }
    }

}
