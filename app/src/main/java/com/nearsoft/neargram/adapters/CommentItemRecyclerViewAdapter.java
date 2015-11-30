package com.nearsoft.neargram.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.CommentItemBinding;
import com.nearsoft.neargram.view.models.CommentVM;

import java.util.List;

/**
 * Photo recycler view adapter.
 * Created by epool on 11/26/15.
 */
public class CommentItemRecyclerViewAdapter
        extends RecyclerView.Adapter<CommentItemRecyclerViewAdapter.ViewHolder> {

    private final List<CommentVM> commentVMs;
    private OnItemClickListener itemClickListener;

    public CommentItemRecyclerViewAdapter(List<CommentVM> commentVMs, OnItemClickListener itemClickListener) {
        this.commentVMs = commentVMs;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CommentVM item = commentVMs.get(position);
        holder.setupView(position, item);
    }

    @Override
    public int getItemCount() {
        return commentVMs.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, CommentVM item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CommentItemBinding binding;

        public ViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(final int position, final CommentVM commentVM) {
            binding.setComment(commentVM);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, position, commentVM);
                    }
                }
            });

            binding.executePendingBindings();
        }
    }
}
