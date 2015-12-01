package com.nearsoft.neargram.view.models.adapters;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * View model adapter.
 * Created by epool on 11/24/15.
 */
public class ViewModelAdapter {

    @BindingAdapter(value = {"bind:imageUrl", "bind:placeholder", "bind:isRounded"}, requireAll = false)
    public static void loadImage(final ImageView imageView, String url, Drawable placeholder, boolean isRounded) {
        if (url == null) {
            return;
        }

        if (!isRounded) {
            DrawableRequestBuilder builder = Glide.with(imageView.getContext())
                    .load(url)
                    .fitCenter();
            if (placeholder != null) {
                builder.placeholder(placeholder);
                builder.error(placeholder);
            }
            builder.into(imageView);
        } else {
            BitmapRequestBuilder builder = Glide.with(imageView.getContext())
                    .load(url)
                    .asBitmap()
                    .fitCenter();
            if (placeholder != null) {
                builder.placeholder(placeholder);
                builder.error(placeholder);
            }
            builder.into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    super.setResource(resource);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                    roundedBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(roundedBitmapDrawable);
                }
            });
        }
    }

    @BindingAdapter({"bind:colors"})
    public static void setColorSchemeColors(SwipeRefreshLayout swipeRefreshLayout, int colorsResource) {
        swipeRefreshLayout.setColorSchemeColors(swipeRefreshLayout.getContext().getResources().getIntArray(colorsResource));
    }

    @BindingAdapter({"bind:unixTimestamp"})
    public static void unixTimestampToFormattedDateString(TextView textView, long unixTimestamp) {
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        String formattedDateString = dateFormat.format(new Date(unixTimestamp * 1000));
        textView.setText(formattedDateString);
    }

}
