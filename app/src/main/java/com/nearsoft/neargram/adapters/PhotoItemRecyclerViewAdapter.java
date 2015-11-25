package com.nearsoft.neargram.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.PhotoListContentBinding;
import com.nearsoft.neargram.view.models.PhotoVM;

import java.util.List;

/**
 * Photo recycler view adapter.
 * Created by epool on 11/23/15.
 */
public class PhotoItemRecyclerViewAdapter
        extends RecyclerView.Adapter<PhotoItemRecyclerViewAdapter.ViewHolder> {

    private final List<PhotoVM> photoVMs;
    private OnItemClickListener itemClickListener;

    public PhotoItemRecyclerViewAdapter(List<PhotoVM> photoVMs, OnItemClickListener itemClickListener) {
        this.photoVMs = photoVMs;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PhotoListContentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.photo_list_content, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PhotoVM item = photoVMs.get(position);
        holder.setupView(position, item);
    }

    @Override
    public int getItemCount() {
        return photoVMs.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, PhotoVM item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PhotoListContentBinding binding;

        public ViewHolder(PhotoListContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(final int position, final PhotoVM photoVM) {
            binding.setPhoto(photoVM);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, photoVM);
                }
            });

            binding.executePendingBindings();
        }
    }
}
