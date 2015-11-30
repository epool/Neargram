package com.nearsoft.neargram.view.adapters.realm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nearsoft.neargram.R;
import com.nearsoft.neargram.databinding.CommentItemBinding;
import com.nearsoft.neargram.databinding.CommentsHeaderBinding;
import com.nearsoft.neargram.model.realm.Comment;
import com.nearsoft.neargram.view.models.CommentVM;
import com.nearsoft.neargram.view.models.PhotoVM;

import io.realm.RealmResults;

/**
 * Comment recycler view adapter.
 * Created by epool on 11/28/15.
 */
public class CommentRecyclerViewAdapter extends RealmRecyclerViewAdapter<Comment> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private PhotoVM photoVM;
    private int vibrantColor;
    private int titleTextColor;
    private int bodyTextColor;

    public CommentRecyclerViewAdapter(Context context, RealmResults<Comment> realmResults, boolean automaticUpdate, PhotoVM photoVM, int vibrantColor, int titleTextColor, int bodyTextColor) {
        super(context, realmResults, automaticUpdate);
        this.photoVM = photoVM;
        this.vibrantColor = vibrantColor;
        this.titleTextColor = titleTextColor;
        this.bodyTextColor = bodyTextColor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            CommentsHeaderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comments_header, parent, false);
            return new HeaderViewHolder(binding);
        } else {
            CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item, parent, false);
            return new CommentViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.setupView(photoVM);
        } else {
            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.setupView(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public Comment getItem(int position) {
        return super.getItem(position - 1);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private CommentsHeaderBinding binding;

        public HeaderViewHolder(CommentsHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(PhotoVM photoVM) {
            binding.setPhoto(photoVM);
            binding.linearLayoutHeader.setBackgroundColor(vibrantColor);
            binding.textViewLikes.setTextColor(titleTextColor);
            binding.textViewCaption.setTextColor(bodyTextColor);
            binding.executePendingBindings();
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private CommentItemBinding binding;

        public CommentViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(int position) {
            Comment comment = getItem(position);
            final CommentVM commentVM = new CommentVM(comment);
            binding.setComment(commentVM);
            binding.executePendingBindings();
        }
    }
}
