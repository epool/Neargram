package com.nearsoft.neargram.view.adapters.realm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.CommentItemBinding;
import com.nearsoft.neargram.model.realm.Comment;
import com.nearsoft.neargram.view.models.CommentVM;
import com.nearsoft.neargram.view.models.PhotoVM;

import io.realm.RealmResults;

/**
 * Comment recycler view adapter.
 * Created by epool on 11/28/15.
 */
public class CommentRecyclerViewAdapter extends RealmRecyclerViewAdapter<Comment> {

    public CommentRecyclerViewAdapter(Context context, RealmResults<Comment> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item, parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        commentViewHolder.setupView(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, PhotoVM item);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private CommentItemBinding binding;

        public CommentViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(final int position) {
            Comment comment = getItem(position);
            final CommentVM commentVM = new CommentVM(comment);
            binding.setComment(commentVM);
            binding.executePendingBindings();
        }
    }

}
