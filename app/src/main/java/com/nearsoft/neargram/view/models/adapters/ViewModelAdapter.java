package com.nearsoft.neargram.view.models.adapters;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * View model adapter.
 * Created by epool on 11/24/15.
 */
public class ViewModelAdapter {

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, int errorResource) {
        Glide.with(imageView.getContext())
                .load(url)
                .fitCenter()
                .placeholder(errorResource)
                .error(errorResource)
                .into(imageView);
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
