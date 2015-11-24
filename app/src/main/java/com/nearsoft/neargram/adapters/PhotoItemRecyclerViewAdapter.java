package com.nearsoft.neargram.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.PhotoListContentBinding;
import com.nearsoft.neargram.dummy.DummyContent;

import java.util.List;

/**
 * Photo recycler view adapter.
 * Created by epool on 11/23/15.
 */
public class PhotoItemRecyclerViewAdapter
        extends RecyclerView.Adapter<PhotoItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent.DummyItem> mValues;
    private OnItemClickListener itemClickListener;

    public PhotoItemRecyclerViewAdapter(List<DummyContent.DummyItem> items, OnItemClickListener itemClickListener) {
        mValues = items;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PhotoListContentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.photo_list_content, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DummyContent.DummyItem item = mValues.get(position);
        holder.setupView(position, item);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, DummyContent.DummyItem item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PhotoListContentBinding binding;

        public ViewHolder(PhotoListContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(final int position, final DummyContent.DummyItem item) {
            binding.id.setText(item.id);
            binding.content.setText(item.content);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, item);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + binding.content.getText() + "'";
        }
    }
}
