package com.nearsoft.neargram.webservices;

import com.nearsoft.neargram.webservices.responses.InstagramPopularResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Instagram service.
 * Created by epool on 11/23/15.
 */
public interface InstagramService {

    @GET("media/popular")
    Call<InstagramPopularResponse> getPopularPhotos(@Query("client_id") String clientId);

}
