package com.nearsoft.neargram.webservices;

import android.support.annotation.IntRange;

import com.nearsoft.neargram.webservices.responses.InstagramSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Instagram service.
 * Created by epool on 11/23/15.
 */
public interface InstagramService {

    @GET("media/search")
    Call<InstagramSearchResponse> getPopularPhotos(@Query("lat") double latitude, @Query("lng") double longitude, @Query("distance") @IntRange(from = 1000, to = 5000) int distanceInMeters);

}
